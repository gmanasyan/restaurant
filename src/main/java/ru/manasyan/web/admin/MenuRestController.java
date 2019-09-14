package ru.manasyan.web.admin;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.manasyan.model.Dish;

import javax.validation.Valid;
import java.time.LocalDate;

import static ru.manasyan.util.ValidationUtil.assureIdConsistent;
import static ru.manasyan.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = "/rest/restaurants",  produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuRestController extends AbstractRestController {

    @PostMapping(value ="/{id}/dishes")
    public Dish createDish(@Valid @RequestBody Dish dish, @PathVariable("id") int restaurant_id) {
        log.info("Create dish for restaurant {} with data {} ",restaurant_id, dish.toString());
        checkNew(dish);
        dish.setDate(LocalDate.now());
        dish.setRestaurant(restaurantRepository.get(restaurant_id));
        Dish savedDish = dishRepository.save(dish);
        return savedDish;
    }

    @PutMapping("dishes/{id}")
    @Transactional
    public Dish updateDish(@Valid @RequestBody Dish dishUpdate, @PathVariable("id") int id) throws Exception {
        log.info("Update dish id {} with data {} ",id, dishUpdate.toString());
        assureIdConsistent(dishUpdate, id);
        // Update only today dishes
        Dish dish = dishRepository.get(id);
        if (dish.getDate().equals(LocalDate.now())) {
            dishUpdate.setRestaurant(dish.getRestaurant());
            dishUpdate.setDate(dish.getDate());
            return dishRepository.save(dishUpdate);
        } else throw new DataIntegrityViolationException("Only today dishes can be updated: " + dishUpdate);
    }

    @DeleteMapping("dishes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDish(@PathVariable("id") int id) {
        log.info("Delete dish id {}",id);
        dishRepository.delete(id);
    }

}

