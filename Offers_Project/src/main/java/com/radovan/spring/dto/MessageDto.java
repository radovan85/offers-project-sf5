package com.radovan.spring.dto;

import java.io.Serializable;

import javax.validation.constraints.Size;

public class MessageDto implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	@Size(min = 5, max = 100,message = "Minimum 5 letters")
	private String subject;
	
	@Size(min = 5, max = 1000,message = "Minimum 5 letters")
	private String text;

	private Integer senderId;
	
	private Integer receiverId;
	
	private String senderName;
	
	private String receiverName;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getSenderId() {
		return senderId;
	}

	public void setSenderId(Integer senderId) {
		this.senderId = senderId;
	}

	public Integer getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(Integer receiverId) {
		this.receiverId = receiverId;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	@Override
	public String toString() {
		return "MessageDto [id=" + id + ", subject=" + subject + ", text=" + text + ", senderId=" + senderId
				+ ", receiverId=" + receiverId + ", senderName=" + senderName + ", receiverName=" + receiverName + "]";
	}
	
	
	

	
	
	


	

	
	

	
	
	

	
	
	
	
	
	
	
	
}
