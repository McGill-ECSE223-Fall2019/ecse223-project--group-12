package ca.mcgill.ecse223.quoridor.controller;

public class InvalidMoveException extends RuntimeException  {
	
	private static final long serialVersionUID = -5633915762703837868L;
	
	public InvalidMoveException(String errorMessage) {
		super(errorMessage);
	}

}