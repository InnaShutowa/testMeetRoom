package com.example.demo.repository;

import com.example.demo.entity.auth_data;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@EntityScan(basePackages = {"com.example.demo.entity"})
public interface AuthDataRepository extends JpaRepository<auth_data, Integer> {
    @Query(value = "select 1 from auth_data where user_id = ?1", nativeQuery = true)
        //если и этого мало - можно написать запрос на чистом SQL и все это будет работать
    auth_data findByUserId(Integer user_id);
}
