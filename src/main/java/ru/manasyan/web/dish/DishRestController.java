package ru.manasyan.web.dish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.manasyan.model.Dish;
import ru.manasyan.model.Vote;
import ru.manasyan.repository.DataJpaDishRepository;
import ru.manasyan.repository.DataJpaVoteRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping(value = "/rest/dish_menu",  produces = MediaType.APPLICATION_JSON_VALUE)
public class DishRestController {

    @Autowired
    private DataJpaDishRepository dishRepository;

    @Autowired
    private DataJpaVoteRepository voteRepository;


//    public Dish get(int id) {
//        //int userId = SecurityUtil.authUserId();
//        return repository.get(id, 1);
//    }
    @GetMapping
    public List<Dish> getAll() {
        return dishRepository.getAll(LocalDate.now());
    }

    @GetMapping("/{date}")
    public List<Dish> getAllByDate( @DateTimeFormat(pattern = "yyyy-MM-dd") @PathVariable("date") LocalDate date) {
        return dishRepository.getAll(date);
    }

    public boolean vote(int restaurant_id) {
        //int userId = SecurityUtil.authUserId();
        int user_id = 100000;
        return canVote() ? voteRepository.update(user_id, restaurant_id, currentDateTime().toLocalDate()) : false;
    }

    private LocalDateTime currentDateTime() {
        return LocalDateTime.now();
    }

    public boolean canVote() {
        return (currentDateTime().toLocalTime().isAfter(LocalTime.of(11,00)))
                ? false : true;
    }

}
