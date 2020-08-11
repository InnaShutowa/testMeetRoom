package com.example.demo.repository;

import com.example.demo.entity.events;
import com.example.demo.entity.user2event;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

@EntityScan(basePackages = {"com.example.demo.entity"})
public interface User2EventRepository extends JpaRepository<user2event, Integer> {
    @Query(value = "select * from user2event where event_id=?1", nativeQuery = true)
    List<user2event> getUsersByEventIdList(Integer eventId);
}