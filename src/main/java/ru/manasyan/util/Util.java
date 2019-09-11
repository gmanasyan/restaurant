package ru.manasyan.util;

import ru.manasyan.model.Dish;
import ru.manasyan.to.DishTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class Util {

    public static LocalDateTime currentDateTime() {

        // for production
        //return LocalDateTime.now();

        // for debug
        return LocalDateTime.of(2019, 8, 16, 11,00);
    }

    public static List<DishTo> toDishTo(List<Dish> dishes) {
        return  dishes.stream()
                .map(d -> new DishTo(d.getId(), d.getName(), d.getPrice()))
                .collect(Collectors.toList());
    }

    public static boolean canVote() {
        return (currentDateTime().toLocalTime().isAfter(LocalTime.of(11,00)))
                ? false : true;
    }
}
