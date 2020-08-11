package com.example.demo.controller;

import com.example.demo.entity.auth_data;
import com.example.demo.entity.events;
import com.example.demo.model.EventCreateFormModel;
import com.example.demo.service.AuthDataService;
import com.example.demo.service.EventService;
import com.example.demo.service.User2EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    private AuthDataService authDataService;
    @Autowired
    private EventService eventService;
    @Autowired
    private User2EventService user2EventService;

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

        return "createEvent";
    }
}
