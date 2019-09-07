package ru.manasyan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.manasyan.model.History;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudVoteHistoryRepository extends JpaRepository<History, Integer> {

    @Query("SELECT h FROM History h WHERE h.date=:date")
    List<History> get(@Param("date") LocalDate date);

    @Query("SELECT h FROM History h WHERE h.date=:date AND h.restaurant_id = :restaurant_id")
    History get(@Param("date") LocalDate date, @Param("restaurant_id") Integer restaurant_id);

}
