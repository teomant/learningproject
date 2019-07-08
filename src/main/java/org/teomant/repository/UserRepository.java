package org.teomant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.teomant.entity.AuthoritiesEntity;
import org.teomant.entity.MessageEntity;
import org.teomant.entity.PostEntity;
import org.teomant.entity.UserEntity;
import org.teomant.entity.UserFileEntity;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity,Long> {

    @Query( "select ue.authorities from UserEntity ue where ue.id = :id" )
    List<AuthoritiesEntity> findAuthoritiesByUserId(
            @Param("id")
                    Long id);

    @Query( "select ue.messageTo from UserEntity ue where ue.id = :id" )
    List<MessageEntity> findMessagesToByUserId(
            @Param("id")
                    Long id);

    @Query( "select ue.messageFrom from UserEntity ue where ue.id = :id" )
    List<MessageEntity> findMessagesFromByUserId(
            @Param("id")
                    Long id);

    @Query( "select ue.files from UserEntity ue where ue.id = :id" )
    List<UserFileEntity> findFilesByUserId(
            @Param("id")
                    Long id);

    @Query( "select ue.posts from UserEntity ue where ue.id = :id" )
    List<PostEntity> findPostsByUserId(
            @Param("id")
                    Long id);

//    @Query( "select ue from UserEntity ue where ue.username = :username" )
//    UserEntity getUserByUsername(
//            @Param("username")
//                    String username);

    UserEntity findByUsername(String username);

}
