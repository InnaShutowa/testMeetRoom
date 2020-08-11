package com.example.demo.controller;

import com.example.demo.entity.auth_data;
import com.example.demo.entity.events;
import com.example.demo.manager.InternalCreateEventManager;
import com.example.demo.model.EventCreateFormModel;
import com.example.demo.model.EventCreateResultModel;
import com.example.demo.service.AuthDataService;
import com.example.demo.service.EventService;
import com.example.demo.service.User2EventService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/editEvent")
public class EditEventController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthDataService authDataService;
    @Autowired
    private EventService eventService;
    @Autowired
    private User2EventService user2EventService;

    @RequestMapping(method = RequestMethod.POST)
    public String createEvent(@ModelAttribute EventCreateFormModel eventForm,
                              BindingResult bindingResult,
                              Model model,
                              @RequestParam(value = "event_id", required = false) Integer event_id) {
        if (eventForm == null || eventForm.getEvent_name() == null){
            model.addAttribute("eventForm", new EventCreateFormModel());
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
                true,
                event_id);

        if (!res.getResult()) {
            model.addAttribute("error", res.getError());
            model.addAttribute("eventForm", new EventCreateFormModel());
            return "createEvent";
        }

        List<events> events = eventService.findAll();
        model.addAttribute("events", events);
        model.addAttribute("number", 1);
        return "redirect:eventsList";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String EditEvent(Model model, HttpServletRequest request, @RequestParam(value = "event_id", required = false) Integer event_id) {
        List<auth_data> auths = authDataService.findAll();
        List<String> users = new ArrayList<>();
        auths.forEach(auth-> users.add(auth.getLogin()));
        events ev = eventService.getOne(event_id);
        EventCreateFormModel modelEvent = new EventCreateFormModel(ev, user2EventService);

        model.addAttribute("users", new ArrayList<String>());
        model.addAttribute("eventForm", modelEvent);
        model.addAttribute("userList", users);
        model.addAttribute("isCreate", false);
        model.addAttribute("event_id", event_id);
        model.addAttribute("isAdmin", request.isUserInRole("ROLE_ADMIN"));

        return "createEvent";
    }
}
