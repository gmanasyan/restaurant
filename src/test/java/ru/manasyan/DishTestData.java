package ru.manasyan;

import ru.manasyan.model.Dish;
import ru.manasyan.model.User;

import static org.assertj.core.api.Assertions.assertThat;

public class DishTestData {

    public static void assertMatch(Dish actual, Dish expected) {
        assertThat(actual).isEqualTo(expected);
    }
}
