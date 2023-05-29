package com.formos.stripe.web.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class UserNotFoundException extends AbstractThrowableProblem {

    public UserNotFoundException(Long id) {
        super(ErrorConstants.USER_NOT_FOUND, String.format("User '%s' not found", id), Status.BAD_REQUEST);
    }
}
