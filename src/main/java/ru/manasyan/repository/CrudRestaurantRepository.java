package ru.manasyan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import ru.manasyan.model.Restaurant;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudRestaurantRepository extends JpaRepository<Restaurant, Integer> {

    @Query("SELECT r FROM Restaurant r ORDER BY r.name")
    List<Restaurant> getAll();

}
