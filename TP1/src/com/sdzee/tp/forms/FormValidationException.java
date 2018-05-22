package com.sdzee.tp.forms;

public class FormValidationException extends Exception {

	public FormValidationException( String message ) {
		super( message );
	}

	public FormValidationException( String message, Throwable e ) {
		super( message, e );
	}

	public FormValidationException( Throwable e ) {
		super( e );
	}

}
