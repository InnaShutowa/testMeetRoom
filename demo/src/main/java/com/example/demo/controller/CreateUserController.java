package com.example.demo.controller;

import com.example.demo.entity.auth_data;
import com.example.demo.entity.events;
import com.example.demo.entity.users;
import com.example.demo.manager.InternalCreateEventManager;
import com.example.demo.manager.InternalCreateUserManager;
import com.example.demo.model.EventCreateFormModel;
import com.example.demo.model.EventCreateResultModel;
import com.example.demo.model.UserCreateFormModel;
import com.example.demo.service.AuthDataService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
@RequestMapping(value = "/createUser")
public class CreateUserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthDataService authDataService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @RequestMapping(method = RequestMethod.GET)
    public String CreateEventInit(Model model, HttpServletRequest request) {
        User usr = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        auth_data auth = authDataService.findAll().stream()
                .filter(a->a.getLogin().equals(usr.getUsername()))
                .collect(Collectors.toList()).stream().findFirst().get();

        this.AddAttributes(model);
        model.addAttribute("isAdmin", request.isUserInRole("ROLE_ADMIN"));
        model.addAttribute("error", null);
        model.addAttribute("login", auth.getLogin());
        return "createUser";
    }

    private void AddAttributes(Model model) {
        List<auth_data> auths = authDataService.findAll();
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_USER");
        roles.add("ROLE_ADMIN");
;
        model.addAttribute("userForm", new UserCreateFormModel());
        model.addAttribute("rolesList", roles);
    }

    @RequestMapping(method = RequestMethod.POST)
    public String createUser(@ModelAttribute UserCreateFormModel userForm, BindingResult bindingResult, Model model) {
        if (userForm == null || userForm.getEmail() == null){
            this.AddAttributes(model);
            return "redirect:createEvent";
        }

        InternalCreateUserManager manager = new InternalCreateUserManager(authDataService,
                userService,
                bCryptPasswordEncoder);

        users usr = manager.CreateUser(userForm);
        String pass = manager.CreateAuthForUser(usr, userForm.getRole());

        model.addAttribute("pass", pass);
        this.AddAttributes(model);
        return "createUser";
    }
}
