package com.sas.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sas.service.SecurityService;
import com.sas.service.UsersService;
import com.sas.validator.UserValidator;

@Controller
public class UsersController {
	private Logger logger = (Logger) LoggerFactory.getLogger(UsersController.class);
	@Autowired
	private UsersService usersService;
//
@Autowired
private SecurityService securityService;
//
@Autowired
private UserValidator userValidator;
//
@RequestMapping(value = "/login", method = RequestMethod.GET)
public String login(Model model, String error, String logout) {
    if (error != null)
        model.addAttribute("error", "Your username and password is invalid.");

    if (logout != null)
        model.addAttribute("message", "You have been logged out successfully.");

    return "login";
}

@RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
public String welcome(Model model) {
    return "welcome";
}

}
