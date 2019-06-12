package org.teomant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.teomant.entity.PostEntity;
import org.teomant.entity.UserEntity;

import java.util.List;

public interface PostsRepository extends JpaRepository<PostEntity, Long> {

    List<PostEntity> findByUser(UserEntity user);
}
