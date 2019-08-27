package ru.manasyan.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.manasyan.model.User;
import ru.manasyan.model.Vote;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Repository
public class DataJpaVoteRepository {

    @Autowired
    private CrudVoteRepository crudVoteRepository;

    public boolean update(int user_id, int restaurant_id, LocalDate date) {
        Vote vote = get(user_id, date);
        if (vote != null) { vote.setRestaurant_id(restaurant_id);}
        else { vote = new Vote(null, user_id, restaurant_id, date); }
        return (crudVoteRepository.save(vote) != null) ? true : false;
    }

    public Vote get(int user_id, LocalDate date) {
        Vote vote = crudVoteRepository.get(user_id, date);
        return vote;
    }

}
