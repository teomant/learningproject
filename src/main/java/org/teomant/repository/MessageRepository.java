package org.teomant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.teomant.entity.MessageEntity;
import org.teomant.entity.PostEntity;
import org.teomant.entity.UserEntity;

import java.util.List;

public interface MessageRepository extends JpaRepository<MessageEntity, Long> {

    List<MessageEntity> findByFrom(UserEntity user);

    List<MessageEntity> findByTo(UserEntity user);

    List<MessageEntity> findByFromAndTo(UserEntity from, UserEntity to);


}
