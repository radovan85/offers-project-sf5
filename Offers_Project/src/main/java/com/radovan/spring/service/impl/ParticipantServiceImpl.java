package com.radovan.spring.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.radovan.spring.converter.TempConverter;
import com.radovan.spring.dto.ParticipantDto;
import com.radovan.spring.entity.ParticipantEntity;
import com.radovan.spring.repository.ParticipantRepository;
import com.radovan.spring.service.ParticipantService;

@Service("participantServiceHandler")
@Transactional
public class ParticipantServiceImpl implements ParticipantService{

	@Autowired
	private ParticipantRepository participantRepository;
	
	@Autowired
	private TempConverter tempConverter;

	@Override
	public ParticipantDto getParticipant(Integer participantId) {
		// TODO Auto-generated method stub
		ParticipantEntity participantEntity = participantRepository.getById(participantId);
		ParticipantDto returnValue = tempConverter.participantEntityToDto(participantEntity);
		return returnValue;
	}

	@Override
	public List<ParticipantDto> listAll() {
		// TODO Auto-generated method stub
		List<ParticipantEntity> allParticipants = participantRepository.findAll();
		List<ParticipantDto> returnValue = new ArrayList<ParticipantDto>();
		
		for(ParticipantEntity participant:allParticipants) {
			ParticipantDto participantDto = tempConverter.participantEntityToDto(participant);
			returnValue.add(participantDto);
		}
		return returnValue;
	}

	@Override
	public ParticipantDto getParticipantByUserId(Integer userId) {
		// TODO Auto-generated method stub
		ParticipantEntity participant = participantRepository.findByUserId(userId);
		ParticipantDto returnValue = tempConverter.participantEntityToDto(participant);
		return returnValue;
	}

	

}
