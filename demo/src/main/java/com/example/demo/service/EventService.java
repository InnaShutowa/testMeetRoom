package com.example.demo.service;

import com.example.demo.entity.auth_data;
import com.example.demo.entity.events;
import com.example.demo.entity.users;
import com.example.demo.model.EventCreateResultModel;
import com.example.demo.repository.EventsRepository;
import com.example.demo.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.security.Provider;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class EventService {
    @Autowired
    private final EventsRepository eventsRepository;

    public EventService(EventsRepository eventsRepository){
        this.eventsRepository = eventsRepository;
    }

    public void CreateEvent(events event) {
        eventsRepository.save(event);
    }

    public List<events> findAll(){
        return eventsRepository.findAll();
    }

    public List<events> getEventsList(LocalDateTime start, LocalDateTime finish) {
        return eventsRepository.getEventsList(start, finish);
    }
    public events getOne(Integer id) { return eventsRepository.getOne(id); }
    public events getEventByDates(LocalDateTime date_first, LocalDateTime date_second) { return eventsRepository.getEventByDates(date_first, date_second); }

    public void updateEventInfo(Integer event_id,
                                String event_name,
                                LocalDateTime start,
                                LocalDateTime finish,
                                Integer start_hours,
                                Integer finish_hours,
                                String description) { eventsRepository.updateEventInfo(event_id,
                                                        event_name,
                                                        start,
                                                        finish,
                                                        start_hours,
                                                        finish_hours,
                                                        description); }
}
