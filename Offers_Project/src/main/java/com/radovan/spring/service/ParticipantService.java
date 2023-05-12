package com.radovan.spring.service;

import java.util.List;

import com.radovan.spring.dto.ParticipantDto;

public interface ParticipantService {

	
	ParticipantDto getParticipant(Integer participantId);
	
	List<ParticipantDto> listAll();
	
	ParticipantDto getParticipantByUserId(Integer userId);
	
	
	
}
