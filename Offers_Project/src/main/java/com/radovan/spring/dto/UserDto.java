package com.radovan.spring.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;





public class UserDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	@NotEmpty(message="Provide first name!")
	private String firstName;
	
	@NotEmpty(message="Provide last name")
	private String lastName;
	
	
	@Email(regexp = ".+[@].+[\\.].+",message = "Provide valid email")
	private String email;
	
	@NotEmpty(message="Provide password")
	private String password;
	
	
	private List<Integer> rolesIds;
	
	private byte enabled;
	
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public List<Integer> getRolesIds() {
		return rolesIds;
	}

	public void setRolesIds(List<Integer> rolesIds) {
		this.rolesIds = rolesIds;
	}

	public byte getEnabled() {
		return enabled;
	}

	public void setEnabled(byte enabled) {
		this.enabled = enabled;
	}
	
	
}
