package ru.manasyan.web.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.manasyan.repository.DataJpaDishRepository;
import ru.manasyan.repository.DataJpaRestaurantRepository;
import ru.manasyan.repository.DataJpaVoteHistoryRepository;
import ru.manasyan.repository.DataJpaVoteRepository;
import ru.manasyan.web.ExceptionInfoHandler;

public class AbstractRestController {

    static final String REST_URL = "/rest/restaurants";

    protected Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    protected DataJpaRestaurantRepository restaurantRepository;

    @Autowired
    protected DataJpaDishRepository dishRepository;

    @Autowired
    protected DataJpaVoteRepository voteRepository;

    @Autowired
    protected DataJpaVoteHistoryRepository historyRepository;

}
