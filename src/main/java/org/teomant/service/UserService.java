package org.teomant.service;


import org.teomant.entity.AuthoritiesEntity;
import org.teomant.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<UserEntity> getUserById(Long id);
    UserEntity findUserByUsername(String username, UserEntity.USER_MAPPING... mappings);
    UserEntity save(UserEntity userEntity);
    List<UserEntity> findAll();
    List<AuthoritiesEntity> findAuthById(Long id);
    UserEntity findById(Long id);

}
