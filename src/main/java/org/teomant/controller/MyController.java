package org.teomant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.teomant.controller.forms.RegistrationForm;
import org.teomant.controller.forms.validators.RegistrationValidator;
import org.teomant.entity.AuthoritiesEntity;
import org.teomant.entity.UserEntity;
import org.teomant.service.AuthoritiesService;
import org.teomant.service.UserService;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@Controller
@RequestMapping
public class MyController {

    @Autowired
    private AuthoritiesService authoritiesService;

    @Autowired
    private UserService userService;

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
        return "userPage";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    private boolean createUser(RegistrationForm registrationForm, String role){
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(registrationForm.getUsername());
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
}