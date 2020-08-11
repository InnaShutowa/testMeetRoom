package com.example.demo.controller;
import com.example.demo.entity.auth_data;
import com.example.demo.entity.users;
import com.example.demo.service.AuthDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

@Controller
public class PersonalAreaController {
    @Autowired
    private AuthDataService authDataService;

    @RequestMapping(value = { "/personalArea" }, method = RequestMethod.GET)
    public String viewPersonalArea(Model model, HttpServletRequest request) {
        User usr = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        auth_data auth = authDataService.findAll().stream()
                .filter(a->a.getLogin().equals(usr.getUsername()))
                .collect(Collectors.toList()).stream().findFirst().get();
        users usrs = auth.getUser();
        model.addAttribute("isAdmin", request.isUserInRole("ROLE_ADMIN"));
        model.addAttribute("firstName", usrs.getFirst_name());
        model.addAttribute("lastName", usrs.getLast_name());
        model.addAttribute("email", usrs.getEmail());
        model.addAttribute("login", auth.getLogin());
        return "personalArea";
    }
}
