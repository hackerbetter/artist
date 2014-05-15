package com.hackerbetter.artistcore.exception;


import com.hackerbetter.artistcore.constants.ErrorCode;

public class ArtistException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private ErrorCode errorCode;
	
	public ArtistException(String msg) {
		super(msg);
	}
	
	public ArtistException(String msg, Throwable e) {
		super(msg, e);
	}
	
	public ArtistException(ErrorCode errorCode) {
		super(errorCode.memo);
		this.errorCode = errorCode;
	}
	
	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}
}
