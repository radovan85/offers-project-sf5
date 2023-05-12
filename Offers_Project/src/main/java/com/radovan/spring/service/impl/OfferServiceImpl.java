package com.radovan.spring.service.impl;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.radovan.spring.converter.TempConverter;
import com.radovan.spring.dto.OfferDto;
import com.radovan.spring.entity.OfferEntity;
import com.radovan.spring.entity.ParticipantEntity;
import com.radovan.spring.entity.UserEntity;
import com.radovan.spring.repository.OfferRepository;
import com.radovan.spring.repository.ParticipantRepository;
import com.radovan.spring.repository.UserRepository;
import com.radovan.spring.service.OfferService;

@Service("offerServiceHandler")
@Transactional
public class OfferServiceImpl implements OfferService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private OfferRepository offerRepository;

	@Autowired
	private TempConverter tempConverter;

	@Autowired
	private ParticipantRepository participantRepository;

	@Override
	public OfferDto addOffer(OfferDto offer, Principal principal) {
		// TODO Auto-generated method stub
		UserEntity authUser = userRepository.findByEmail(principal.getName());
		ParticipantEntity participantEntity = participantRepository.findByUserId(authUser.getId());
		offer.setParticipantId(participantEntity.getId());
		OfferEntity offerEntity = tempConverter.offerDtoToEntity(offer);
		offerEntity.setCreatorName(participantEntity.getFullName());
		OfferEntity storedOffer = offerRepository.save(offerEntity);
		OfferDto returnValue = tempConverter.offerEntityToDto(storedOffer);

		return returnValue;
	}

	@Override
	public OfferDto getOffer(Integer id) {
		// TODO Auto-generated method stub
		OfferEntity offerEntity = offerRepository.getById(id);
		OfferDto returnValue = tempConverter.offerEntityToDto(offerEntity);
		return returnValue;
	}

	@Override
	public OfferDto updateOffer(Integer id, OfferDto offer) {
		// TODO Auto-generated method stub
		OfferEntity tempOffer = offerRepository.getById(id);
		OfferEntity offerEntity = tempConverter.offerDtoToEntity(offer);
		offerEntity.setId(id);
		offerEntity.setCreatorName(tempOffer.getCreatorName());
		OfferEntity updatedOffer = offerRepository.saveAndFlush(offerEntity);
		OfferDto returnValue = tempConverter.offerEntityToDto(updatedOffer);
		return returnValue;
	}

	@Override
	public List<OfferDto> listAll() {
		// TODO Auto-generated method stub
		List<OfferEntity> allOffers = offerRepository.findAll();
		List<OfferDto> returnValue = new ArrayList<OfferDto>();

		for (OfferEntity offerEntity : allOffers) {
			OfferDto offerDto = tempConverter.offerEntityToDto(offerEntity);
			returnValue.add(offerDto);
		}
		return returnValue;
	}

	@Override
	public void deleteOffer(Integer id) {
		// TODO Auto-generated method stub
		offerRepository.deleteById(id);
		offerRepository.flush();

	}

	@Override
	public List<OfferDto> listAllByParticipantId(Integer participantId) {
		// TODO Auto-generated method stub
		Optional<List<OfferEntity>> allOffers = Optional
				.ofNullable(offerRepository.findAllByParticipantId(participantId));
		List<OfferDto> returnValue = new ArrayList<OfferDto>();

		if (!allOffers.isEmpty()) {
			for (OfferEntity offerEntity : allOffers.get()) {
				OfferDto offerDto = tempConverter.offerEntityToDto(offerEntity);
				returnValue.add(offerDto);
			}
		}
		return returnValue;
	}

	@Override
	public Boolean hasOffer(Integer participantId) {
		// TODO Auto-generated method stub
		if (participantId == null) {
			return false;
		}

		List<OfferEntity> offers = offerRepository.findAllByParticipantId(participantId);

		if (offers.size() == 0) {
			return false;
		}

		return true;
	}

}
