package com.example.demo.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class HourModel {
    private boolean is_busy;
    private Integer number;
    private Integer event_id;
    private String event_name;
    private String start_date;
    private String finish_date;
    private List<String> users;

    public HourModel() {}
    public HourModel(int num,
                     String event_name,
                     LocalDateTime start_date,
                     LocalDateTime finish_date,
                     List<String> users,
                     int event_id) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String formatStartDate = start_date.format(formatter);
        String formatFinishDate = finish_date.format(formatter);

        this.number = num;
        this.event_name = event_name;
        this.start_date = formatStartDate;
        this.finish_date = formatFinishDate;
        this.users = users;
        this.event_id = event_id;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
    public void setIs_busy(boolean is_busy) {
        this.is_busy = is_busy;
    }
    public void setEvent_name(String event_name) { this.event_name = event_name;}
    public void setStart_date(String start_date) { this.start_date = start_date; }
    public void setFinish_date(String finish_date) { this.finish_date = finish_date; }
    public void setUsers(List<String> users) { this.users = users; }
    public void setEvent_id(Integer event_id) { this.event_id = event_id; }

    public Integer getNumber() {
        return this.number;
    }
    public boolean getIs_busy() {
        return this.is_busy;
    }
    public String getEvent_name() { return this.event_name; }
    public String getStart_date() { return this.start_date; }
    public String getFinish_date() { return this.finish_date; }
    public List<String> getUsers() { return this.users; }
    public Integer getEvent_id() { return this.event_id; }
}
