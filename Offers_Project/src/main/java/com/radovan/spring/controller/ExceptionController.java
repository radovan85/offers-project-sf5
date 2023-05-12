package com.radovan.spring.controller;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.radovan.spring.exception.ExistingEmailException;
import com.radovan.spring.exception.SuspendedUserException;



@ControllerAdvice
public class ExceptionController {

	
	@ExceptionHandler(SuspendedUserException.class)
	public String handleSuspendedUserException(SuspendedUserException ex,RedirectAttributes redirectAttributes) {
		SecurityContextHolder.clearContext();
		redirectAttributes.addFlashAttribute("message","Account Suspended");
		SecurityContextHolder.clearContext();
		return "redirect:/login";
	}
	
	
	@ExceptionHandler(ExistingEmailException.class)
	public String handleExistingEmailException(ExistingEmailException ex,RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("message","Email already exists!!!");
		return "redirect:/newaccount";
	}
	
}
