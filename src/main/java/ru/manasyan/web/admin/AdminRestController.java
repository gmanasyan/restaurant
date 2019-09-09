package ru.manasyan.web.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.manasyan.View;
import ru.manasyan.model.Dish;
import ru.manasyan.model.History;
import ru.manasyan.model.Restaurant;
import ru.manasyan.repository.DataJpaDishRepository;
import ru.manasyan.repository.DataJpaRestaurantRepository;
import ru.manasyan.repository.DataJpaVoteHistoryRepository;
import ru.manasyan.repository.DataJpaVoteRepository;
import ru.manasyan.to.DishTo;
import ru.manasyan.to.VotesStatistics;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.manasyan.util.Util.currentDateTime;
import static ru.manasyan.util.ValidationUtil.assureIdConsistent;
import static ru.manasyan.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = "/rest/restaurants",  produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestController {

    @Autowired
    private DataJpaRestaurantRepository restaurantRepository;

    @Autowired
    private DataJpaDishRepository dishRepository;

    @Autowired
    private DataJpaVoteRepository voteRepository;

    @Autowired
    private DataJpaVoteHistoryRepository historyRepository;

    @GetMapping
    public Map<Restaurant, List<DishTo>> viewAll() {

        List<Restaurant> restaurants = restaurantRepository.getAll();
        List<Dish> dishes = dishRepository.getAll(currentDateTime().toLocalDate());

        Map<Restaurant, List<Dish>> map = dishes.stream()
                .collect(Collectors.groupingBy(d -> d.getRestaurant()));

        Map<Restaurant, List<DishTo>> collect = map.entrySet().stream()
                .collect(Collectors.toMap(e -> e.getKey(), e -> toDishTo(e.getValue())));

        restaurants.forEach(r -> collect.putIfAbsent(r, null));

        return collect;
    }

    public List<DishTo> toDishTo(List<Dish> dishes) {
        return  dishes.stream()
                .map(d -> new DishTo(d.getId(), d.getName(), d.getPrice()))
                .collect(Collectors.toList());
    }

    @PostMapping
    public Restaurant createRestaurant(@Valid @RequestBody Restaurant restaurant) {
        checkNew(restaurant);
        restaurant.setDateTime(LocalDateTime.now());
        return restaurantRepository.save(restaurant);
    }

    @PutMapping("/{id}")
    public Restaurant updateRestaurant(@Valid @RequestBody Restaurant restaurant, @PathVariable("id") int id) {
        assureIdConsistent(restaurant, id);
        restaurant.setDateTime(restaurantRepository.get(id).getDateTime());
        return restaurantRepository.save(restaurant);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRestaurant(@PathVariable("id") int id) {
        restaurantRepository.delete(id);
    }


    // -------------- Dishes ---------------------

    @PostMapping(value ="/{id}/dishes")
    public Dish createDish(@Valid @RequestBody Dish dish, @PathVariable("id") int restaurant_id) {
        checkNew(dish);
        dish.setDate(LocalDate.now());
        dish.setRestaurant(restaurantRepository.get(restaurant_id));
        Dish savedDish = dishRepository.save(dish);
        return savedDish;
    }

    @PutMapping("dishes/{id}")
    public Dish updateDish(@Valid @RequestBody Dish dishUpdate, @PathVariable("id") int id) throws Exception {
        assureIdConsistent(dishUpdate, id);

        // Update only today dishes
        Dish dish = dishRepository.get(id);
        if (dish.getDate().equals(LocalDate.now())) {
            dishUpdate.setRestaurant(dish.getRestaurant());
            return dishRepository.save(dishUpdate);
        } else throw new DataIntegrityViolationException("Only today dishes can be updated: " + dishUpdate);
    }

    @DeleteMapping("dishes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDish(@PathVariable("id") int id) {
        dishRepository.delete(id);
    }


    // ------------------ Votes ------------------

    // Ver 1| Search by votes database for today
    @GetMapping("/votes")
    public Map<Restaurant, Long> todayVotes() {
        List<VotesStatistics> votes = voteRepository.getToday(LocalDate.of(2019,8,27));

        Map<Restaurant, Long> votesRestaurant = votes.stream()
                .collect(Collectors.toMap(v -> restaurantRepository.get(v.getRestaurant()), v -> v.getVotes() ));

        return votesRestaurant;
    }

    // Ver 2| Search by history database for speed.
    @GetMapping("/votes/{date}")
    public List<History> votesByDate(@DateTimeFormat(pattern = "yyyy-MM-dd") @PathVariable("date") LocalDate date) {
        List<History> history = historyRepository.get(date);
        // no need history id
        history.forEach(h -> h.setId(null));
        return history;
    }

    @GetMapping("{id}/votes")
    public List<History> votesByRestaurant(@PathVariable("id") int id) {
        List<History> history = historyRepository.get(id);
        // no need history id
        history.forEach(h -> h.setId(null));
        return history;
    }



}
