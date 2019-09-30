package ru.manasyan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.manasyan.model.History;
import ru.manasyan.repository.CrudVoteHistoryRepository;

import java.time.LocalDate;
import java.util.List;

@Service("voteHistoryService")
public class VoteHistoryService {

    @Autowired
    private CrudVoteHistoryRepository crudVoteHistoryRepository;

    public List<History> get(LocalDate date) {
        List<History> histories = crudVoteHistoryRepository.get(date);
        return histories;
    }

    public List<History> get(int restaurantId) {
        List<History> histories = crudVoteHistoryRepository.get(restaurantId);
        return histories;
    }

    @Transactional
    public void increase(Integer restaurant_id, LocalDate date) {
        History history = crudVoteHistoryRepository.get(date, restaurant_id);

        if (history == null) {
            history = new History(null, restaurant_id, date, 1);
        } else {
            history.setVotes(history.getVotes() + 1);
        }

        crudVoteHistoryRepository.save(history);
    }

    @Transactional
    public void decrease(Integer restaurant_id, LocalDate date) throws Exception {
        History history = crudVoteHistoryRepository.get(date, restaurant_id);

        if ((history != null) && (history.getVotes() > 0)) {
            history.setVotes(history.getVotes() - 1);
        } else {
            throw new Exception("Vote history database error.");
        }
        crudVoteHistoryRepository.save(history);
    }
}
