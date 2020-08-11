package com.example.demo.repository;

import com.example.demo.entity.auth_data;
import com.example.demo.entity.events;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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
}
