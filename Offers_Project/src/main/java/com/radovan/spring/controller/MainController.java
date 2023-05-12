package com.radovan.spring.controller;

import java.security.Principal;
import java.util.List;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.radovan.spring.dto.MessageDto;
import com.radovan.spring.dto.OfferDto;
import com.radovan.spring.dto.ParticipantDto;
import com.radovan.spring.entity.UserEntity;
import com.radovan.spring.exception.SuspendedUserException;
import com.radovan.spring.service.MessageService;
import com.radovan.spring.service.OfferService;
import com.radovan.spring.service.ParticipantService;
import com.radovan.spring.service.UserService;
import com.radovan.spring.service.impl.UserDetailsImpl;

@Controller
public class MainController {

	@Autowired
	private UserDetailsImpl userDetailsService;

	@Autowired
	private ParticipantService participantService;

	@Autowired
	private MessageService messageService;

	@Autowired
	private UserService userService;

	@Autowired
	private OfferService offerService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String showHome(ModelMap map, Principal principal) throws RuntimeErrorException {

		List<OfferDto> offers = offerService.listAll();

		UserEntity authUser = userDetailsService.loadUserByUsername(principal.getName());
		ParticipantDto participant = participantService.getParticipantByUserId(authUser.getId());
		List<MessageDto> allMessages = messageService.listAllByReceiverId(participant.getId());

		map.put("offers", offers);
		map.put("allMessages", allMessages);
		map.put("authUser", authUser);
		map.put("participant", participant);

		Boolean hasOffer = false;

		if (authUser != null) {
			hasOffer = offerService.hasOffer(authUser.getId());
		}

		if (authUser.getEnabled() == (byte) 0) {
			Error error = new Error("Account suspended");
			throw new SuspendedUserException(error);
		}

		map.put("hasOffer", hasOffer);

		return "home";
	}

	@RequestMapping(value = "/admin/offers", method = RequestMethod.GET)
	public String adminReview(ModelMap map) {
		List<OfferDto> offers = offerService.listAll();
		map.put("offers", offers);
		return "admin_offers";
	}

	@RequestMapping(value = "/admin/suspendUser/{participantId}", method = RequestMethod.GET)
	public String suspendUser(@PathVariable("participantId") Integer participantId,
			RedirectAttributes redirectAttributes) {
		System.out.println("Suspending participant with id: " + participantId);
		userService.suspendUser(participantId);
		redirectAttributes.addFlashAttribute("message", "User has been suspended!");
		return "redirect:/admin";
	}

}
