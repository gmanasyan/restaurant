package ru.manasyan.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.manasyan.model.Dish;
import ru.manasyan.model.Restaurant;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@Transactional(readOnly = true)
public class DataJpaDishRepository {

    @Autowired
    private CrudDishRepository crudDishRepository;

    public List<Dish> getAll(LocalDate date) {
        return crudDishRepository.getAll(date);
    }

    @Transactional
    public Dish save(Dish dish) {
        return crudDishRepository.save(dish);
    }

    public Dish get(int id) {
        return crudDishRepository.getOne(id);
    }

    @Transactional
    public void delete(int id) {
        crudDishRepository.delete(get(id));
    }

//    public Map<Restaurant, List<Dish>> getAllByRestaurant(LocalDate date) {
//        List<Dish> allDishes = crudDishRepository.getAll(date);
//        Map<Restaurant, List<Dish>> map = allDishes.stream()
//                .collect(Collectors.groupingBy(Dish::getRestaurant));
//        return map;
//    }

//    public Dish get() {
//        return crudDishRepository.getOne();
//    }


}
