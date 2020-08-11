package com.example.demo.entity;

import com.example.demo.service.User2EventService;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "events")
public class events {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer event_id;
    @Column
    private String event_name;
    @Column
    private @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime start_date;
    @Column
    private @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime finish_date;
    @Column
    private int start_hour;
    @Column
    private int finish_hour;
    @Column
    private String description;

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }
    public void setStart_date(LocalDateTime start_date) {
        this.start_date = start_date;
    }
    public void setFinish_date(LocalDateTime finish_date) {
        this.finish_date = finish_date;
    }
    public void setStart_hour(int start_hour) { this.start_hour = start_hour; }
    public void setFinish_hour(int finish_hour) { this.finish_hour = finish_hour; }
    public void setDescription(String description) { this.description = description; }

    public String getEvent_name() {
        return this.event_name;
    }
    public LocalDateTime getStart_date() {
        return this.start_date;
    }
    public LocalDateTime getFinish_date() {
        return this.finish_date;
    }
    public int getStart_hour() { return this.start_hour; }
    public int getFinish_hour() { return this.finish_hour; }
    public Integer getEvent_id() { return this.event_id; }
    public String getDescription() { return this.description; }

    public List<String> GetUsersList(User2EventService user2EventService) {
        List<user2event> usrs = user2EventService.GetUsersByEventIdList(this.event_id);
        List<String> logins = new ArrayList<>();
        usrs.forEach(usr->{
            logins.add(usr.getUser().getFirst_name() + " " + usr.getUser().getLast_name());
        });

        return logins;
    }
}
