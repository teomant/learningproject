package org.teomant.utils;

import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.teomant.entity.AuthoritiesEntity;
import org.teomant.entity.UserEntity;
import org.teomant.service.AuthoritiesService;
import org.teomant.service.MessagesService;
import org.teomant.service.PostsService;
import org.teomant.service.UserFileService;
import org.teomant.service.UserService;

import java.util.List;
import java.util.Set;

@Service
public class EntityUtils {

    private final UserService userService;

    private final MessagesService messagesService;

    private final AuthoritiesService authoritiesService;

    private final UserFileService userFileService;

    private final PostsService postsService;

    @Autowired
    public EntityUtils(UserService userService, MessagesService messagesService
            , AuthoritiesService authoritiesService, UserFileService userFileService
            , PostsService postsService) {
        this.userService = userService;
        this.messagesService = messagesService;
        this.authoritiesService = authoritiesService;
        this.userFileService = userFileService;
        this.postsService = postsService;
    }

    @Deprecated
    public UserEntity getUserEntity(String name, UserEntity.USER_MAPPING... mappings) {
        UserEntity user = userService.findUserByUsername(name);
        Set<UserEntity.USER_MAPPING> mappingSet = Sets.newHashSet(mappings);
        user.setMessageTo(mappingSet.contains(UserEntity.USER_MAPPING.MESSAGE_TO)
                ? messagesService.getToMessages(user) : null);
        user.setMessageFrom(mappingSet.contains(UserEntity.USER_MAPPING.MESSAGES_FROM)
                ? messagesService.getFromMessages(user) : null);
        user.setAuthorities(mappingSet.contains(UserEntity.USER_MAPPING.AUTHORITIES)
                ? authoritiesService.getAuthoritiesByUser(user) : null);
        user.setFiles(mappingSet.contains(UserEntity.USER_MAPPING.FILES)
                ? userFileService.findByUser(user) : null);
        user.setPosts(mappingSet.contains(UserEntity.USER_MAPPING.POSTS)
                ? postsService.getPostsByUser(user) : null);
        user.setPassword("Nope");
        return user;
    }

    @Deprecated
    public List<AuthoritiesEntity> authoritiesEntityList(UserEntity userEntity) {
        return userService.findAuthById(userEntity.getId());
    }
}
