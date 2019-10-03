package ru.manasyan.web.admin;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.manasyan.model.History;
import ru.manasyan.model.Restaurant;
import ru.manasyan.to.HistoryTo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.manasyan.web.admin.AbstractRestController.REST_URL;

@RestController
@RequestMapping(value = REST_URL,  produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteRestController extends AbstractRestController {

    @GetMapping("/votes")
    @Transactional(readOnly = true)
    public List<HistoryTo> todayVotes() {
         return votesByDate(LocalDate.now());
    }

    @GetMapping("/votes/{date}")
    @Transactional(readOnly = true)
    public List<HistoryTo> votesByDate(@DateTimeFormat(pattern = "yyyy-MM-dd") @PathVariable("date") LocalDate date) {
        log.info("View votes by date {} for all restaurants", date);
        List<History> history = historyService.get(date);
        return addRestaurant(history);
    }

    @GetMapping("{id}/votes")
    public List<HistoryTo> votesByRestaurant(@PathVariable("id") int id) {
        log.info("View all votes for restaurant {}", id);
        List<History> history = historyService.get(id);
        return addRestaurant(history);
    }

    private List<HistoryTo> addRestaurant(List<History> histories) {
       Map<Integer, Restaurant> restaurants = restaurants();
       return histories.stream()
               .map(h -> new HistoryTo(h.getId(), restaurants.get(h.getRestaurant_id()), h.getDate(), h.getVotes()))
               .collect(Collectors.toList());
    }
}
