package com.example.demo.manager;

import com.example.demo.entity.auth_data;
import com.example.demo.entity.events;
import com.example.demo.entity.user2event;
import com.example.demo.entity.users;
import com.example.demo.model.EventCreateResultModel;
import com.example.demo.service.AuthDataService;
import com.example.demo.service.EventService;
import com.example.demo.service.User2EventService;
import com.example.demo.service.UserService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class InternalCreateEventManager {
    private EventService eventService;
    private AuthDataService authDataService;
    private UserService userService;
    private User2EventService user2EventService;
    public InternalCreateEventManager() {}
    public InternalCreateEventManager(EventService eventService,
                                      AuthDataService authDataService,
                                      UserService userService,
                                      User2EventService user2EventService) {
        this.eventService = eventService;
        this.authDataService = authDataService;
        this.userService = userService;
        this.user2EventService = user2EventService;
    }
    public EventCreateResultModel CreateEventByParams(String eventName,
                                                      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                                      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime finishDate,
                                                      String description) {

        if (startDate == null
                || finishDate == null
                || startDate.isBefore(LocalDateTime.now())
                || finishDate == startDate
                || finishDate.isBefore(startDate)
                || Duration.between(startDate, finishDate).toHours()>24
                || Duration.between(startDate, finishDate).toMinutes()<30)
            return new EventCreateResultModel("Некорректные даты");

        List<events> events = eventService.findAll();
        if (events.stream().anyMatch(event -> event.getStart_date().isBefore(startDate)
                && event.getFinish_date().isAfter(startDate)) ||
                events.stream().anyMatch(event -> event.getStart_date().isBefore(finishDate)
                        && event.getFinish_date().isAfter(finishDate))) {
            return new EventCreateResultModel("Время уже занято");
        }

        events newEvent = this.CreateNewEvent(eventName, startDate, finishDate, description);

        return new EventCreateResultModel(newEvent);
    }

    public void CreateUsersForEvent(events event, List<String> users) {
        User usr = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        auth_data auth = authDataService.findAll().stream()
                .filter(a->a.getLogin().equals(usr.getUsername()))
                .collect(Collectors.toList()).stream().findFirst().get();
        this.CreateUsersByParams(auth, event, true);
        List<auth_data> auths = authDataService.findAll();

        users.forEach(login->{
            List<auth_data> filtered = auths.stream().filter(a->a.getLogin().equals(login)).collect(Collectors.toList());
            if (filtered.size() != 0){
                auth_data found = filtered.stream().findFirst().get();
                this.CreateUsersByParams(found, event, false);
            }
        });
    }

    private void CreateUsersByParams(auth_data auth, events event, boolean isCreator) {
        user2event usr = new user2event();

        usr.setUser(auth.getUser());
        usr.setEvent(event);
        usr.setIs_creator(isCreator);
        user2EventService.CreateUser2Event(usr);
    }

    private events CreateNewEvent(String eventName,
                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime finishDate,
                                  String description) {
        events newEvent = new events();
        newEvent.setEvent_name(eventName);
        newEvent.setFinish_date(finishDate);
        newEvent.setStart_date(startDate);
        newEvent.setDescription(description);

        if (startDate.getMinute() > 0)
            newEvent.setStart_hour(startDate.getHour()+1);
        else
            newEvent.setStart_hour(startDate.getHour());
        if (finishDate.getMinute() > 0)
            newEvent.setFinish_hour(finishDate.getHour()+1);
        else
            newEvent.setFinish_hour(finishDate.getHour());

        eventService.CreateEvent(newEvent);
        return newEvent;
    }
}
