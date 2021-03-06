package ru.manasyan.util;

import ru.manasyan.model.Dish;
import ru.manasyan.model.User;
import ru.manasyan.to.DishTo;
import ru.manasyan.to.UserToIn;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class Util {

    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm";

    public static List<DishTo> toDishTo(List<Dish> dishes) {
        return  dishes.stream()
                .map(d -> new DishTo(d.getId(), d.getName(), d.getPrice()))
                .collect(Collectors.toList());
    }

    public static User updateFromTo(UserToIn userTo, User user) {
        user.setName(userTo.getName());
        user.setPassword(userTo.getPassword());
        return  user;
    }


}
