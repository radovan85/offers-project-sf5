package com.radovan.spring.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.radovan.spring.dto.UserDto;
import com.radovan.spring.entity.UserEntity;
import com.radovan.spring.exception.ExistingEmailException;
import com.radovan.spring.service.UserService;

@Controller
public class LoginController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(ModelMap map, UserEntity user) {

		map.put("user", user);
		return "login";
	}

	@RequestMapping("/denied")
	public String showDenied() {
		return "denied";
	}

	@RequestMapping("/messages")
	public String showMessages() {
		return "messages";
	}

	@RequestMapping("/admin")
	public String showAdmin(ModelMap map) {

		List<UserEntity> users = userService.listAll();

		map.put("users", users);

		return "admin";
	}

	@RequestMapping("/newaccount")
	public String showNewAccount(ModelMap map) {

		map.put("user", new UserDto());
		return "newaccount";
	}

	@RequestMapping(value = "/createaccount", method = RequestMethod.POST)
	public String createAccount(@Validated @ModelAttribute("user") UserDto user, Errors errors, ModelMap map)
			throws ExistingEmailException {

		if (errors.hasErrors()) {
			return "newaccount";
		}

		Optional<List<UserDto>> allUsers = Optional.ofNullable(userService.listAllUsers());
		String email = user.getEmail();
		if (allUsers.isPresent()) {
			for (UserDto tempUser : allUsers.get()) {
				if (email.equalsIgnoreCase(tempUser.getEmail())) {
					Error error = new Error("Email Exists Already");
					throw new ExistingEmailException(error);
				}
			}
		}

		map.put("user", user);
		userService.addUser(user);
		return "accountcreated";
	}
}
