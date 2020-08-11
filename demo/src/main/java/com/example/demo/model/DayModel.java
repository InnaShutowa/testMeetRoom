package com.example.demo.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Dictionary;
import java.util.List;

public class DayModel {
    private String day_date;
    private List<HourModel> hours;

    public DayModel() {}
    public DayModel(LocalDateTime day_date, List<HourModel> hours){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formatDateTime = day_date.format(formatter);
        this.day_date = formatDateTime;
        this.hours = hours;
    }

    public void setHours(List<HourModel> hours){
        this.hours = hours;
    }
    public void setDay_date(String day_date) {
        this.day_date = day_date;
    }
    public List<HourModel> getHours() {
        return this.hours;
    }
    public String getDay_date(){
        return this.day_date;
    }
}
