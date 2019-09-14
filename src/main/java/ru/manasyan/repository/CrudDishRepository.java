package ru.manasyan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.manasyan.model.Dish;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Transactional(readOnly = true)
public interface CrudDishRepository  extends JpaRepository<Dish, Integer> {

    @Query("SELECT d FROM Dish d WHERE d.date =:date")
    List<Dish> getAll(@Param("date") LocalDate date);

    @Query("SELECT d FROM Dish d JOIN FETCH d.restaurant WHERE d.date =:date")
    List<Dish> getAllWithRestaurant(@Param("date") LocalDate date);

    @Query("SELECT d FROM Dish d WHERE d.date =:date AND d.restaurant.id = :id")
    List<Dish> getHistory(@Param("id") int id, @Param("date") LocalDate date);

}
