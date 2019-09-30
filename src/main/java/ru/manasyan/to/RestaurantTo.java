package ru.manasyan.to;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ru.manasyan.model.Dish;
import ru.manasyan.model.Restaurant;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RestaurantTo {

    private int id;

    private String name;

    private LocalDateTime registered;

    @JsonDeserialize(as = ArrayList.class, contentAs = Dish.class)
    private List<Dish> dishes;

    public RestaurantTo() {
    }

    public RestaurantTo(int id, String name, LocalDateTime registered, List<Dish> dishes) {
        this.id = id;
        this.name = name;
        this.registered = registered;
        this.dishes = dishes;
    }

    public RestaurantTo(Restaurant restaurant, List<Dish> dishes) {
        this(restaurant.getId(), restaurant.getName(), restaurant.getRegistered(), dishes);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getRegistered() {
        return registered;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    @Override
    public String toString() {
        return "RestaurantTo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", registered=" + registered +
                ", dishes=" + dishes +
                '}';
    }
}
