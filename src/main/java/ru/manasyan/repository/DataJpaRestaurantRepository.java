package ru.manasyan.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.manasyan.model.Restaurant;

import java.util.List;

@Repository
public class DataJpaRestaurantRepository {

    @Autowired
    private CrudRestaurantRepository crudRestaurantRepository;

    public List<Restaurant> getAll() {
        return crudRestaurantRepository.getAll();
    }

    @Transactional
    public Restaurant save(Restaurant restaurant) {
        return crudRestaurantRepository.save(restaurant);
    }

    public Restaurant get(int id) {
        return crudRestaurantRepository.findById(id).orElse(null);
    }

    @Transactional
    public void delete(int id) {
        crudRestaurantRepository.delete(get(id));
    }

}
