package ru.manasyan.web.dish;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import ru.manasyan.model.Dish;
import ru.manasyan.model.Restaurant;
import ru.manasyan.repository.DataJpaDishRepository;
import ru.manasyan.repository.DataJpaRestaurantRepository;
import ru.manasyan.repository.DataJpaVoteHistoryRepository;
import ru.manasyan.repository.DataJpaVoteRepository;
import ru.manasyan.to.DishTo;
import ru.manasyan.web.json.JsonUtil;

import javax.annotation.PostConstruct;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.manasyan.TestUtil.userHttpBasic;
import static ru.manasyan.UserTestData.*;

@SpringJUnitWebConfig(locations = {
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-mvc.xml",
        "classpath:spring/spring-db.xml"
})

@Transactional
class DishRestControllerTest {

    private static final String REST_URL = "/rest/menu/";
    private static final String REST_URL_2 = "/rest/restaurants/";

    private static final CharacterEncodingFilter CHARACTER_ENCODING_FILTER = new CharacterEncodingFilter();
    static {
        CHARACTER_ENCODING_FILTER.setEncoding("UTF-8");
        CHARACTER_ENCODING_FILTER.setForceEncoding(true);
    }

    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private DataJpaRestaurantRepository restaurantRepository;

    @Autowired
    private DataJpaDishRepository dishRepository;

    @Autowired
    private DataJpaVoteRepository voteRepository;

    @Autowired
    private DataJpaVoteHistoryRepository historyRepository;

    @PostConstruct
    private void postConstruct() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilter(CHARACTER_ENCODING_FILTER)
                .apply(springSecurity())
                .build();
    }

    @Test
    void get() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "2019-08-16")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
                //.andExpect(result -> assertMatch(readFromJsonMvcResult(result, Meal.class), ADMIN_MEAL1));
    }

    @Test
    void vote() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + "/vote/100006")
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andDo(print());
                //.andExpect(result -> result.getResponse().getContentAsString().equals("true"));

        System.out.println(voteRepository.get(USER1.getId(), LocalDate.now()));
        System.out.println(historyRepository.get(LocalDate.now()));

    }

    @Test
    void history() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + "/vote/history")
                .with(userHttpBasic(USER3)))
                .andExpect(status().isOk())
                .andDo(print());
        //.andExpect(result -> result.getResponse().getContentAsString().equals("true"));

    }

    @Test
    void historyMenu() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + "/restaurants/100005/2019-08-26")
                .with(userHttpBasic(USER3)))
                .andExpect(status().isOk())
                .andDo(print());
        //.andExpect(result -> result.getResponse().getContentAsString().equals("true"));

    }

// ------------------------- Restaurants Test ---------------------------------------

    @Test
    void allRestaurants() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL_2)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        //.andExpect(result -> assertMatch(readFromJsonMvcResult(result, Meal.class), ADMIN_MEAL1));
    }

    @Test
    void newRestaurant() throws Exception {
        Restaurant restaurant = new Restaurant(null, "The Ivy", null);
        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL_2)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(restaurant))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
                //.andExpect(result -> assertMatch(readFromJsonMvcResult(result, Meal.class), ADMIN_MEAL1));
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void updateRestaurant() throws Exception {
        Restaurant restaurant = new Restaurant(100006, "New Restaurant", null);
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL_2+ "/100006")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(restaurant))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        //.andExpect(result -> assertMatch(readFromJsonMvcResult(result, Meal.class), ADMIN_MEAL1));
        //System.out.println(restaurantRepository.getAll());
    }

    @Test
    void deleteRestaurant() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL_2 + "100006")
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());

        System.out.println(restaurantRepository.getAll());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void updateRestaurantConflict() throws Exception {
        Restaurant restaurant = new Restaurant(null, "The Ivy", null);
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL_2+ "/100006")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(restaurant))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        //.andExpect(result -> assertMatch(readFromJsonMvcResult(result, Meal.class), ADMIN_MEAL1));
        //System.out.println(restaurantRepository.getAll());
    }


    // ------------------------- Dish Tests -----------------------------

    @Test
    void newDish() throws Exception {
        Dish dish = new Dish(null, "New Dish", 2050, null, null);
        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL_2 + "100006/dishes")
                .contentType(MediaType.APPLICATION_JSON)
//                .param("name", "New dish")
//                .param("price", "20.10")
                .content(JsonUtil.writeValue(dish))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk());
        //.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        //.andExpect(result -> assertMatch(readFromJsonMvcResult(result, Meal.class), ADMIN_MEAL1));
    }

    @Test
    void updateDish() throws Exception {

        Dish dish = new Dish(null, "New Dish", 2050, null, null);
        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL_2 + "100006/dishes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(dish))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk());

        Dish dishUpdate = new Dish(100020, "Updated dish", 3510, null, null);
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL_2 + "dishes/100020")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(dishUpdate))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk());

        System.out.println(dishRepository.get(100020));
    }

    @Test
    void updateDishWrongId() throws Exception {
        Dish dishUpdate = new Dish(100015, "Updated dish", 3510, null, null);
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL_2 + "dishes/100014")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(dishUpdate))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }


    @Test
    void updateNotTodayDish() throws Exception {
        Dish dishUpdate = new Dish(100015, "Updated dish", 3510, null, null);
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL_2 + "dishes/100015")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(dishUpdate))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isConflict());
    }


    @Test
    void deleteDish() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL_2 + "dishes/100007")
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());

        System.out.println(dishRepository.getAll(LocalDate.of(2019, 8, 16)));
    }

    @Test
    void newDishNotValid() throws Exception {
        Dish dish = new Dish(null, "New Dish", 5, null, null);
        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL_2 + "100006/dishes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(dish))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }


    @Test
    void votesToday() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL_2 + "votes")
                .with(userHttpBasic(ADMIN)))
                .andDo(print());
    }

    @Test
    void votesHistory() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL_2 + "votes/2019-08-27")
                .with(userHttpBasic(ADMIN)))
                .andDo(print());
    }



}