package ru.manasyan.web.dish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.manasyan.model.Dish;
import ru.manasyan.model.Restaurant;
import ru.manasyan.model.Vote;
import ru.manasyan.repository.DataJpaDishRepository;
import ru.manasyan.repository.DataJpaVoteRepository;
import ru.manasyan.to.DishTo;
import ru.manasyan.web.SecurityUtil;

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
    public boolean vote(@PathVariable("id") int restaurant_id) throws Exception {
        int userId = SecurityUtil.authUserId();
        return canVote() ? voteRepository.update(userId, restaurant_id, LocalDate.now()) : false;
    }

    public boolean canVote() {
        return (currentDateTime().toLocalTime().isAfter(LocalTime.of(11,00)))
                ? false : true;
    }

    @PostMapping("/vote/history")
    public List<Vote> history() throws Exception {
        int userId = SecurityUtil.authUserId();
        List<Vote> votes = voteRepository.history(userId);
        return votes;
    }

    @PostMapping("/restaurants/{id}/{date}")
    public List<Dish> restaurantMenu(@PathVariable("id") int restaurant_id, @DateTimeFormat(pattern = "yyyy-MM-dd") @PathVariable("date") LocalDate date) throws Exception {
        List<Dish> dishes = dishRepository.getHistory(restaurant_id, date);
        // No need restaurant info in every dish.
        dishes.forEach(d -> d.setRestaurant(null));
        return dishes;
    }


}
