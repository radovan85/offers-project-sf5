package com.radovan.spring.controller;



import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.radovan.spring.dto.MessageDto;
import com.radovan.spring.dto.ParticipantDto;
import com.radovan.spring.entity.UserEntity;
import com.radovan.spring.service.ParticipantService;
import com.radovan.spring.service.UserService;



@Controller
public class MessageMvcController {
	
	
	@Autowired
	private ParticipantService participantService;
	
	@Autowired
	private UserService usersService;

	
	@RequestMapping(value = "/sendMessage/{receiverId}", method = RequestMethod.GET)
	public String renderMessageForm(@PathVariable("receiverId") Integer receiverId, ModelMap map,
			Principal principal) {

		
		MessageDto messageDto = new MessageDto();

		messageDto.setReceiverId(receiverId);
		UserEntity authUser = usersService.getUserByEmail(principal.getName());
		ParticipantDto receiver = participantService.getParticipant(receiverId);
		ParticipantDto sender = participantService.getParticipantByUserId(authUser.getId());
		messageDto.setSenderId(sender.getId());
		messageDto.setSenderName(sender.getFullName());
		map.put("senderName", sender.getFullName());
		map.put("receiverName", receiver.getFullName());
		map.put("message", messageDto);
		map.put("senderId", sender.getId());
		map.put("receiverId", receiver.getId());
		
		
		return "message_form";

	} 
	
	
	
	
	@RequestMapping(value = "/getMessages/{receiverId}",method = RequestMethod.GET)
	public String getMessages(@PathVariable ("receiverId") Integer receiverId) {
		
		return "redirect:/messageFlow?receiverId=" + receiverId;
	}
}
