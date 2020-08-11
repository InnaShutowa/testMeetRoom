package com.example.demo.controller;

import com.example.demo.entity.auth_data;
import com.example.demo.entity.events;
import com.example.demo.entity.user2event;
import com.example.demo.entity.users;
import com.example.demo.manager.InternalCreateEventManager;
import com.example.demo.model.EventCreateFormModel;
import com.example.demo.model.EventCreateResultModel;
import com.example.demo.service.AuthDataService;
import com.example.demo.service.EventService;
import com.example.demo.service.User2EventService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/createEvent")
public class CreateEventController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthDataService authDataService;
    @Autowired
    private EventService eventService;
    @Autowired
    private User2EventService user2EventService;

    @RequestMapping(method = RequestMethod.GET)
    public String CreateEventInit(Model model, HttpServletRequest request) {
        this.AddAttributes(model);

        User usr = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        auth_data auth = authDataService.findAll().stream()
                .filter(a->a.getLogin().equals(usr.getUsername()))
                .collect(Collectors.toList()).stream().findFirst().get();

        model.addAttribute("error", null);
        model.addAttribute("login", auth.getLogin());
        model.addAttribute("isAdmin", request.isUserInRole("ROLE_ADMIN"));
        model.addAttribute("isCreate", true);
        return "createEvent";
    }

    private void AddAttributes(Model model) {
        List<auth_data> auths = authDataService.findAll();
        List<String> users = new ArrayList<>();
        auths.forEach(auth-> users.add(auth.getLogin()));

        model.addAttribute("users", new ArrayList<String>());
        model.addAttribute("eventForm", new EventCreateFormModel());
        model.addAttribute("userList", users);
    }

    @RequestMapping(method = RequestMethod.POST)
    public String createEvent(@ModelAttribute EventCreateFormModel eventForm, BindingResult bindingResult, Model model) {
        if (eventForm == null || eventForm.getEvent_name() == null){
            this.AddAttributes(model);
            return "redirect:createEvent";
        }
        InternalCreateEventManager manager = new InternalCreateEventManager(this.eventService,
                this.authDataService,
                this.userService,
                this.user2EventService);

        EventCreateResultModel res = manager.CreateEventByParams(eventForm.getEvent_name(),
                eventForm.getStart_date(),
                eventForm.getFinish_date(),
                eventForm.getDescription(),
                false,
                -1);

        if (!res.getResult()) {
            model.addAttribute("error", res.getError());
            this.AddAttributes(model);
            return "createEvent";
        }

        manager.CreateUsersForEvent(res.getEvent(), eventForm.getUsers());
        List<events> events = eventService.findAll();
        model.addAttribute("events", events);
        model.addAttribute("number", 1);
        return "redirect:eventsList";
    }
}