package com.google.code.lightssh.project.security.service;

import com.google.code.lightssh.common.ApplicationException;

/**
 * security exception 
 * @author YangXiaojin
 *
 */
public class SecurityException extends ApplicationException{
	
	private static final long serialVersionUID = 468787161436809205L;

	public SecurityException() {
		super();
	}

	public SecurityException(String message, Throwable cause) {
		super(message, cause);
	}

	public SecurityException(String message) {
		super(message);
	}

	public SecurityException(Throwable cause) {
		super(cause);
	}

}
