package ru.manasyan.web.admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.manasyan.model.Dish;
import ru.manasyan.model.Restaurant;
import ru.manasyan.to.DishTo;
import ru.manasyan.util.exception.IllegalRequestDataException;

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
    @Transactional(readOnly = true)
    public Map<Restaurant, List<DishTo>> viewAll() {

        List<Restaurant> restaurants = restaurantRepository.getAll();

        List<Dish> dishes = dishRepository.getAll(currentDateTime().toLocalDate());

        Map<Integer, List<Dish>> map = dishes.stream()
                .collect(Collectors.groupingBy(d -> d.getRestaurant().getId()));

        Map<Restaurant, List<DishTo>> collect = map.entrySet().stream()
                .collect(Collectors.toMap(e -> restaurants().get(e.getKey()), e -> toDishTo(e.getValue())));

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
    @Transactional
    public Restaurant updateRestaurant(@Valid @RequestBody Restaurant restaurant, @PathVariable("id") int id) throws Exception {
        log.info("Update restaurant {} with data {} ", id, restaurant.toString());
        assureIdConsistent(restaurant, id);
        restaurant.setDateTime(restaurantRepository.findById(id).orElseThrow(() -> new IllegalRequestDataException(dbRestaurant)).getDateTime());
        return restaurantRepository.save(restaurant);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRestaurant(@PathVariable("id") int id) throws Exception {
        log.info("Delete restaurant {} ", id);
        restaurantRepository.delete(restaurantRepository.getOne(id));
    }

    @GetMapping("/{id}")
    public Restaurant getRestaurant(@PathVariable("id") int id) throws Exception {
        log.info("Get restaurant {}", id);
        return restaurantRepository.findById(id).orElseThrow(() -> new IllegalRequestDataException(dbRestaurant));
    }
}
