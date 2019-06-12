package org.teomant.service;


import org.teomant.entity.MessageEntity;
import org.teomant.entity.UserEntity;

import java.util.List;

public interface MessagesService {
    MessageEntity save(MessageEntity messageEntity);
    List<MessageEntity> getFromMessages(UserEntity userEntity);
    List<MessageEntity> getToMessages(UserEntity userEntity);
    List<MessageEntity> getFromToMessages(UserEntity from, UserEntity to);
}
