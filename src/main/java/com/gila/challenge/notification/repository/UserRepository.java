package com.gila.challenge.notification.repository;

import com.gila.challenge.notification.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  @Query (value = """
          select userId as id  from User u where u.userPhone = :userPhone
          """)
  Long getUserId(String userPhone);

  @Query (value = """
           select (count(u.userId) = 1) from User u where u.userPhone = :userPhone
          """)
  boolean existsBy(String userPhone);


}
