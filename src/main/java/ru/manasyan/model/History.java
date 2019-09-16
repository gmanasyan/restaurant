package ru.manasyan.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "history", uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id", "date"}, name = "history_unique_date_restaurant_idx")})
public class History extends AbstractBaseEntity {

    @Column(name = "restaurant_id", nullable = false)
    @NotNull
    private Integer restaurant_id;

    @Column(name = "date", nullable = false)
    @NotNull
    private LocalDate date;

    @Column(name = "votes", nullable = false)
    @NotNull
    private Integer votes;

    public History() {
    }

    public History(Integer id, Integer restaurant_id, LocalDate date, Integer votes) {
        super(id);
        this.restaurant_id = restaurant_id;
        this.date = date;
        this.votes = votes;
    }

    public void addVotes(int votes) {
        this.votes += votes;
    }


    public Integer getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(Integer restaurant_id) {
        this.restaurant_id = restaurant_id;
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
        return "Vote History {" +
                "id=" + id +
                ", date=" + date +
                ", restaurant_id=" + restaurant_id +
                ", votes=" + votes +
                '}';
    }

}
