package ru.manasyan.web.admin;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.manasyan.model.History;
import ru.manasyan.model.Restaurant;
import ru.manasyan.to.VotesStatistics;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class VoteRestController extends AbstractRestController {

    // Ver 1| Search by votes database for today
    @GetMapping("/votes")
    public Map<Restaurant, Long> todayVotes() {
        log.info("View today votes for all restaurants");

        List<VotesStatistics> votes = voteRepository.getToday(LocalDate.of(2019,8,27));

        Map<Restaurant, Long> votesRestaurant = votes.stream()
                .collect(Collectors.toMap(v -> restaurantRepository.get(v.getRestaurant()), v -> v.getVotes() ));

        return votesRestaurant;
    }

    // Ver 2| Search by history database for speed
    @GetMapping("/votes/{date}")
    public List<History> votesByDate(@DateTimeFormat(pattern = "yyyy-MM-dd") @PathVariable("date") LocalDate date) {
        log.info("View votes by date {} for all restaurants", date);
        List<History> history = historyRepository.get(date);
        // no need history id
        history.forEach(h -> h.setId(null));
        return history;
    }

    @GetMapping("{id}/votes")
    public List<History> votesByRestaurant(@PathVariable("id") int id) {
        log.info("View all votes for restaurant {}", id);
        List<History> history = historyRepository.get(id);
        // no need history id
        history.forEach(h -> h.setId(null));
        return history;
    }
}
