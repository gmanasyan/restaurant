package ru.manasyan.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.manasyan.model.Dish;
import ru.manasyan.model.Restaurant;
import ru.manasyan.repository.DataJpaDishRepository;
import ru.manasyan.repository.DataJpaRestaurantRepository;
import ru.manasyan.repository.DataJpaVoteRepository;
import ru.manasyan.to.DishTo;
import ru.manasyan.to.VotesStatistics;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.manasyan.util.Util.currentDateTime;

@RestController
@RequestMapping(value = "/rest/restaurants",  produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestController {

    @Autowired
    private DataJpaRestaurantRepository restaurantRepository;

    @Autowired
    private DataJpaDishRepository dishRepository;

    @Autowired
    private DataJpaVoteRepository voteRepository;


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

    //@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping
    public Restaurant createRestaurant(@RequestParam("name") String name) {
        Restaurant restaurant = new Restaurant(null, name, LocalDateTime.now());
        return restaurantRepository.save(restaurant);
    }

    @PutMapping("/{id}")
    public Restaurant updateRestaurant(@PathVariable("id") int id, @RequestParam("name") String name) {
        Restaurant restaurant = restaurantRepository.get(id);
        restaurant.setName(name);
        return restaurantRepository.save(restaurant);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRestaurant(@PathVariable("id") int id) {
        restaurantRepository.delete(id);
    }


    // -------------- Dishes ---------------------

    @PostMapping("/{id}/dishes")
    public Dish createDish(@PathVariable("id") int restaurant_id, @RequestParam("name") String name, @RequestParam("price") double price ) {
        Dish dish = new Dish(null, name, (int)(price*100), LocalDate.now());
        dish.setRestaurant(restaurantRepository.get(restaurant_id));
        Dish savedDish = dishRepository.save(dish);
        return savedDish;
    }

    @PutMapping("dishes/{id}")
    public Dish updateDish(@PathVariable("id") int id, @RequestParam("name") String name, @RequestParam("price") double price) throws Exception {
        Dish dish = dishRepository.get(id);

        // Update only today dishes
        if (dish.getDateTime().equals(LocalDate.now())) {
            dish.setName(name);
            dish.setPrice((int) (price * 100));
            Dish savedDish = dishRepository.save(dish);
            return savedDish;
        } else throw new Exception("Only today dishes can be updated");
    }

    @DeleteMapping("dishes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDish(@PathVariable("id") int id) {
        dishRepository.delete(id);
    }


    // ------------------ Votes ------------------

    @GetMapping("/votes")
    public Map<Restaurant, Long> todayVotes() {
        List<VotesStatistics> votes = voteRepository.getToday(LocalDate.of(2019,8,27));

        Map<Restaurant, Long> votesRestaurant = votes.stream()
                .collect(Collectors.toMap(v -> restaurantRepository.get(v.getRestaurant()), v -> v.getVotes() ));

        return votesRestaurant;
    }



}
