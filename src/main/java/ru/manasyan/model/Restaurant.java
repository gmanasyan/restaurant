package ru.manasyan.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "restaurants")
public class Restaurant extends AbstractBaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "date_time", nullable = false)
    private LocalDateTime date_time;

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")//, cascade = CascadeType.REMOVE, orphanRemoval = true)
//    @OrderBy("date DESC")
////    @JsonIgnore
//    protected List<Dish> dishes;

    public Restaurant () {
    }

    public Restaurant (Integer id, String name, LocalDateTime date_time) {
        super(id);
        this.name = name;
        this.date_time = date_time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDate_time() {
        return date_time;
    }

    public void setDate_time(LocalDateTime date_time) {
        this.date_time = date_time;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", dateTime=" + date_time +
                ", name='" + name + '\'' +
                '}';
    }

}
