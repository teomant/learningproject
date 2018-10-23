package org.teomant.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.teomant.entity.AuthoritiesEntity;
import org.teomant.entity.MessageEntity;
import org.teomant.entity.UserEntity;
import org.teomant.repository.AuthoritiesRepository;
import org.teomant.repository.MessagesRepository;
import org.teomant.service.AuthoritiesService;
import org.teomant.service.MessagesService;

import java.util.List;

@Service
public class MessagesServiceImpl implements MessagesService {

    @Autowired
    MessagesRepository messagesRepository;

    @Override
    public MessageEntity save(MessageEntity messageEntity) {
        return messagesRepository.save(messageEntity);
    }

    @Transactional(readOnly = true)
    @Override
    public List<MessageEntity> getMessagesByUser(UserEntity userEntity) {
        return messagesRepository.findByUser(userEntity);
    }


}
