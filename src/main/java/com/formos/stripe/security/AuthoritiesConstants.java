package com.formos.stripe.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String HR = "ROLE_HR";

    public static final String PM = "ROLE_PM";

    public static final String USER = "ROLE_USER";

    public static final String TECH_LEAD = "ROLE_TECH_LEAD";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    private AuthoritiesConstants() {}
}
