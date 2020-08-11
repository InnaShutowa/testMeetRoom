package com.example.demo.repository;

import com.example.demo.entity.users;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@EntityScan(basePackages = {"com.example.demo.entity"})
public interface UsersRepository extends JpaRepository<users, Integer> {


}