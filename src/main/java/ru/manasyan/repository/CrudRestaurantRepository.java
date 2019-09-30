package ru.manasyan.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import ru.manasyan.model.Restaurant;
import ru.manasyan.to.RestaurantTo;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudRestaurantRepository extends JpaRepository<Restaurant, Integer> {

    @Query("SELECT r FROM Restaurant r ORDER BY r.name")
    List<Restaurant> getAll();

    @Query("SELECT r FROM Restaurant r WHERE r.id=:id")
    Restaurant get(@Param("id") int id);

    @EntityGraph(attributePaths = {"dishes"})
    @Query("SELECT r FROM Restaurant r JOIN r.dishes d WHERE d.date = ?1")
    List<Restaurant> getAllWithMeals(LocalDate date);

}
