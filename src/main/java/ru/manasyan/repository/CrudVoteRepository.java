package ru.manasyan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.manasyan.model.Dish;
import ru.manasyan.model.Vote;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudVoteRepository extends JpaRepository<Vote, Integer> {

    @Transactional
    Vote save(Vote vote);

    @Query("SELECT v FROM Vote v WHERE v.user_id=:user_id AND v.date=:date")
    Vote get(@Param("user_id") int user_id, @Param("date") LocalDate startDate);

}
