package org.teomant.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.teomant.entity.UserEntity;
import org.teomant.entity.UserFileEntity;
import org.teomant.repository.UserFileRepository;
import org.teomant.service.UserFileService;

import java.util.List;

@Service
public class UserFileServiceImpl implements UserFileService {

    @Autowired
    UserFileRepository userFileRepository;

    @Override
    public UserFileEntity save(UserFileEntity file) {
        return userFileRepository.save(file);
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserFileEntity> findByUser(UserEntity user) {
        return userFileRepository.findByUser(user);
    }

    @Transactional(readOnly = true)
    @Override
    public UserFileEntity findById(Long id) {
        return userFileRepository.findById(id).get();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Long> findIdsByUser(UserEntity user) {
        return userFileRepository.findIdsByUser(user);
    }
}
