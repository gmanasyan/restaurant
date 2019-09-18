package ru.manasyan.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import ru.manasyan.model.History;

import java.time.LocalDate;
import java.util.List;

@Repository
public class DataJpaVoteHistoryRepository {

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

    public void add(Integer restaurant_id, LocalDate date) {
        History history = crudVoteHistoryRepository.get(date, restaurant_id);

        if (history == null) {
            history = new History(null, restaurant_id, date, 1);
        } else {
            history.addVotes(1);
        }

        crudVoteHistoryRepository.save(history);
    }

    public void remove(Integer restaurant_id, LocalDate date) throws DataIntegrityViolationException {
        History history = crudVoteHistoryRepository.get(date, restaurant_id);

        if ((history != null) && (history.getVotes() > 0)) {
            history.removeVotes(1);
        } else {
            throw new DataIntegrityViolationException("Vote history database error.");
        }
        crudVoteHistoryRepository.save(history);
    }

}
