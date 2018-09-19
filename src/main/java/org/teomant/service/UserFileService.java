package org.teomant.service;

import org.teomant.entity.UserEntity;
import org.teomant.entity.UserFileEntity;

import java.util.List;

public interface UserFileService {

    UserFileEntity save(UserFileEntity file);
    List<UserFileEntity> findByUser(UserEntity user);
    UserFileEntity findById(Long id);
    List<Long> findIdsByUser(UserEntity user);
}
