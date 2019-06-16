package org.teomant.controller.forms.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.teomant.controller.forms.RegistrationForm;
import org.teomant.service.UserService;

@Component
public class RegistrationValidator implements Validator {

    private final UserService userService;

    @Autowired
    public RegistrationValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return RegistrationForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace( errors ,
                "username" ,
                "" ,
                "Can`t be empty" );
        ValidationUtils.rejectIfEmptyOrWhitespace( errors ,
                "password" ,
                "" ,
                "Can`t be empty" );

        RegistrationForm registrationForm = (RegistrationForm) o;

        if (userService.findUserByUsername(registrationForm.getUsername())!=null){
            errors.rejectValue("username", "", "This user already exist");
        }
    }

}
