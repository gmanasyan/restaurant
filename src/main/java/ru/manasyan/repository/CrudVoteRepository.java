package ru.manasyan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.manasyan.model.Vote;
import ru.manasyan.to.VotesStatistics;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudVoteRepository extends JpaRepository<Vote, Integer> {

    @Transactional
    Vote save(Vote vote);

    @Query("SELECT v FROM Vote v WHERE v.user_id=:user_id AND v.date=:date")
    Vote get(@Param("user_id") int user_id, @Param("date") LocalDate startDate);

//    https://stackoverflow.com/questions/36328063/how-to-return-a-custom-object-from-a-spring-data-jpa-group-by-query
    @Query("SELECT " +
            "new ru.manasyan.to.VotesStatistics(v.restaurant_id, COUNT(v)) " +
            "FROM Vote v WHERE v.date=:date GROUP BY v.restaurant_id")
    List<VotesStatistics> get(@Param("date") LocalDate startDate);

    @Query("SELECT v FROM Vote v WHERE v.user_id=:user_id ORDER BY v.date ASC")
    List<Vote> getByUserId(@Param("user_id") int user_id);


}
