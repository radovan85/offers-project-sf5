package com.radovan.spring.service;

import java.security.Principal;
import java.util.List;

import com.radovan.spring.dto.OfferDto;

public interface OfferService {

	OfferDto addOffer(OfferDto offer, Principal principal);

	OfferDto getOffer(Integer id);

	OfferDto updateOffer(Integer id, OfferDto offer);

	List<OfferDto> listAll();

	void deleteOffer(Integer id);

	Boolean hasOffer(Integer userId);

	List<OfferDto> listAllByParticipantId(Integer participantId);

}
