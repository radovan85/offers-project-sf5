package com.radovan.spring.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.radovan.spring.dto.MessageDto;
import com.radovan.spring.dto.OfferDto;
import com.radovan.spring.dto.ParticipantDto;
import com.radovan.spring.dto.RoleDto;
import com.radovan.spring.dto.UserDto;
import com.radovan.spring.entity.MessageEntity;
import com.radovan.spring.entity.OfferEntity;
import com.radovan.spring.entity.ParticipantEntity;
import com.radovan.spring.entity.RoleEntity;
import com.radovan.spring.entity.UserEntity;
import com.radovan.spring.repository.MessageRepository;
import com.radovan.spring.repository.ParticipantRepository;
import com.radovan.spring.repository.RoleRepository;
import com.radovan.spring.repository.UserRepository;

public class TempConverter {

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private ParticipantRepository participantRepository;

	@Autowired
	private MessageRepository messageRepository;

	public UserDto userEntityToDto(UserEntity userEntity) {
		UserDto returnValue = mapper.map(userEntity, UserDto.class);
		Optional<List<RoleEntity>> roles = Optional.ofNullable(userEntity.getRoles());
		List<Integer> rolesIds = new ArrayList<Integer>();

		if (!roles.isEmpty()) {
			for (RoleEntity roleEntity : roles.get()) {
				rolesIds.add(roleEntity.getId());
			}
		}

		returnValue.setRolesIds(rolesIds);

		return returnValue;
	}

	public UserEntity userDtoToEntity(UserDto userDto) {
		UserEntity returnValue = mapper.map(userDto, UserEntity.class);
		List<RoleEntity> roles = new ArrayList<>();
		Optional<List<Integer>> rolesIds = Optional.ofNullable(userDto.getRolesIds());

		if (!rolesIds.isEmpty()) {
			for (Integer roleId : rolesIds.get()) {
				RoleEntity role = roleRepository.getById(roleId);
				roles.add(role);
			}
		}

		returnValue.setRoles(roles);

		return returnValue;
	}

	public RoleDto roleEntityToDto(RoleEntity roleEntity) {
		RoleDto returnValue = mapper.map(roleEntity, RoleDto.class);
		Optional<List<UserEntity>> users = Optional.ofNullable(roleEntity.getUsers());
		List<Integer> userIds = new ArrayList<>();

		if (!users.isEmpty()) {
			for (UserEntity user : users.get()) {
				userIds.add(user.getId());
			}
		}

		returnValue.setUserIds(userIds);
		return returnValue;
	}

	public RoleEntity roleDtoToEntity(RoleDto roleDto) {
		RoleEntity returnValue = mapper.map(roleDto, RoleEntity.class);
		Optional<List<Integer>> usersIds = Optional.ofNullable(roleDto.getUserIds());
		List<UserEntity> users = new ArrayList<>();

		if (!usersIds.isEmpty()) {
			for (Integer userId : usersIds.get()) {
				UserEntity userEntity = userRepository.getById(userId);
				users.add(userEntity);
			}
		}
		returnValue.setUsers(users);
		return returnValue;
	}

	public ParticipantDto participantEntityToDto(ParticipantEntity participantEntity) {

		ParticipantDto returnValue = mapper.map(participantEntity, ParticipantDto.class);
		Optional<UserEntity> user = Optional.ofNullable(participantEntity.getUser());
		if (user.isPresent()) {
			returnValue.setUserId(user.get().getId());

		}

		Optional<List<MessageEntity>> receivedMessages = Optional.ofNullable(participantEntity.getReceivedMessages());
		List<Integer> receivedMessagesIds = new ArrayList<>();
		if (receivedMessages.isPresent()) {
			for (MessageEntity message : receivedMessages.get()) {
				receivedMessagesIds.add(message.getId());
			}
		}

		Optional<List<MessageEntity>> sentMessages = Optional.ofNullable(participantEntity.getSentMessages());
		List<Integer> sentMessagesIds = new ArrayList<>();
		if (sentMessages.isPresent()) {
			for (MessageEntity message : sentMessages.get()) {
				sentMessagesIds.add(message.getId());
			}
		}

		returnValue.setReceivedMessagesIds(receivedMessagesIds);
		returnValue.setSentMessagesIds(sentMessagesIds);
		return returnValue;
	}

	public ParticipantEntity participantDtoToEntity(ParticipantDto participantDto) {
		ParticipantEntity returnValue = mapper.map(participantDto, ParticipantEntity.class);
		List<MessageEntity> sentMessages = new ArrayList<>();
		List<MessageEntity> receivedMessages = new ArrayList<>();

		Optional<Integer> userId = Optional.ofNullable(participantDto.getUserId());
		if (userId.isPresent()) {
			UserEntity userEntity = userRepository.getById(userId.get());
			returnValue.setUser(userEntity);

		}

		Optional<List<Integer>> sentMessageIds = Optional.ofNullable(participantDto.getSentMessagesIds());
		if (!sentMessageIds.isEmpty()) {
			for (Integer messageId : sentMessageIds.get()) {
				MessageEntity messageEntity = messageRepository.getById(messageId);
				sentMessages.add(messageEntity);
			}
		}

		Optional<List<Integer>> receivedMessageIds = Optional.ofNullable(participantDto.getReceivedMessagesIds());
		if (!receivedMessageIds.isEmpty()) {
			for (Integer messageId : receivedMessageIds.get()) {
				MessageEntity messageEntity = messageRepository.getById(messageId);
				receivedMessages.add(messageEntity);
			}
		}

		returnValue.setReceivedMessages(receivedMessages);
		returnValue.setSentMessages(sentMessages);
		return returnValue;
	}

	public OfferDto offerEntityToDto(OfferEntity offerEntity) {
		OfferDto returnValue = mapper.map(offerEntity, OfferDto.class);
		Optional<ParticipantEntity> participantEntity = Optional.ofNullable(offerEntity.getParticipant());
		if (participantEntity.isPresent()) {
			returnValue.setParticipantId(participantEntity.get().getId());

		}

		return returnValue;

	}

	public OfferEntity offerDtoToEntity(OfferDto offerDto) {
		OfferEntity returnValue = mapper.map(offerDto, OfferEntity.class);
		Optional<Integer> participantId = Optional.ofNullable(offerDto.getParticipantId());
		if (participantId.isPresent()) {
			ParticipantEntity participantEntity = participantRepository.getById(participantId.get());
			returnValue.setParticipant(participantEntity);
		}
		return returnValue;
	}

	public MessageDto messageEntityToDto(MessageEntity messageEntity) {
		MessageDto returnValue = mapper.map(messageEntity, MessageDto.class);

		Optional<ParticipantEntity> sender = Optional.ofNullable(messageEntity.getSender());
		if (sender.isPresent()) {
			Integer senderId = sender.get().getId();
			String senderName = sender.get().getFullName();

			returnValue.setSenderId(senderId);
			returnValue.setSenderName(senderName);
		}

		Optional<ParticipantEntity> receiver = Optional.ofNullable(messageEntity.getReceiver());
		if (receiver.isPresent()) {
			Integer receiverId = receiver.get().getId();
			String receiverName = receiver.get().getFullName();

			returnValue.setReceiverId(receiverId);
			returnValue.setReceiverName(receiverName);
		}

		return returnValue;
	}

	public MessageEntity messageDtoToEntity(MessageDto messageDto) {
		MessageEntity returnValue = mapper.map(messageDto, MessageEntity.class);
		Optional<Integer> senderId = Optional.ofNullable(messageDto.getSenderId());
		Optional<Integer> receiverId = Optional.ofNullable(messageDto.getReceiverId());

		if (senderId.isPresent()) {
			ParticipantEntity sender = participantRepository.getById(senderId.get());
			returnValue.setSender(sender);
		}

		if (receiverId.isPresent()) {
			ParticipantEntity receiver = participantRepository.getById(receiverId.get());
			returnValue.setReceiver(receiver);
		}
		return returnValue;
	}
}
