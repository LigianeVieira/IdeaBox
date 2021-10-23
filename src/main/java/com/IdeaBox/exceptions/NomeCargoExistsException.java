package com.IdeaBox.exceptions;

public class NomeCargoExistsException extends Exception{

	
	private static final long serialVersionUID = 1L;

	public NomeCargoExistsException(String msg) {
		super(msg);
	}
}
