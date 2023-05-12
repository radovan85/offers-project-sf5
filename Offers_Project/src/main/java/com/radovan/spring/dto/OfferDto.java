package com.radovan.spring.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class OfferDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	@NotEmpty
	private String name;
	
	@Size(min=5, max=255)
	private String text;
	
	private Integer participantId;
	
	private String creatorName;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getParticipantId() {
		return participantId;
	}

	public void setParticipantId(Integer participantId) {
		this.participantId = participantId;
	}

	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	
	

	

	
	
	
	

	

	

	

	
	
	
}
