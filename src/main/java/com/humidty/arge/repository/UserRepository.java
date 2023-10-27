package com.humidty.arge.repository;

import com.humidty.arge.model.Device;
import com.humidty.arge.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

  Optional<User> findByEmail(String email);


  Optional<User> findById(Integer id);

  Optional<User> findByUserToken(String token);
}
