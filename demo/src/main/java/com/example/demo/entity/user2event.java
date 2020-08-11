package com.example.demo.entity;

import javax.persistence.*;

@Entity
@Table(name = "user2event")
public class user2event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer user2event_id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private users user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "event_id")
    private events event;

    private boolean is_creator;

    public void setUser(users user) {
        this.user = user;
    }
    public void setEvent(events event) {
        this.event = event;
    }
    public void setIs_creator(Boolean is_creator) {
        this.is_creator = is_creator;
    }
    public users getUser(){
        return this.user;
    }
    public events getEvent(){
        return this.event;
    }
    public boolean getIs_creator() { return this.is_creator; }
}
