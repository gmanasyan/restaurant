package ru.manasyan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.manasyan.model.Vote;
import ru.manasyan.repository.CrudVoteRepository;
import ru.manasyan.to.VotesStatistics;

import java.time.LocalDate;
import java.util.List;

@Service("voteService")
public class VoteService {

    @Autowired
    private CrudVoteRepository crudVoteRepository;

    @Autowired
    private VoteHistoryService historyService;

    @Transactional
    public boolean update(int user_id, int restaurant_id, LocalDate date) throws Exception {
        Vote vote = get(user_id, date);
        if (vote != null) {
            historyService.decrease(vote.getRestaurant_id(), date);
            vote.setRestaurant_id(restaurant_id);
        }
        else {
            vote = new Vote(null, user_id, restaurant_id, date);
        }
        Vote savedVote = crudVoteRepository.save(vote);

        if (savedVote != null) {
            // Update Vote History
            historyService.increase(restaurant_id, date);
        }

        return (savedVote != null) ? true : false;
    }

    public Vote get(int user_id, LocalDate date) {
        Vote vote = crudVoteRepository.get(user_id, date);
        return vote;
    }

    public List<VotesStatistics> get(LocalDate date) {
        List<VotesStatistics> votes = crudVoteRepository.get(date);
        return votes;
    }

    public List<Vote> history(int user_id) {
        List<Vote> votes = crudVoteRepository.getByUserId(user_id);
        return votes;
    }
}
