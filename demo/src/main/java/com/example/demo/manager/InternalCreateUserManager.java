package com.example.demo.manager;

import com.example.demo.entity.auth_data;
import com.example.demo.entity.users;
import com.example.demo.model.UserCreateFormModel;
import com.example.demo.service.AuthDataService;
import com.example.demo.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Random;

public class InternalCreateUserManager {
    private AuthDataService authDataService;
    private UserService userService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public InternalCreateUserManager() {}
    public InternalCreateUserManager(AuthDataService authDataService,
                                     UserService userService,
                                     BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.authDataService = authDataService;
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public users CreateUser(UserCreateFormModel model) {
        users newUser = new users();
        newUser.setEmail(model.getEmail());
        newUser.setFirst_name(model.getFirst_name());
        newUser.setLast_name(model.getLast_name());
        userService.CreateUsers(newUser);
        return newUser;
    }

    public String CreateAuthForUser(users user, String role){
        auth_data auth = new auth_data();
        auth.setLogin(user.getFirst_name().toLowerCase()+"."+user.getLast_name().toLowerCase());

        String pass = this.RandomPassCreate();
        auth.setPass_hash(bCryptPasswordEncoder.encode(pass));
        auth.setUser(user);
        auth.setRole_user(role);
        auth.setEnabled(true);
        authDataService.CreateAuthData(auth);
        return pass;
    }

    private String RandomPassCreate() {
        char[] chars = "abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        StringBuilder sb = new StringBuilder(20);
        Random random = new Random();
        for (int i = 0; i < 12; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        return  sb.toString();
    }
}
