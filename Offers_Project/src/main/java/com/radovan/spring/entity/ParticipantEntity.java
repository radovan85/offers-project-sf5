package com.radovan.spring.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "participants")
public class ParticipantEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	
	@OneToOne(cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id",unique = true)
	private UserEntity user;
	
	@OneToMany(cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.EAGER,
			mappedBy = "sender")
	@Fetch(FetchMode.SUBSELECT)
	private List<MessageEntity> sentMessages;
	
	@OneToMany(cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.EAGER,
			mappedBy = "receiver")
	private List<MessageEntity> receivedMessages;
	
	@Column(name = "full_name")
	private String fullName;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public List<MessageEntity> getSentMessages() {
		return sentMessages;
	}

	public void setSentMessages(List<MessageEntity> sentMessages) {
		this.sentMessages = sentMessages;
	}

	public List<MessageEntity> getReceivedMessages() {
		return receivedMessages;
	}

	public void setReceivedMessages(List<MessageEntity> receivedMessages) {
		this.receivedMessages = receivedMessages;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	
	
	

}
