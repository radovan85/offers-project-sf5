package com.radovan.spring.service;


import java.util.List;

import com.radovan.spring.dto.MessageDto;



public interface MessageService {

	MessageDto addMessage(MessageDto message,Integer senderId,Integer receiverId);
	
	MessageDto getMessage(Integer id);
	
	List<MessageDto> listAll();
	
	List<MessageDto> listAllByReceiverId(Integer receiverId);
	
	List<MessageDto> listAllBySenderId(Integer senderId);
	
	void deleteMessage(Integer id);
	
	MessageDto replyMessage(MessageDto message,Integer senderId,Integer receiverId);
	
	void removeAllByReceiverId(Integer receiverId);
	
}
