package com.radovan.spring.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.radovan.spring.converter.TempConverter;
import com.radovan.spring.dto.MessageDto;
import com.radovan.spring.entity.MessageEntity;
import com.radovan.spring.repository.MessageRepository;
import com.radovan.spring.service.MessageService;

@Service("messageServiceHandler")
@Transactional
public class MessageServiceImpl implements MessageService {

	@Autowired
	private MessageRepository messageRepository;

	@Autowired
	private TempConverter tempConverter;

	@Override
	public MessageDto addMessage(MessageDto message, Integer senderId, Integer receiverId) {
		// TODO Auto-generated method stub

		message.setSenderId(senderId);
		message.setReceiverId(receiverId);
		MessageEntity messageEntity = tempConverter.messageDtoToEntity(message);
		MessageEntity storedMessage = messageRepository.save(messageEntity);
		MessageDto returnValue = tempConverter.messageEntityToDto(storedMessage);
		return returnValue;
	}

	@Override
	public MessageDto replyMessage(MessageDto message, Integer senderId, Integer receiverId) {
		// TODO Auto-generated method stub

		message.setReceiverId(senderId);
		message.setSenderId(receiverId);
		MessageEntity messageEntity = tempConverter.messageDtoToEntity(message);
		MessageEntity storedMessage = messageRepository.save(messageEntity);
		MessageDto returnValue = tempConverter.messageEntityToDto(storedMessage);
		return returnValue;
	}

	@Override
	public MessageDto getMessage(Integer id) {
		// TODO Auto-generated method stub
		MessageEntity messageEntity = messageRepository.getById(id);
		MessageDto returnValue = tempConverter.messageEntityToDto(messageEntity);
		return returnValue;
	}

	@Override
	public List<MessageDto> listAll() {
		// TODO Auto-generated method stub
		List<MessageEntity> allMessages = messageRepository.findAll();
		List<MessageDto> returnValue = new ArrayList<MessageDto>();

		for (MessageEntity message : allMessages) {
			MessageDto messageDto = tempConverter.messageEntityToDto(message);
			returnValue.add(messageDto);
		}
		return returnValue;
	}

	@Override
	public List<MessageDto> listAllByReceiverId(Integer receiverId) {
		// TODO Auto-generated method stub
		Optional<List<MessageEntity>> allMessages = Optional
				.ofNullable(messageRepository.findAllByReceiverId(receiverId));
		List<MessageDto> returnValue = new ArrayList<MessageDto>();

		if (!allMessages.isEmpty()) {
			for (MessageEntity message : allMessages.get()) {
				MessageDto messageDto = tempConverter.messageEntityToDto(message);
				returnValue.add(messageDto);
			}
		}
		return returnValue;
	}

	@Override
	public List<MessageDto> listAllBySenderId(Integer senderId) {
		// TODO Auto-generated method stub
		Optional<List<MessageEntity>> allMessages = Optional.ofNullable(messageRepository.findAllBySenderId(senderId));
		List<MessageDto> returnValue = new ArrayList<MessageDto>();

		if (!allMessages.isEmpty()) {
			for (MessageEntity message : allMessages.get()) {
				MessageDto messageDto = tempConverter.messageEntityToDto(message);
				returnValue.add(messageDto);
			}
		}
		return returnValue;
	}

	@Override
	public void deleteMessage(Integer id) {
		// TODO Auto-generated method stub
		messageRepository.removeMessageById(id);
		messageRepository.flush();
	}

	@Override
	public void removeAllByReceiverId(Integer receiverId) {
		// TODO Auto-generated method stub
		messageRepository.removeAllByReceiverId(receiverId);
		messageRepository.flush();
	}

}
