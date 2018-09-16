package org.teomant.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.teomant.entity.AuthoritiesEntity;
import org.teomant.entity.UserEntity;
import org.teomant.repository.AuthoritiesRepository;
import org.teomant.service.AuthoritiesService;

import java.util.List;

@Service
public class AuthoritiesServiceImpl implements AuthoritiesService {

    @Autowired
    AuthoritiesRepository authoritiesRepository;

    @Override
    public AuthoritiesEntity save(AuthoritiesEntity authoritiesEntity) {
        return authoritiesRepository.save(authoritiesEntity);
    }

    @Override
    public List<AuthoritiesEntity> getAuthoritiesByUser(UserEntity userEntity) {
        return authoritiesRepository.findByUser(userEntity);
    }


}
