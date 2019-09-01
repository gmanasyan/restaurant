package ru.manasyan.web;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.manasyan.AuthorizedUser;

import static java.util.Objects.requireNonNull;

public class SecurityUtil {

    private SecurityUtil() {
    }

    public static int authUserId() throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            Object principal = auth.getPrincipal();
            AuthorizedUser user = (principal instanceof AuthorizedUser) ? (AuthorizedUser) principal : null;
            if (user != null) {
                return user.getId();
            }
        }
        throw new Exception("No authorized user found");
    }
}
