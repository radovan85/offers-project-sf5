package com.radovan.spring.exception;

import javax.management.RuntimeErrorException;

public class ExistingEmailException extends RuntimeErrorException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExistingEmailException(Error e) {
		super(e);
		// TODO Auto-generated constructor stub
	}

}
