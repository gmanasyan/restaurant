package ru.manasyan.web.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.manasyan.model.Dish;
import ru.manasyan.model.Restaurant;
import ru.manasyan.model.Vote;
import ru.manasyan.repository.CrudDishRepository;
import ru.manasyan.repository.CrudRestaurantRepository;
import ru.manasyan.service.VoteService;
import ru.manasyan.to.RestaurantTo;
import ru.manasyan.web.SecurityUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static ru.manasyan.util.Util.*;

@RestController
@RequestMapping(value = DishRestController.REST_URL,  produces = MediaType.APPLICATION_JSON_VALUE)
@PropertySource("classpath:app.properties")
public class DishRestController {

    static final String REST_URL = "/rest";

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
    public List<RestaurantTo> getAll() throws Exception {
        log.info("View today menu for all restaurants");
        return getAllByDate(currentDateTime().toLocalDate());
    }

    @GetMapping("/{date}")
    @Transactional(readOnly = true)
    public List<RestaurantTo> getAllByDate(@DateTimeFormat(pattern = "yyyy-MM-dd") @PathVariable("date") LocalDate date) throws Exception {
        int userId = SecurityUtil.authUserId();
        log.info("View menu by date {} for all restaurants by user {}", date, userId);

        List<Restaurant> restaurants = restaurantRepository.getAllWithMeals(date);

        List<RestaurantTo> collect = restaurants.stream()
                .map(r -> new RestaurantTo(r.getId(), r.getName(), r.getRegistered(), r.getDishes()))
                .collect(Collectors.toList());

        return collect;
    }

    @PostMapping("/{id}/vote")
    @ResponseStatus(HttpStatus.OK)
    public void vote(@PathVariable("id") int restaurantId) throws Exception {
        int userId = SecurityUtil.authUserId();
        log.info("Vote for restaurant {} by user {}", restaurantId, userId);
        if (voteService.get(userId, LocalDate.now()) != null && !canVote(voteEndTime)) {
            throw new DataIntegrityViolationException("User can't vote second time after 11:00");
        }
        voteService.update(userId, restaurantId, LocalDate.now());
    }

    @GetMapping("/votes")
    public List<Vote> history() throws Exception {
        int userId = SecurityUtil.authUserId();
        log.info("View vote history for user {}", userId);
        List<Vote> votes = voteService.history(userId);
        return votes;
    }

    @GetMapping("/{id}/{date}")
    public List<Dish> restaurantMenu(@PathVariable("id") int restaurant_id, @DateTimeFormat(pattern = "yyyy-MM-dd") @PathVariable("date") LocalDate date) throws Exception {
        int userId = SecurityUtil.authUserId();
        log.info("View menu for restaurant {} by date {} for user {}", restaurant_id, date, userId);
        List<Dish> dishes = dishRepository.getHistory(restaurant_id, date);
        // No need restaurant info in every dish.
        dishes.forEach(d -> d.setRestaurant(null));
        return dishes;
    }

}
