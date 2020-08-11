package com.example.demo.repository;

import com.example.demo.entity.auth_data;
import com.example.demo.entity.events;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@EntityScan(basePackages = {"com.example.demo.entity"})
public interface EventsRepository extends JpaRepository<events, Integer> {
    @Query(value = "select * from events where (start_date between ?1 and ?2) " +
            "or (finish_date between ?1 and ?2) ", nativeQuery = true)

    List<events> getEventsList(LocalDateTime date_first, LocalDateTime date_second);

    @Query(value = "select 1 from events where start_date = ?1 " +
            "and finish_date = ?2", nativeQuery = true)
    events getEventByDates(LocalDateTime date_first, LocalDateTime date_second);

    @Modifying
    @Transactional
    @Query(value = "update events set event_name = :event_name," +
            " start_date = :start_date," +
            " finish_date = :finish_date," +
            " start_hour = :start_hour," +
            " finish_hour = :finish_hour," +
            " description = :description" +
            " where event_id = :event_id", nativeQuery = true)

    void updateEventInfo(@Param("event_id") Integer event_id,
                           @Param("event_name") String event_name,
                           @Param("start_date")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime start,
                           @Param("finish_date")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime finish,
                           @Param("start_hour") Integer start_hours,
                           @Param("finish_hour") Integer finish_hours,
                           @Param("description") String description);
}
