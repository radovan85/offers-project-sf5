package com.radovan.spring.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.radovan.spring.dto.MessageDto;
import com.radovan.spring.dto.OfferDto;
import com.radovan.spring.dto.ParticipantDto;
import com.radovan.spring.entity.UserEntity;
import com.radovan.spring.service.MessageService;
import com.radovan.spring.service.OfferService;
import com.radovan.spring.service.ParticipantService;
import com.radovan.spring.service.UserService;

@Controller
public class OfferController {
	
	@Autowired
	private OfferService offerService;
	
	@Autowired
	private ParticipantService participantServce;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MessageService messageService;

	@RequestMapping("/createoffer")
	public String createOffer(ModelMap map,Principal principal) {

		UserEntity authUser = userService.getUserByEmail(principal.getName());
		ParticipantDto participant = participantServce.getParticipantByUserId(authUser.getId());
		List<MessageDto> allMessages = messageService.listAllByReceiverId(participant.getId());
		OfferDto offer = new OfferDto();
		
		map.put("offer", offer);
		map.put("participant", participant);
		map.put("allMessages", allMessages);

		return "createoffer";
	}

	
	@RequestMapping(value = "/docreate", method = RequestMethod.POST)
	public String doCreate(@Validated @ModelAttribute("offer") OfferDto offer, Errors errors,
			ModelMap map,Principal principal) {
		
		UserEntity authUser = userService.getUserByEmail(principal.getName());
		ParticipantDto participant = participantServce.getParticipantByUserId(authUser.getId());
		map.put("participant", participant);
		List<MessageDto> allMessages = messageService.listAllByReceiverId(participant.getId());
		map.put("allMessages", allMessages);
		
		
		if(errors.hasErrors()) {
			return "createoffer";
		}

		offerService.addOffer(offer,principal);

		return "offercreated";
	}
	
	
	@RequestMapping(value = "/deleteOffer/{offerId}",method = RequestMethod.GET)
	public String deleteOffer(@PathVariable ("offerId") Integer offerId) {
		
		offerService.deleteOffer(offerId);
		return "redirect:/";
	}
	
	@RequestMapping(value="/updateOffer/{offerId}",method = RequestMethod.GET)
	public String updateOfferForm(@PathVariable ("offerId") Integer offerId,
			ModelMap map,Principal principal) {
		
		UserEntity authUser = userService.getUserByEmail(principal.getName());
		ParticipantDto participant = participantServce.getParticipantByUserId(authUser.getId());
		List<MessageDto> allMessages = messageService.listAllByReceiverId(participant.getId());
		
		OfferDto offer = new OfferDto();
		OfferDto currentOffer = offerService.getOffer(offerId);
		map.put("offer", offer);
		map.put("currentOffer", currentOffer);
		map.put("participant", participant);
		map.put("allMessages", allMessages);
		return "updateoffer";
	}
	
	
	@RequestMapping(value="/updateOffer/{offerId}",method = RequestMethod.POST)
	public String updateOffer(@PathVariable ("offerId") Integer offerId,@Validated @ModelAttribute ("offer") OfferDto offer,
			Errors errors,Principal principal,ModelMap map) {
		
		UserEntity authUser = userService.getUserByEmail(principal.getName());
		ParticipantDto participant = participantServce.getParticipantByUserId(authUser.getId());
		List<MessageDto> allMessages = messageService.listAllByReceiverId(participant.getId());
		map.put("allMessages", allMessages);
			
		
		offer.setParticipantId(participant.getId());
		map.put("participant", participant);
		
		if(errors.hasErrors()) {
			OfferDto currentOffer = offerService.getOffer(offerId);
			map.put("currentOffer", currentOffer);
			return "updateoffer";
		}
		
		offerService.updateOffer(offerId, offer);
		return "offerupdated";
	}
	
	
	@RequestMapping(value="/currentOffers/{participantId}",method = RequestMethod.GET)
	public String currentOffers(@PathVariable ("participantId") Integer participantId,ModelMap map,Principal principal) {
		
		UserEntity authUser = userService.getUserByEmail(principal.getName());
		ParticipantDto participant = participantServce.getParticipantByUserId(authUser.getId());
		List<OfferDto> offers = offerService.listAllByParticipantId(participantId);
		
		map.put("offers", offers);
		map.put("participant", participant);
		return "current_offers";
	}
}
