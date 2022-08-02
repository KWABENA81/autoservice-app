package com.sas.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.sas.entity.Users;
import com.sas.service.UsersService;

@Component
public class UserValidator implements Validator {
	@Autowired
	private UsersService usersService;

	@Override
	public boolean supports(Class<?> cls) {
		return Users.class.equals(cls);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		Users users = (Users) obj;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
		if (users.getUsername().length() < 6 || users.getUsername().length() > 32) {
			errors.rejectValue("username", "Size.userForm.username");
		}
		if (usersService.findByUserId(users.getUsername()) != null) {
			errors.rejectValue("username", "Duplicate.userForm.username");
		}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
		if (users.getPassword().length() < 8 || users.getPassword().length() > 32) {
			errors.rejectValue("password", "Size.userForm.password");
		}

//		if (!users.getPasswordConfirm().equals(users.getPassword())) {
//			errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
//		}
	}

}
