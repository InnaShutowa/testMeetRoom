package com.example.demo.service;

import com.example.demo.entity.events;
import com.example.demo.entity.user2event;
import com.example.demo.entity.users;
import com.example.demo.repository.EventsRepository;
import com.example.demo.repository.User2EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class User2EventService {
    @Autowired
    private final User2EventRepository user2eventRepository;

    public User2EventService(User2EventRepository user2eventRepository){
        this.user2eventRepository = user2eventRepository;
    }

    public void CreateUser2Event(user2event user2event) {
        user2eventRepository.save(user2event);
    }

    public List<user2event> GetUsersByEventIdList(int userId) { return user2eventRepository.getUsersByEventIdList(userId);}
}
