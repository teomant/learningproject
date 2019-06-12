package org.teomant.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.teomant.entity.PostEntity;
import org.teomant.entity.UserEntity;
import org.teomant.repository.PostsRepository;
import org.teomant.service.PostsService;

import java.util.List;

@Service
public class PostsServiceImpl implements PostsService {

    @Autowired
    PostsRepository postsRepository;

    @Override
    public PostEntity save(PostEntity postEntity) {
        return postsRepository.save(postEntity);
    }

    @Transactional(readOnly = true)
    @Override
    public List<PostEntity> getPostsByUser(UserEntity userEntity) {
        return postsRepository.findByUser(userEntity);
    }


}
