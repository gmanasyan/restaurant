package ru.manasyan.to;

import org.hibernate.validator.constraints.Range;
import ru.manasyan.model.AbstractBaseEntity;
import ru.manasyan.model.Restaurant;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class DishTo extends AbstractBaseEntity {

    @NotBlank
    @Size(min = 2, max = 256)
    private String name;

    @NotNull
    @Range(min = 10, max = 200000, message = "Price must be between 0.10 and 2000")
    private Integer price;

    public DishTo() {
    }

    public DishTo(Integer id, String name, int price) {
        super(id);
        this.name = name;
        this.price = price;
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

    @Override
    public String toString() {
        return "DishTo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }

}
