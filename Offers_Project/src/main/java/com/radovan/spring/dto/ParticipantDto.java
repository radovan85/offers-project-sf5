package com.radovan.spring.dto;

import java.io.Serializable;
import java.util.List;



public class ParticipantDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

	private Integer userId;
	
	private List<Integer> receivedMessagesIds;
	
	private List<Integer> sentMessagesIds;
	
	private String fullName;
	
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	

	public List<Integer> getReceivedMessagesIds() {
		return receivedMessagesIds;
	}

	public void setReceivedMessagesIds(List<Integer> receivedMessagesIds) {
		this.receivedMessagesIds = receivedMessagesIds;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public List<Integer> getSentMessagesIds() {
		return sentMessagesIds;
	}

	public void setSentMessagesIds(List<Integer> sentMessagesIds) {
		this.sentMessagesIds = sentMessagesIds;
	}
	
	

	
	
	
	
	
	
	

	
	
	

}
