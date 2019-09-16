package ru.manasyan;

import ru.manasyan.model.User;

public class AuthorizedUser extends org.springframework.security.core.userdetails.User {

    private User user;

    public AuthorizedUser(User user) {
        super(user.getEmail(), user.getPassword(), true, true, true, true,user.getRoles());
        this.user = user;
    }

    public int getId() {
        return user.getId();
    }

    public void update(User newUser) {
        user = newUser;
    }

    public User getUserTo() {
        return user;
    }

    @Override
    public String toString() {
        return user.toString();
    }

}
