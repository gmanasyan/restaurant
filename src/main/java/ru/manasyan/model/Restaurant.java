package ru.manasyan.model;

import org.springframework.format.annotation.DateTimeFormat;
import ru.manasyan.View;
import ru.manasyan.util.Util;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "restaurants", uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "restaurants_unique_name_idx")})
public class Restaurant extends AbstractBaseEntity {

    @Column(name = "name", nullable = false, unique = true)
    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    @Column(name = "registered", nullable = false)
    @NotNull(groups = View.Persist.class)
    @DateTimeFormat(pattern = Util.DATE_TIME_PATTERN)
    private LocalDateTime registered;

    @OneToMany(mappedBy = "restaurant")
    private List<Dish> dishes;

    public Restaurant () {
    }

    public Restaurant (Integer id, String name, LocalDateTime registered) {
        this(id, name, registered, null);
    }

    public Restaurant (Integer id, String name, LocalDateTime registered, List<Dish> dishes) {
        super(id);
        this.name = name;
        this.registered = registered;
        this.dishes = dishes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getRegistered() {
        return registered;
    }

    public void setRegistered(LocalDateTime registered) {
        this.registered = registered;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", registered=" + registered +
                ", name='" + name + '\'' +
                '}';
    }

}
