package ru.manasyan.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.manasyan.View;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "history")
public class History extends AbstractBaseEntity {

    @Column(name = "restaurant_id", nullable = false)
    private int restaurant_id;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "votes", nullable = false)
    private int votes;


    public History() {
    }

    public History(Integer id, int restaurant_id, LocalDate date, int votes) {
        super(id);
        this.restaurant_id = restaurant_id;
        this.date = date;
        this.votes = votes;
    }

    public void addVotes(int votes) {
        this.votes += votes;
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

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
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
