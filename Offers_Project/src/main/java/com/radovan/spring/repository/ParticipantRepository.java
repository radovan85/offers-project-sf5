package com.radovan.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.radovan.spring.entity.ParticipantEntity;

@Repository
public interface ParticipantRepository extends JpaRepository<ParticipantEntity, Integer> {

	ParticipantEntity findByUserId(Integer userId);
	
	
}
