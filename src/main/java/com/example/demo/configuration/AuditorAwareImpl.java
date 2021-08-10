package com.example.demo.configuration;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuditorAwareImpl implements AuditorAware<String> {

    public static final String ANONYMOUS_USER = "anonymousUser";

    @Override
    public Optional<String> getCurrentAuditor() {

        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || this.isAnonymousUser(authentication.getPrincipal())) {
            return Optional.of(ANONYMOUS_USER);
        } else {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            return Optional.of(username);
        }

    }

    private boolean isAnonymousUser(Object principal) {
        if (principal instanceof String) {
            return ((String) principal).equalsIgnoreCase(ANONYMOUS_USER);
        } else {
            return false;
        }
    }

}
