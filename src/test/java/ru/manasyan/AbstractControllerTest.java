package ru.manasyan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import ru.manasyan.repository.*;
import ru.manasyan.service.VoteHistoryService;
import ru.manasyan.service.VoteService;
import ru.manasyan.web.ExceptionInfoHandler;

import javax.annotation.PostConstruct;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@SpringJUnitWebConfig(locations = {
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-mvc.xml",
        "classpath:spring/spring-db.xml"
})

@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))

public abstract class AbstractControllerTest {

    protected static final String REST_URL_MENU = "/rest/";
    protected static final String REST_URL_PROFILE = "/rest/profile/";
    protected static final String REST_URL_RESTAURANT = "/rest/admin/restaurants/";

    protected static Logger log = LoggerFactory.getLogger(ExceptionInfoHandler.class);

    private static final CharacterEncodingFilter CHARACTER_ENCODING_FILTER = new CharacterEncodingFilter();
    static {
        CHARACTER_ENCODING_FILTER.setEncoding("UTF-8");
        CHARACTER_ENCODING_FILTER.setForceEncoding(true);
    }

    protected MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @Autowired
    protected CrudDishRepository dishRepository;

    @Autowired
    protected CrudRestaurantRepository restaurantRepository;

    @Autowired
    protected VoteService voteService;

    @Autowired
    protected VoteHistoryService historyService;

    @Autowired
    protected CrudUserRepository userRepository;

    @PostConstruct
    private void postConstruct() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilter(CHARACTER_ENCODING_FILTER)
                .apply(springSecurity())
                .build();
    }
}
