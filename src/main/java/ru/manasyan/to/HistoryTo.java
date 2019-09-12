package ru.manasyan.to;

import ru.manasyan.model.AbstractBaseEntity;
import ru.manasyan.model.Restaurant;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class HistoryTo extends AbstractBaseEntity {

    private Restaurant restaurant;

    private LocalDate date;

    private Integer votes;

    public HistoryTo() {
    }

    public HistoryTo(Integer id, Restaurant restaurant, LocalDate date, Integer votes) {
        super(id);
        this.restaurant = restaurant;
        this.date = date;
        this.votes = votes;
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

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    @Override
    public String toString() {
        return "HistoryTo{" +
                "restaurant=" + restaurant +
                ", date=" + date +
                ", votes=" + votes +
                '}';
    }
}
