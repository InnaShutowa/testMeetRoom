package com.example.demo.model;

import com.example.demo.entity.auth_data;
import com.example.demo.entity.events;
import com.example.demo.service.User2EventService;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class EventCreateFormModel {
    private String event_name;
    private @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start_date;
    private @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime finish_date;
    private List<String> users;
    private String description;

    public EventCreateFormModel() {}

    public EventCreateFormModel(events event, User2EventService user2EventService) {
        this.event_name = event.getEvent_name();
        this.description = event.getDescription();
        this.start_date = event.getStart_date();
        this.finish_date = event.getFinish_date();
        this.users = event.GetUsersList(user2EventService);
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }
    public void setStart_date(LocalDateTime start_date) {
        this.start_date = start_date;
    }
    public void setFinish_date(LocalDateTime finish_date) {
        this.finish_date = finish_date;
    }
    public void setDescription(String description) { this.description = description; }
    public void setUsers(List<String> users) {
        this.users = users;
    }

    public String getEvent_name() {
        return this.event_name;
    }
    public LocalDateTime getStart_date() {
        return this.start_date;
    }
    public LocalDateTime getFinish_date() {
        return this.finish_date;
    }
    public List<String> getUsers() {
        return this.users;
    }
    public String getDescription() { return this.description; }
}