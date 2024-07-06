package com.gila.challenge.notification.repository;

import com.gila.challenge.notification.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  @Query ("select user_id as id  from User u where u.user_id is not null")
  Long getId();
  boolean existsById(Long user_id);
 }
