package ru.manasyan.to;

import ru.manasyan.model.Restaurant;
import java.time.LocalDate;

public class HistoryTo extends BaseTo {


    private Integer restaurant_id;

    private String restaurant;

    private LocalDate date;

    private Integer votes;

    public HistoryTo() {
    }

    public HistoryTo(Integer id, Restaurant restaurant, LocalDate date, Integer votes) {
        super(id);
        this.restaurant = restaurant.getName();
        this.restaurant_id = restaurant.getId();
        this.date = date;
        this.votes = votes;
    }

    public Integer getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(Integer restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }


    @Override
    public String toString() {
        return "HistoryTo{" +
                "restaurant_id=" + restaurant_id +
                ", restaurant='" + restaurant + '\'' +
                ", date=" + date +
                ", votes=" + votes +
                '}';
    }
}
