package com.example.demo.model;

import com.example.demo.entity.events;

public class EventCreateResultModel {

    private boolean Result;
    private String ErrorMess;
    private events Event;

    public EventCreateResultModel() {}

    public EventCreateResultModel(events event) {
        this.Event = event;
        this.Result = true;
    }
    public EventCreateResultModel(String err) {
        this.ErrorMess = err;
        this.Result = false;
    }

    public boolean getResult() {
        return this.Result;
    }
    public String getError() { return this.ErrorMess; }
    public events getEvent() { return this.Event; }
}
