package com.formos.stripe.web.rest.errors;

public class EmailDoesNotExistException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public EmailDoesNotExistException() {
        super(ErrorConstants.INVALID_EMAIL_TYPE, "Email does not exist", "", "emailNotExist");
    }
}
