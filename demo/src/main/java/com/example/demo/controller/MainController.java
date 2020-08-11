package com.example.demo.controller;

import com.example.demo.entity.auth_data;
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
public class MainController {
    @Autowired
    private AuthDataService authDataService;

    @RequestMapping(value = { "/" }, method = RequestMethod.GET)
    public String viewMainPage(Model model, HttpServletRequest request) {
        User usr = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        auth_data auth = authDataService.findAll().stream()
                .filter(a->a.getLogin().equals(usr.getUsername()))
                .collect(Collectors.toList()).stream().findFirst().get();
        model.addAttribute("login", auth.getLogin());
        model.addAttribute("isAdmin", request.isUserInRole("ROLE_ADMIN"));
        return "main";
    }
}
