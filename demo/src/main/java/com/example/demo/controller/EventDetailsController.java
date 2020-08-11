package com.example.demo.controller;

import com.example.demo.entity.auth_data;
import com.example.demo.entity.events;
import com.example.demo.service.AuthDataService;
import com.example.demo.service.EventService;
import com.example.demo.service.User2EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class EventDetailsController {
    @Autowired
    private EventService eventService;
    @Autowired
    private User2EventService user2EventService;
    @Autowired
    private AuthDataService authDataService;

    @RequestMapping(value = "/eventDetails/{detailId}", method=RequestMethod.GET)
    public String getOrder(@PathVariable Integer detailId, Model model, HttpServletRequest request){
        events event = eventService.getOne(detailId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        User usr = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        auth_data auth = authDataService.findAll().stream()
                .filter(a->a.getLogin().equals(usr.getUsername()))
                .collect(Collectors.toList()).stream().findFirst().get();

        model.addAttribute("eventName", event.getEvent_name());
        model.addAttribute("description", event.getDescription());
        model.addAttribute("users", event.GetUsersList(user2EventService));
        model.addAttribute("date_start", event.getStart_date().format(formatter));
        model.addAttribute("date_finish", event.getFinish_date().format(formatter));
        model.addAttribute("login", auth.getLogin());
        model.addAttribute("isAdmin", request.isUserInRole("ROLE_ADMIN"));
        model.addAttribute("event_id", detailId);

        return "eventDetails";
    }
}
