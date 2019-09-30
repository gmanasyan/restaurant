package ru.manasyan.web.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import ru.manasyan.model.Restaurant;
import ru.manasyan.repository.*;
import ru.manasyan.service.VoteHistoryService;
import ru.manasyan.service.VoteService;
import ru.manasyan.web.ExceptionInfoHandler;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractRestController {

    static final String REST_URL = "/rest/admin/restaurants";

    protected Logger log = LoggerFactory.getLogger(getClass());

    @Value("${error.dbRestaurant}")
    protected String dbRestaurant;

    @Autowired
    protected CrudDishRepository dishRepository;

    @Autowired
    protected CrudRestaurantRepository restaurantRepository;

    @Autowired
    protected VoteService voteService;

    @Autowired
    protected VoteHistoryService historyService;

    protected Map<Integer, Restaurant> restaurants() {
        List<Restaurant> restaurants = restaurantRepository.getAll();
        return  restaurants.stream().collect(Collectors.toMap(r -> r.getId(), r -> r));
    }
}
