package ru.manasyan.web.admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.manasyan.model.Dish;
import ru.manasyan.model.Restaurant;
import ru.manasyan.to.DishTo;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.manasyan.util.Util.currentDateTime;
import static ru.manasyan.util.ValidationUtil.assureIdConsistent;
import static ru.manasyan.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = "/rest/restaurants",  produces = MediaType.APPLICATION_JSON_VALUE)
@Transactional
public class RestaurantRestController extends AbstractRestController {

    @GetMapping
    public Map<Restaurant, List<DishTo>> viewAll() {

        List<Restaurant> restaurants = restaurantRepository.getAll();
        List<Dish> dishes = dishRepository.getAll(currentDateTime().toLocalDate());

        Map<Restaurant, List<Dish>> map = dishes.stream()
                .collect(Collectors.groupingBy(d -> d.getRestaurant()));

        Map<Restaurant, List<DishTo>> collect = map.entrySet().stream()
                .collect(Collectors.toMap(e -> e.getKey(), e -> toDishTo(e.getValue())));

        restaurants.forEach(r -> collect.putIfAbsent(r, new ArrayList<>()));

        return collect;
    }

    public List<DishTo> toDishTo(List<Dish> dishes) {
        return  dishes.stream()
                .map(d -> new DishTo(d.getId(), d.getName(), d.getPrice()))
                .collect(Collectors.toList());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant createRestaurant(@Valid @RequestBody Restaurant restaurant) {
        log.info("Create restaurant {} ", restaurant.toString());
        checkNew(restaurant);
        restaurant.setDateTime(LocalDateTime.now());
        return restaurantRepository.save(restaurant);
    }

    @PutMapping("/{id}")
    public Restaurant updateRestaurant(@Valid @RequestBody Restaurant restaurant, @PathVariable("id") int id) {
        log.info("Update restaurant {} with data {} ", id, restaurant.toString());
        assureIdConsistent(restaurant, id);
        restaurant.setDateTime(restaurantRepository.get(id).getDateTime());
        return restaurantRepository.save(restaurant);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRestaurant(@PathVariable("id") int id) {
        log.info("Delete restaurant {} ", id);
        restaurantRepository.delete(id);
    }

    @GetMapping("/{id}")
    public Restaurant getRestaurant(@PathVariable("id") int id) {
        log.info("Get restaurant {}", id);
        return restaurantRepository.get(id);
    }
}
