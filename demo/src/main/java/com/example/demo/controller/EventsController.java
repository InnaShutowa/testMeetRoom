package com.example.demo.controller;

import com.example.demo.entity.auth_data;
import com.example.demo.manager.InternalEventManager;
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
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/eventsList")
public class EventsController {

    @Autowired
    private User2EventService user2EventService;
    @Autowired
    private EventService eventService;
    @Autowired
    private AuthDataService authDataService;

    @RequestMapping(method = RequestMethod.GET)
    public String viewPersonList(HttpServletResponse response,
                                HttpServletRequest request,
                                Model model,
                                @RequestParam(value = "number", required = false) Integer number,
                                @RequestParam(value = "direction", required = false) String direction) throws IOException {
        InternalEventManager manager = new InternalEventManager(this.eventService, this.user2EventService);
        LocalDateTime day = LocalDateTime.now();
        if (number == null) number = 0;

        day = day.plusWeeks(number);
        User usr = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        auth_data auth = authDataService.findAll().stream()
                .filter(a->a.getLogin().equals(usr.getUsername()))
                .collect(Collectors.toList()).stream().findFirst().get();
        model.addAttribute("login", auth.getLogin());
        model.addAttribute("days", manager.GetFullWeekDaysModel(day));
        model.addAttribute("number", number);
        model.addAttribute("isAdmin", request.isUserInRole("ROLE_ADMIN"));

        return "eventsList";
    }
}
