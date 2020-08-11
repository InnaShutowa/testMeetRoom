package com.example.demo.service;

import com.example.demo.entity.auth_data;
import com.example.demo.entity.users;
import com.example.demo.repository.AuthDataRepository;
import com.example.demo.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthDataService {
    @Autowired
    private final AuthDataRepository authDataRepository;

    public AuthDataService(AuthDataRepository authDataRepository){
        this.authDataRepository = authDataRepository;
    }

    public void CreateAuthData(auth_data auth_data) {
        authDataRepository.save(auth_data);
    }

    public List<auth_data> findAll(){
        return authDataRepository.findAll();
    }
    public auth_data getOne(Integer id) { return authDataRepository.getOne(id); }
    public auth_data findByUserId(Integer id) { return authDataRepository.findByUserId(id); }


}
