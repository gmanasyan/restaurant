package ru.manasyan.web.dish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.manasyan.model.Dish;
import ru.manasyan.model.Restaurant;
import ru.manasyan.repository.DataJpaDishRepository;
import ru.manasyan.repository.DataJpaVoteRepository;
import ru.manasyan.to.DishTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.manasyan.util.Util.currentDateTime;

@RestController
@RequestMapping(value = "/rest/menu",  produces = MediaType.APPLICATION_JSON_VALUE)
public class DishRestController {

    @Autowired
    private DataJpaDishRepository dishRepository;

    @Autowired
    private DataJpaVoteRepository voteRepository;

    @GetMapping
    public Map<Restaurant, List<DishTo>> getAll() {
       return getAllByDate(currentDateTime().toLocalDate());
    }

    @GetMapping("/{date}")
    public Map<Restaurant, List<DishTo>> getAllByDate(@DateTimeFormat(pattern = "yyyy-MM-dd") @PathVariable("date") LocalDate date) {
        List<Dish> allDishes = dishRepository.getAll(date);
        Map<Restaurant, List<Dish>> map = allDishes.stream()
                .collect(Collectors.groupingBy(Dish::getRestaurant));

        Map<Restaurant, List<DishTo>> collect = map.entrySet().stream()
                .collect(Collectors.toMap(e -> e.getKey(), e -> toDishTo(e.getValue())));
        return collect;
    }

    public List<DishTo> toDishTo(List<Dish> dishes) {
        return  dishes.stream()
                .map(d -> new DishTo(d.getId(), d.getName(), d.getPrice()))
                .collect(Collectors.toList());
    }

    @PostMapping("/vote/{id}")
    public boolean vote(@PathVariable("id") int restaurant_id) {
        //int userId = SecurityUtil.authUserId();
        int user_id = 100000;
        return canVote() ? voteRepository.update(user_id, restaurant_id, currentDateTime().toLocalDate()) : false;
    }

    public boolean canVote() {
        return (currentDateTime().toLocalTime().isAfter(LocalTime.of(11,00)))
                ? false : true;
    }


}
