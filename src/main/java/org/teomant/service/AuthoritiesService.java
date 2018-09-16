package org.teomant.service;


import org.teomant.entity.AuthoritiesEntity;
import org.teomant.entity.UserEntity;

import java.util.List;

public interface AuthoritiesService {
    AuthoritiesEntity save(AuthoritiesEntity authoritiesEntity);
    List<AuthoritiesEntity> getAuthoritiesByUser(UserEntity userEntity);
}
