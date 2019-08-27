package ru.manasyan.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "dishes")
public class Dish extends AbstractBaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private int price;

    //@Column(name = "restaurant_id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @NotNull
    private Restaurant restaurant;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    public Dish() {
    }

    public Dish(Integer id, String name, int price, LocalDate date) {
        super(id);
        this.name = name;
        this.price = price;
        this.date = date;
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

    public void setPrice(int price) {
        this.price = price;
    }

    public Restaurant getRestaurant_id() {
        return restaurant;
    }

    public void setRestaurant_id(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public LocalDate getDateTime() {
        return date;
    }

    public void setDateTime(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", dateTime=" + date +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", restaurant id=" + restaurant.id +
                '}';
    }

}
