package org.teomant.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.teomant.entity.AuthoritiesEntity;
import org.teomant.entity.UserEntity;
import org.teomant.repository.AuthoritiesRepository;
import org.teomant.repository.UserRepository;
import org.teomant.service.UserService;

import java.util.List;
import java.util.Optional;

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

    @Override
    public UserEntity findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserEntity save(UserEntity userEntity) {
        Long id = userRepository.saveAndFlush(userEntity).getId();
        UserEntity user = userRepository.getOne(id);
        return user;
    }

    @Override
    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }
}
