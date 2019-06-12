package org.teomant.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.teomant.entity.MessageEntity;
import org.teomant.entity.PostEntity;
import org.teomant.entity.UserEntity;
import org.teomant.repository.MessageRepository;
import org.teomant.repository.PostsRepository;
import org.teomant.service.MessagesService;
import org.teomant.service.PostsService;

import java.util.List;

@Service
public class MessagesServiceImpl implements MessagesService {

    @Autowired
    MessageRepository messageRepository;

    @Override
    public MessageEntity save(MessageEntity messageEntity) {
        return messageRepository.save(messageEntity);
    }

    @Transactional(readOnly = true)
    @Override
    public List<MessageEntity> getFromMessages(UserEntity userEntity) {
        return messageRepository.findByFrom(userEntity);
    }

    @Transactional(readOnly = true)
    @Override
    public List<MessageEntity> getToMessages(UserEntity userEntity) {
        return messageRepository.findByTo(userEntity);
    }

    @Transactional(readOnly = true)
    @Override
    public List<MessageEntity> getFromToMessages(UserEntity from, UserEntity to){
        return messageRepository.findByFromAndTo(from,to);
    }


}
