package org.teomant.service;


import org.teomant.entity.AuthoritiesEntity;
import org.teomant.entity.MessageEntity;
import org.teomant.entity.UserEntity;

import java.util.List;

public interface MessagesService {
    MessageEntity save(MessageEntity messageEntity);
    List<MessageEntity> getMessagesByUser(UserEntity userEntity);
}
