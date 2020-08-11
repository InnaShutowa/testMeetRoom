package com.example.demo.service;

import com.example.demo.entity.users;
import com.example.demo.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private final UsersRepository usersRepository;

    public UserService(UsersRepository usersRepository){
        this.usersRepository = usersRepository;
    }

    public void CreateUsers(users users) {
        usersRepository.save(users);
    }

    public List<users> findAll(){
        return usersRepository.findAll();
    }
    public users getOne(Integer id) { return usersRepository.getOne(id); }

}
