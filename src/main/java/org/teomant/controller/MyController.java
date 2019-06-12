package org.teomant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.teomant.controller.forms.RegistrationForm;
import org.teomant.controller.forms.validators.RegistrationValidator;
import org.teomant.entity.AuthoritiesEntity;
import org.teomant.entity.MessageEntity;
import org.teomant.entity.UserEntity;
import org.teomant.entity.UserFileEntity;
import org.teomant.service.AuthoritiesService;
import org.teomant.service.MessagesService;
import org.teomant.service.UserFileService;
import org.teomant.service.UserService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping
public class MyController {

    @Autowired
    private AuthoritiesService authoritiesService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserFileService userFileService;

    @Autowired
    private MessagesService messagesService;

    @Autowired
    private RegistrationValidator registrationValidator;

    @ModelAttribute( "registrationForm" )
    public RegistrationForm registrationForm(){
        return new RegistrationForm();
    }

    @InitBinder( "registrationForm" )
    protected void initRegistrationBinder( WebDataBinder binder ){
        binder.setValidator( registrationValidator );
    }

    @GetMapping("/")
    public String index(Model model, Principal principal) {
        model.addAttribute("message", "You are logged in as " + principal.getName());
        return "index";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        return "registration";
    }

    @PostMapping(value = "/registration")
    public String postRegistration(Model model,
                                   HttpServletResponse response,
                                   @Validated
                                   @ModelAttribute( "registrationForm" )
                                           RegistrationForm registrationForm, BindingResult errors ) {

        if (errors.hasErrors()){
            return "registration";
        }

        if (createUser(registrationForm,"ROLE_USER")){
            return "redirect:/";
        }
        return "registration";
    }

    @GetMapping("/admin/page")
    public String adminPage(Model model, Principal principal) {
        return "adminPage";
    }

    @GetMapping("/admin/create")
    public String createAdminPage(Model model, Principal principal) {
        return "createAdmin";
    }

    @PostMapping("/admin/create")
    public String createAdminPageAction(Model model,
                                        HttpServletResponse response,
                                        @Validated
                                        @ModelAttribute( "registrationForm" )
                                        RegistrationForm registrationForm, BindingResult errors) {
        if (errors.hasErrors()){
            return "createAdmin";
        }
        if (createUser(registrationForm,"ROLE_ADMIN")){
            return "redirect:/";
        }
        return "createAdmin";
    }

    @GetMapping("/user/page")
    public String userPage(Model model, Principal principal) {
        UserEntity user = userService.findUserByUsername(principal.getName());
        user.setAuthorities(authoritiesService.getAuthoritiesByUser(user));
//        user.setFiles(userFileService.findByUser(user));
        model.addAttribute("fileIds", userFileService.findIdsByUser(user));
        model.addAttribute("user",user);
        return "userPage";
    }

    @GetMapping("/login")
    public String login(Model model, Principal principal) {
        if (principal==null) {
            return "login";
        }
        else return "redirect:/";
    }

    @GetMapping("/accessDenied")
    public String accessDenied(Model model) {
        return "accessDenied";
    }

    @PostMapping(value = "/uploadFile")
    public String submit(@RequestParam("file") MultipartFile file, Model model, Principal principal) throws IOException {

        UserEntity userEntity = userService.findUserByUsername(principal.getName());
        UserFileEntity userFileEntity = new UserFileEntity();
        userFileEntity.setFile(file.getBytes());
        userFileEntity.setUser(userEntity);
        userFileService.save(userFileEntity);

        return "redirect:/user/page";
    }

    @GetMapping("/user/image")
    @ResponseBody
    public byte[] getImage(Model model,
                           @RequestParam("id") Long id) {
        return userFileService.findById(id).getFile();
    }

    private boolean createUser(RegistrationForm registrationForm, String role){
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(registrationForm.getUsername());
        userEntity.setFiles(null);
        userEntity.setPassword(new BCryptPasswordEncoder().encode(registrationForm.getPassword()));
        AuthoritiesEntity authoritiesEntity = new AuthoritiesEntity();
        if (userService.findAll().isEmpty()){
            authoritiesEntity.setAuthority("ROLE_ADMIN");
        } else {
            authoritiesEntity.setAuthority(role);
        }
        authoritiesEntity.setUser(userEntity);
        userService.save(userEntity);
        authoritiesService.save(authoritiesEntity);
        return true;
    }

    @GetMapping("/chat/{id}")
    public String getChat(Model model, Principal principal,
                           @PathVariable("id") Long toId) {
        UserEntity user = userService.findUserByUsername(principal.getName());
        UserEntity to = userService.findById(toId);
        List<MessageEntity> messages = messagesService.getFromToMessages(user,to);
        messages.addAll(messagesService.getFromToMessages(to,user));
        messages.sort((m1, m2) -> m1.getDate().compareTo(m2.getDate()));

        model.addAttribute("from", user);
        model.addAttribute("to", to);
        model.addAttribute("messages", messages);
        return "chatPage";
    }

    @PostMapping("/chat/{id}/newMessage")
    @ResponseBody
    public String newMessage(Principal principal, @PathVariable("id") Long toId,
                             @RequestBody Map<String, String> requestBody , Model model){
        try{
            MessageEntity messageEntity = new MessageEntity();
            messageEntity.setText(requestBody.get("messageText"));
            messageEntity.setTo(userService.findById(toId));
            messageEntity.setFrom(userService.findUserByUsername(principal.getName()));
            messageEntity.setDate(LocalDateTime.now());
            messagesService.save(messageEntity);
            return "success";
        }catch( IllegalArgumentException e ){
            return "fail";
        }
    }

    @GetMapping("/chat/{id}/messages")
    @ResponseBody
    public ResponseEntity<List<MessageEntity>> getMessages(Principal principal, @PathVariable("id") Long toId, Model model){
        UserEntity user = userService.findUserByUsername(principal.getName());
        UserEntity to = userService.findById(toId);
        List<MessageEntity> messages = messagesService.getFromToMessages(user,to);
        messages.addAll(messagesService.getFromToMessages(to,user));
        messages.sort((m1, m2) -> m1.getDate().compareTo(m2.getDate()));
        return ResponseEntity.ok(messages);
    }

}