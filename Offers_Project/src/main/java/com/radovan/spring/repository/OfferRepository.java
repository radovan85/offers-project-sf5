package com.radovan.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.radovan.spring.entity.OfferEntity;

@Repository
public interface OfferRepository extends JpaRepository<OfferEntity, Integer> {

	List<OfferEntity> findAllByParticipantId(Integer participantId);
	
	@Modifying
	@Query(value = "delete from offers where participant_id = :participantId",nativeQuery = true)
	void deleteAllByParticipantId(@Param ("participantId") Integer participantId);
}
