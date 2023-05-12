package com.radovan.spring.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "messages")
public class MessageEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	
	private String subject;

	
	private String text;

	@ManyToOne
	@JoinColumn(name = "sender_id")
	private ParticipantEntity sender;

	@ManyToOne
	@JoinColumn(name = "receiver_id")
	private ParticipantEntity receiver;

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

	public ParticipantEntity getSender() {
		return sender;
	}

	public void setSender(ParticipantEntity sender) {
		this.sender = sender;
	}

	public ParticipantEntity getReceiver() {
		return receiver;
	}

	public void setReceiver(ParticipantEntity receiver) {
		this.receiver = receiver;
	}
	
	

}
