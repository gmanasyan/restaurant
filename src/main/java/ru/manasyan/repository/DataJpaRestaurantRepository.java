package ru.manasyan.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.manasyan.model.Dish;
import ru.manasyan.model.Restaurant;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class DataJpaRestaurantRepository {

    @Autowired
    private CrudRestaurantRepository crudRestaurantRepository;

    public List<Restaurant> getAll(LocalDate date) {
        return crudRestaurantRepository.getAll();
    }

    @Transactional
    public Restaurant create(String name) {
        Restaurant restaurant = new Restaurant(null, name, LocalDateTime.now());
        return crudRestaurantRepository.save(restaurant);
    }

    public Restaurant get(int id) {
        return crudRestaurantRepository.getOne(id);
    }

}
