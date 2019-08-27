package ru.manasyan.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "votes", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "date"}, name = "votes_unique_date_user_idx")})
public class Vote extends AbstractBaseEntity {

    @Column(name = "user_id", nullable = false)
    private int user_id;

    @Column(name = "restaurant_id", nullable = false)
    private int restaurant_id;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    public Vote() {
    }

    public Vote(Integer id, int user_id, int restaurant_id, LocalDate date) {
        super(id);
        this.user_id = user_id;
        this.restaurant_id = restaurant_id;
        this.date = date;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(int restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date_time) {
        this.date = date;
    }
}
