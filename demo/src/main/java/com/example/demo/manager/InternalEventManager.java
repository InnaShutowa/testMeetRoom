package com.example.demo.manager;

import com.example.demo.entity.events;
import com.example.demo.entity.users;
import com.example.demo.model.DayModel;
import com.example.demo.model.HourModel;
import com.example.demo.service.EventService;
import com.example.demo.service.User2EventService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class InternalEventManager {
    private EventService eventService;
    private User2EventService user2EventService;

    public InternalEventManager() {}

    public InternalEventManager(EventService service, User2EventService user2EventService) {
        this.eventService = service;
        this.user2EventService = user2EventService;
    }

    public List<DayModel> GetFullWeekDaysModel(LocalDateTime day) {
        DayOfWeek dayOfWeek = day.getDayOfWeek();
        List<DayModel> days;
        if (dayOfWeek == DayOfWeek.MONDAY) {
            days = this.CreateDaysModelByMonday(day);
        } else {
            while(dayOfWeek!=DayOfWeek.MONDAY) {
                day = day.minusDays(1);
                dayOfWeek = day.getDayOfWeek();
            }
            days = this.CreateDaysModelByMonday(day);
        }
        return days;
    }

    /// создаем модель для любовго дня по дате
    public DayModel CreateDaysModel(LocalDateTime day) {
        LocalDateTime startDay = LocalDateTime.of(day.toLocalDate(),
                LocalTime.of(0,0,0,0));
        LocalDateTime finishDay = LocalDateTime.of(day.toLocalDate(),
                LocalTime.of(23,59,0,0));

        List<events> evs = eventService.getEventsList(startDay, finishDay);
        List<HourModel> hours = this.GetHoursList(startDay, finishDay, day, evs);

        return new DayModel(day, hours);
    }

    /// сюда в качестве параметра передается понедельник
    public List<DayModel> CreateDaysModelByMonday(LocalDateTime day) {
        List<DayModel> result = new ArrayList<>();
        DayOfWeek dayOfWeek = day.getDayOfWeek();
        while (dayOfWeek != DayOfWeek.SUNDAY) {
            result.add(this.CreateDaysModel(day));
            day = day.plusDays(1);
            dayOfWeek = day.getDayOfWeek();
        }
        return result;
    }

    private List<HourModel> GetHoursList(LocalDateTime startDay,
                                         LocalDateTime finishDay,
                                         LocalDateTime day,
                                         List<events> evs) {
        List<events> evsStart = evs.stream()
                .filter(a->a.getStart_date().toLocalDate().equals(day.toLocalDate())
                        && !a.getFinish_date().toLocalDate().equals(day.toLocalDate()))
                .collect(Collectors.toList());

        List<events> evsBoth = evs.stream()
                .filter(a->a.getStart_date().toLocalDate().equals(day.toLocalDate())
                        && a.getFinish_date().toLocalDate().equals(day.toLocalDate()))
                .collect(Collectors.toList());

        List<events> evsFinish = evs.stream()
                .filter(a->a.getFinish_date().toLocalDate().equals(day.toLocalDate())
                        && !a.getStart_date().toLocalDate().equals(day.toLocalDate()))
                .collect(Collectors.toList());

        List<HourModel> hours = new ArrayList<>();

        evsBoth.forEach(ev->{
            hours.add(new HourModel(ev.getStart_hour(),
                    ev.getEvent_name(),
                    ev.getStart_date(),
                    ev.getFinish_date(),
                    ev.GetUsersList(user2EventService),
                    ev.getEvent_id()));
            int count = ev.getStart_hour();
            while(count < ev.getFinish_hour()) {
                count++;
                hours.add(new HourModel(count,
                        ev.getEvent_name(),
                        ev.getStart_date(),
                        ev.getFinish_date(),
                        ev.GetUsersList(user2EventService),
                        ev.getEvent_id()));
            }
        });

        evsStart.forEach(ev->{
            hours.add(new HourModel(ev.getStart_hour(),
                    ev.getEvent_name(),
                    ev.getStart_date(),
                    finishDay,
                    ev.GetUsersList(user2EventService),
                    ev.getEvent_id()));
            int count = ev.getStart_hour();
            while(count < 24) {
                count++;
                hours.add(new HourModel(count,
                        ev.getEvent_name(),
                        ev.getStart_date(),
                        finishDay,
                        ev.GetUsersList(user2EventService),
                        ev.getEvent_id()));
            }
        });

        evsFinish.forEach(ev->{
            hours.add(new HourModel(0,
                    ev.getEvent_name(),
                    startDay,
                    ev.getFinish_date(),
                    ev.GetUsersList(user2EventService),
                    ev.getEvent_id()));
            int count = 0;
            while(count < ev.getFinish_hour()) {
                count++;
                hours.add(new HourModel(count,
                        ev.getEvent_name(),
                        startDay,
                        ev.getFinish_date(),
                        ev.GetUsersList(user2EventService),
                        ev.getEvent_id()));
            }
        });
        return hours;
    }
}
