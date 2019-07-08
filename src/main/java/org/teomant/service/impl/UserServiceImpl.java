package org.teomant.service.impl;

import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.teomant.entity.AuthoritiesEntity;
import org.teomant.entity.UserEntity;
import org.teomant.repository.AuthoritiesRepository;
import org.teomant.repository.UserRepository;
import org.teomant.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthoritiesRepository authoritiesRepository;

    @Override
    public Optional<UserEntity> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public UserEntity findUserByUsername(String username, UserEntity.USER_MAPPING... mappings) {
        UserEntity user = userRepository.findByUsername(username);
        Set<UserEntity.USER_MAPPING> mappingSet = Sets.newHashSet(mappings);
        user.setMessageTo(mappingSet.contains(UserEntity.USER_MAPPING.MESSAGE_TO)
                ? userRepository.getMessagesToByUserId(user.getId()) : null);
        user.setMessageFrom(mappingSet.contains(UserEntity.USER_MAPPING.MESSAGES_FROM)
                ? userRepository.getMessagesFromByUserId(user.getId()) : null);
        user.setAuthorities(mappingSet.contains(UserEntity.USER_MAPPING.AUTHORITIES)
                ? userRepository.getAuthoritiesByUserId(user.getId()) : null);
        user.setFiles(mappingSet.contains(UserEntity.USER_MAPPING.FILES)
                ? userRepository.getFilesByUserId(user.getId()) : null);
        user.setPosts(mappingSet.contains(UserEntity.USER_MAPPING.POSTS)
                ? userRepository.getPostsByUserId(user.getId()) : null);
        user.setPassword("Nope");
        return user;
    }

    @Override
    public UserEntity save(UserEntity userEntity) {
        Long id = userRepository.saveAndFlush(userEntity).getId();
        UserEntity user = userRepository.getOne(id);
        return user;
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    @Override
    public List<AuthoritiesEntity> findAuthById(Long id) {
        return userRepository.getAuthoritiesByUserId(id);
    }

    @Transactional(readOnly = true)
    @Override
    public UserEntity findById(Long id) {
        return userRepository.findById(id).get();
    }
}
