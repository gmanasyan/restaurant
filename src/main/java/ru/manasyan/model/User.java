package ru.manasyan.model;

import java.util.Date;
import java.util.Set;

public class User extends AbstractBaseEntity {

    private String name;
    
    private String email;

    private String password;

    private Date registered = new Date();

    private Set<Role> roles;

    public User(Integer id, String name, String email, String password, Date registered, Set<Role> roles) {
        super(id);
        this.name = name;
        this.email = email;
        this.password = password;
        this.registered = registered;
        this.roles = roles;
    }
}
