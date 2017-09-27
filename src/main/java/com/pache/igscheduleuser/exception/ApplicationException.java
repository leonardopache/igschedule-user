package com.pache.igscheduleuser.exception;

/**
 * Custon Exception to Application error.
 * Created by lpache on 7/22/17.
 */
public class ApplicationException extends RuntimeException {

	private static final long serialVersionUID = 5511276616825867260L;

    public ApplicationException(){
        super();
    }

    public ApplicationException(String message) {
        super(message);
    }
}
