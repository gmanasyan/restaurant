package ru.manasyan.web.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.manasyan.model.Dish;
import ru.manasyan.model.Restaurant;
import ru.manasyan.model.Vote;
import ru.manasyan.repository.CrudDishRepository;
import ru.manasyan.repository.CrudRestaurantRepository;
import ru.manasyan.service.VoteService;
import ru.manasyan.to.DishTo;
import ru.manasyan.web.SecurityUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.manasyan.util.Util.*;

@RestController
@RequestMapping(value = DishRestController.REST_URL,  produces = MediaType.APPLICATION_JSON_VALUE)
@PropertySource("classpath:app.properties")
public class DishRestController {

    static final String REST_URL = "/rest/menu";

    @Value("${vote.endTime}")
    private String voteEndTime;

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private CrudDishRepository dishRepository;

    @Autowired
    private VoteService voteService;

    @Autowired
    private CrudRestaurantRepository restaurantRepository;

    @GetMapping
    public Map<Restaurant, List<DishTo>> getAll() throws Exception {
        log.info("View today menu for all restaurants");
        return getAllByDate(currentDateTime().toLocalDate());
    }

    @GetMapping("/{date}")
    @Transactional(readOnly = true)
    public Map<Restaurant, List<DishTo>> getAllByDate(@DateTimeFormat(pattern = "yyyy-MM-dd") @PathVariable("date") LocalDate date) throws Exception {
        int userId = SecurityUtil.authUserId();
        log.info("View menu by date {} for all restaurants by user {}", date, userId);
        List<Dish> allDishes = dishRepository.getAll(date);
        Map<Integer, List<Dish>> map = allDishes.stream()
                .collect(Collectors.groupingBy(d -> d.getRestaurant().getId()));

        List<Restaurant> restaurants = restaurantRepository.getAll();
        Map<Integer, Restaurant> restaurantMap = restaurants.stream()
                .collect(Collectors.toMap(r -> r.getId(), r -> r));

        Map<Restaurant, List<DishTo>> collect = map.entrySet().stream()
                .collect(Collectors.toMap(e -> restaurantMap.get(e.getKey()), e -> toDishTo(e.getValue())));
        return collect;
    }

    @PostMapping("/vote/{id}")
    public boolean vote(@PathVariable("id") int restaurant_id) throws Exception {
        int userId = SecurityUtil.authUserId();
        log.info("Vote for restaurant {} by user", restaurant_id, userId);
        if (voteService.get(userId, LocalDate.now()) != null && !canVote(voteEndTime)) {
            return false;
        }
        return voteService.update(userId, restaurant_id, LocalDate.now());
    }

    @GetMapping("/vote/history")
    public List<Vote> history() throws Exception {
        int userId = SecurityUtil.authUserId();
        log.info("View vote history for user {}", userId);
        List<Vote> votes = voteService.history(userId);
        return votes;
    }

    @GetMapping("/restaurants/{id}/{date}")
    public List<Dish> restaurantMenu(@PathVariable("id") int restaurant_id, @DateTimeFormat(pattern = "yyyy-MM-dd") @PathVariable("date") LocalDate date) throws Exception {
        int userId = SecurityUtil.authUserId();
        log.info("View menu for restaurant {} by date {} for user {}", restaurant_id, date, userId);
        List<Dish> dishes = dishRepository.getHistory(restaurant_id, date);
        // No need restaurant info in every dish.
        dishes.forEach(d -> d.setRestaurant(null));
        return dishes;
    }

}
