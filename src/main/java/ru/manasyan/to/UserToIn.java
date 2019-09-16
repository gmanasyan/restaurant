package ru.manasyan.to;

import org.hibernate.validator.constraints.SafeHtml;
import ru.manasyan.model.AbstractBaseEntity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserToIn extends AbstractBaseEntity {

    @NotBlank
    @Size(min = 2, max = 100, message = "length must be between 2 and 100 characters")
    @SafeHtml
    private String name;

    @NotBlank
    @Size(min = 5, max = 32, message = "length must be between 5 and 32 characters")
    private String password;

    public UserToIn() {
    }

    public UserToIn(Integer id, String name, String password) {
        super(id);
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name=" + name +
                '}';
    }
}
