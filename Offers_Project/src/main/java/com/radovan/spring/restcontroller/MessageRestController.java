package com.radovan.spring.restcontroller;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.radovan.spring.dto.MessageDto;
import com.radovan.spring.service.MessageService;


@RestController
public class MessageRestController {
	
	@Autowired
	private MessageService messageService;

	
	@RequestMapping(value = "/allMessages", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<MessageDto>> getAllMessages(){
		List<MessageDto> allMessages = messageService.listAll();
		return ResponseEntity.ok().body(allMessages);
	}
	
	@RequestMapping(value = "/allMessages/{receiverId}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<MessageDto>> getAllMessagesByReceiverId(@PathVariable ("receiverId") Integer receiverId){
		List<MessageDto> allMessages = messageService.listAllByReceiverId(receiverId);
		return ResponseEntity.ok().body(allMessages);
	}
	
	
	@RequestMapping(value = "/getMessage/{messageId}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<MessageDto> getMessage(@PathVariable ("messageId") Integer messageId){
		MessageDto message = messageService.getMessage(messageId);
		return ResponseEntity.ok().body(message);
	}
	
	
	
	
	
	@RequestMapping(value = "/saveMessage/{senderId}/{receiverId}",method =RequestMethod.POST,consumes = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_JSON_VALUE }) 
	public ResponseEntity<?> saveMessage(@PathVariable ("receiverId") Integer receiverId,
			@PathVariable ("senderId") Integer senderId,@Validated @RequestBody MessageDto message){
		
		MessageDto storedMessage = messageService.addMessage(message,senderId,receiverId);
		return ResponseEntity.ok("Message saved with id " + storedMessage.getId());
	}
	
	
	@RequestMapping(value = "/replyMessage/{senderId}/{receiverId}",method =RequestMethod.POST,consumes = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_JSON_VALUE }) 
	public ResponseEntity<?> replyMessage(@PathVariable ("receiverId") Integer receiverId,
			@PathVariable ("senderId") Integer senderId,@Validated @RequestBody MessageDto message){
		
		
		MessageDto storedMessage = messageService.replyMessage(message,senderId,receiverId);
		return ResponseEntity.ok("Message saved with id " + storedMessage.getId());
	}
	
	
	
	
	@RequestMapping(value = "/deleteMessage/{messageId}",method = RequestMethod.DELETE,produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> deleteMessage(@PathVariable ("messageId") Integer messageId){
		messageService.deleteMessage(messageId);
		return ResponseEntity.ok("Deleted message with id: " + messageId);
	}
	
	
	@RequestMapping(value = "/deleteAllMessages/{receiverId}",method = RequestMethod.DELETE,produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> deleteAllMessages(@PathVariable ("receiverId") Integer receiverId){
		messageService.removeAllByReceiverId(receiverId);
		return ResponseEntity.ok("Deleted message with id: " + receiverId);
	}
	
	
	
}
