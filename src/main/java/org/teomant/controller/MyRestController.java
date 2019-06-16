package org.teomant.controller;

import com.google.common.collect.Sets;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.teomant.entity.PostEntity;
import org.teomant.entity.UserEntity;
import org.teomant.service.AuthoritiesService;
import org.teomant.service.PostsService;
import org.teomant.service.UserFileService;
import org.teomant.service.UserService;
import org.teomant.utils.EntityUtils;

import java.security.Principal;
import java.util.Map;
import java.util.Set;

@RestController
public class MyRestController {

    @Autowired
    private PostsService postesService;

    @Autowired
    private EntityUtils entityUtils;

    @ApiOperation(value = "Info about user", notes = "All info about user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Here you are"),
            @ApiResponse(code = 404, message = "nope")
    })
    @GetMapping("/user/me")
    @ResponseBody
    public ResponseEntity<UserEntity> userRest(Model model, Principal principal){
        UserEntity user = entityUtils.getUserEntity(principal.getName()
                , UserEntity.USER_MAPPING.FILES
                , UserEntity.USER_MAPPING.AUTHORITIES
                , UserEntity.USER_MAPPING.MESSAGE_TO
                , UserEntity.USER_MAPPING.MESSAGES_FROM
                , UserEntity.USER_MAPPING.POSTS);
        return ResponseEntity.ok(user);
    }

    @PostMapping(value = "/user/post", headers = "Content-Type=application/json")
    @ResponseBody
    public ResponseEntity<UserEntity> postPost(Model model, @RequestBody
            Map<String, String> requestBody, Principal principal){
        UserEntity user = entityUtils.getUserEntity(principal.getName());
        PostEntity postEntity = new PostEntity();
        postEntity.setUser(user);
        postEntity.setText(requestBody.get("text"));
        postesService.save(postEntity);
        return ResponseEntity.ok(user);
    }

}
