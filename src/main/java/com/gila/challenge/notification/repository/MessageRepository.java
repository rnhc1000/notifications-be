package com.gila.challenge.notification.repository;

import com.gila.challenge.notification.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
  @Query (value = """
           select messageId from Message m where m.messageId = :messageId
          """)
  Long getMessageId();
}
