package com.example.springangularapp.Validator;

import com.example.springangularapp.service.CompanyService;
import com.example.springangularapp.entity.CompanyEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    @Autowired
    private CompanyService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return CompanyEntity.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        CompanyEntity user = (CompanyEntity) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        if (user.getEmail().length() < 6 || user.getEmail().length() > 32) {
            errors.rejectValue("username", "Size.userForm.username");
        }
        if (userService.findByEmail(user.getEmail()) != null) {
            errors.rejectValue("username", "Duplicate.userForm.username");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }
    }
}
