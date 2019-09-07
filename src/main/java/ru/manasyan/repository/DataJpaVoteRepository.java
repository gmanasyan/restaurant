package ru.manasyan.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.manasyan.model.Vote;
import ru.manasyan.to.VotesStatistics;

import java.time.LocalDate;
import java.util.List;

@Repository
public class DataJpaVoteRepository {

    @Autowired
    private CrudVoteRepository crudVoteRepository;

    @Autowired
    private DataJpaVoteHistoryRepository historyRepository;

    @Transactional
    public boolean update(int user_id, int restaurant_id, LocalDate date) {
        Vote vote = get(user_id, date);
        if (vote != null) {
            vote.setRestaurant_id(restaurant_id);
        }
        else {
            vote = new Vote(null, user_id, restaurant_id, date);
        }
        Vote savedVote = crudVoteRepository.save(vote);

        if (savedVote != null) {
            // Update Vote History
            historyRepository.add(restaurant_id, date);
        }

        return (savedVote != null) ? true : false;
    }

    public Vote get(int user_id, LocalDate date) {
        Vote vote = crudVoteRepository.get(user_id, date);
        return vote;
    }

    public List<VotesStatistics> getToday(LocalDate date) {
        List<VotesStatistics> votes = crudVoteRepository.getToday(date);
        return votes;
    }

    public List<Vote> history(int user_id) {
        List<Vote> votes = crudVoteRepository.getByUserId(user_id);
        return votes;
    }

}
