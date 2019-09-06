package ru.manasyan.model;

import org.hibernate.validator.constraints.UniqueElements;
import ru.manasyan.View;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "restaurants", uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "restaurants_unique_name_idx")})
public class Restaurant extends AbstractBaseEntity {

    @Column(name = "name", nullable = false, unique = true)
    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    @Column(name = "date_time", nullable = false)
    @NotNull(groups = View.Persist.class)
    private LocalDateTime dateTime;

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")//, cascade = CascadeType.REMOVE, orphanRemoval = true)
//    @OrderBy("date DESC")
////    @JsonIgnore
//    protected List<Dish> dishes;

    public Restaurant () {
    }

    public Restaurant (Integer id, String name, LocalDateTime dateTime) {
        super(id);
        this.name = name;
        this.dateTime = dateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", name='" + name + '\'' +
                '}';
    }

}
