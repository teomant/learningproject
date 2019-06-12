package org.teomant.service;


import org.teomant.entity.PostEntity;
import org.teomant.entity.UserEntity;

import java.util.List;

public interface PostsService {
    PostEntity save(PostEntity postEntity);
    List<PostEntity> getPostsByUser(UserEntity userEntity);
}
