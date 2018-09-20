package org.teomant.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.teomant.entity.UserEntity;
import org.teomant.service.AuthoritiesService;
import org.teomant.service.UserFileService;
import org.teomant.service.UserService;

import java.security.Principal;

@RestController
public class MyRestController {

    @Autowired
    private AuthoritiesService authoritiesService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserFileService userFileService;

    @ApiOperation(value = "Info about user", notes = "All info about user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Here you are"),
            @ApiResponse(code = 404, message = "nope")
    })
    @GetMapping("/user/me")
    @ResponseBody
    public ResponseEntity<UserEntity> userRest(Model model, Principal principal){
        UserEntity user = userService.findUserByUsername(principal.getName());
        user.setFiles(userFileService.findByUser(user));
        user.setAuthorities(authoritiesService.getAuthoritiesByUser(user));
        user.setPassword("Nope! You must know it, if you`re here");
        return ResponseEntity.ok(user);
    }

}
