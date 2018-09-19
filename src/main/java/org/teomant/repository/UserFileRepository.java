package org.teomant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.teomant.entity.UserEntity;
import org.teomant.entity.UserFileEntity;

import java.util.List;

public interface UserFileRepository extends JpaRepository<UserFileEntity,Long> {

    List<UserFileEntity> findByUser(UserEntity user);

    @Query("select ufe.id from UserFileEntity ufe where ufe.user= :userEntity")
    List<Long> findIdsByUser(@Param("userEntity") UserEntity user);

}
