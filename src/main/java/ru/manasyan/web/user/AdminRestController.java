package ru.manasyan.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.manasyan.model.Dish;
import ru.manasyan.model.Restaurant;
import ru.manasyan.repository.DataJpaDishRepository;
import ru.manasyan.repository.DataJpaRestaurantRepository;
import ru.manasyan.repository.DataJpaVoteRepository;

import java.time.LocalDate;

@Controller
@RequestMapping(value = "/rest/restaurants",  produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestController {

    @Autowired
    private DataJpaRestaurantRepository restaurantRepository;

    @Autowired
    private DataJpaDishRepository dishRepository;


    @PostMapping
    public Restaurant vote(@RequestParam("name") String name) {
        return restaurantRepository.create(name);
    }

    @PostMapping("/{id}")
    public Dish vote( @PathVariable("id") int restaurant_id,  @RequestParam("name") String name,  @RequestParam("price") double price ) {
        Dish dish = new Dish(null, name, (int)(price*100), LocalDate.now());
        dish.setRestaurant(restaurantRepository.get(restaurant_id));
        return dishRepository.save(dish);
    }



}
