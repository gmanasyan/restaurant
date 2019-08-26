package ru.manasyan.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "votes", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "restaurant_id", "date_time"}, name = "votes_unique_restaurant_datetime_user_idx")})
public class Vote extends AbstractBaseEntity {

    @Column(name = "user_id", nullable = false)
    private String user_id;

    @Column(name = "restaurant_id", nullable = false)
    private String restaurant_id;

    @Column(name = "date_time", nullable = false)
    private LocalDateTime date_time;

    public Vote() {
    }

    public Vote(Integer id, String user_id, String restaurant_id, LocalDateTime date_time) {
        super(id);
        this.user_id = user_id;
        this.restaurant_id = restaurant_id;
        this.date_time = date_time;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(String restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public LocalDateTime getDate_time() {
        return date_time;
    }

    public void setDate_time(LocalDateTime date_time) {
        this.date_time = date_time;
    }
}
