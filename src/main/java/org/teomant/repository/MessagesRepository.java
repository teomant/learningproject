package org.teomant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.teomant.entity.AuthoritiesEntity;
import org.teomant.entity.MessageEntity;
import org.teomant.entity.UserEntity;

import java.util.List;

public interface MessagesRepository extends JpaRepository<MessageEntity, Long> {

    List<MessageEntity> findByUser(UserEntity user);
}
