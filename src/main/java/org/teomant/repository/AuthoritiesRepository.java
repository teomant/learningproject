package org.teomant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.teomant.entity.AuthoritiesEntity;
import org.teomant.entity.UserEntity;

import java.util.List;

public interface AuthoritiesRepository extends JpaRepository<AuthoritiesEntity, Long> {

//    @Query( "select au.authority from AuthoritiesEntity au where au.user = :user" )
//    List<AuthoritiesEntity> getAuthoritiesByUser(
//            @Param( "user" )
//                    UserEntity user );

    List<AuthoritiesEntity> findByUser(UserEntity user);
}
