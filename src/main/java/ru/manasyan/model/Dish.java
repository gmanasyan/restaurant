package ru.manasyan.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;
import ru.manasyan.View;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "dishes")
public class Dish extends AbstractBaseEntity {

    @Column(name = "name", nullable = false)
    @NotBlank
    @Size(min = 2, max = 256, message = "Dish name must be more than 2 symbols and less than 256")
    private String name;

    @Column(name = "price", nullable = false)
    @NotNull
    @Range(min = 10, max = 200000, message = "Price must be between 0.10 and 2000")
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull(groups = View.Persist.class)
    @JsonIgnore
    private Restaurant restaurant;

    @Column(name = "date", nullable = false)
    @NotNull(groups = View.Persist.class)
    private LocalDate date;

    public Dish() {
    }

    public Dish(Integer id, String name, int price, LocalDate date, Restaurant restaurant) {
        super(id);
        this.name = name;
        this.price = price;
        this.date = date;
        this.restaurant = restaurant;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", dateTime=" + date +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", restaurant=" + restaurant +
                '}';
    }

}
