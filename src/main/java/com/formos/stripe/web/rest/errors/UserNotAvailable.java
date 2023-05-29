package com.formos.stripe.web.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class UserNotAvailable extends AbstractThrowableProblem {

    public UserNotAvailable() {
        super(ErrorConstants.USER_NOT_AVAILABLE, "User is not available on this system", Status.BAD_REQUEST);
    }
}
