package com.example.demo;

import com.example.demo.entity.users;
import com.example.demo.repository.UsersRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.autoconfigure.jdbc.*;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SuppressWarnings("ALL")
@SpringBootApplication

public class DemoApplication {
    @Autowired
    private UserService userService;
    public static void main(String[] args) {
        try{
            SpringApplication.run(DemoApplication.class, args);
        } catch (Exception ex) {
            System.out.println("ebnulos");
        }
    }

    @EventListener(ApplicationReadyEvent.class)
    private void testJpaMethods() {
        users users = new users();

        users.setEmail("someEmail@gmail.com");
        users.SetFirstName("Inna");
        users.SetLastName("Shutova");
        //userService.CreateUsers(users);
        //userService.findAll().forEach(it-> System.out.println(it));
    }
}
