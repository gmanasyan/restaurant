package ru.manasyan.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.manasyan.model.Dish;

import java.time.LocalDate;
import java.util.List;

@Repository
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
        return crudDishRepository.getWithRestaurant(id);
    }

    public List<Dish> getHistory(int restaurant_id, LocalDate date) {
        return crudDishRepository.getHistory(restaurant_id, date);
    }

    @Transactional
    public void delete(int id) {
        crudDishRepository.delete(get(id));
    }

}
