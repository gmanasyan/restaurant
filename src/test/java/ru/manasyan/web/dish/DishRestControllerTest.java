package ru.manasyan.web.dish;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import ru.manasyan.model.Restaurant;
import ru.manasyan.repository.DataJpaDishRepository;
import ru.manasyan.repository.DataJpaRestaurantRepository;

import javax.annotation.PostConstruct;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitWebConfig(locations = {
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-mvc.xml",
        "classpath:spring/spring-db.xml"
})
//@WebAppConfiguration
//@ExtendWith(SpringExtension.class)
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

    @PostConstruct
    private void postConstruct() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilter(CHARACTER_ENCODING_FILTER)
                //.apply(springSecurity())
                .build();
    }

    @Test
    void get() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "2019-08-16"))
                //.with(userHttpBasic(ADMIN)))
                //.andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
                //.andExpect(result -> assertMatch(readFromJsonMvcResult(result, Meal.class), ADMIN_MEAL1));
    }

    @Test
    void vote() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + "/vote/100006"))
                //.with(userHttpBasic(ADMIN)))
                //.andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        //.andExpect(result -> assertMatch(readFromJsonMvcResult(result, Meal.class), ADMIN_MEAL1));
    }



// ------------------------- Restaurants Test ---------------------------------------

    @Test
    void newRestaurant() throws Exception {

        //Restaurant restaurant = new Restaurant(null, );
        //{"id":100002,"dateTime":"2015-05-30T10:00:00","description":"Обновленный завтрак","calories":200}

        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL_2)
                .param("name", "Test Restaurant"))
                //.contentType(MediaType.APPLICATION_JSON)
                //.content("{\"name\":\"test\"}"))
                //.content("Test name"))
                //.with(userHttpBasic(ADMIN)))
                //.andExpect(status().isOk())
                .andDo(print())
                .andExpect(status().isOk());
                //.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
                //.andExpect(result -> assertMatch(readFromJsonMvcResult(result, Meal.class), ADMIN_MEAL1));
    }

    @Test
    void newDish() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL_2 + "100006")
                .param("name", "New dish")
                .param("price", "20.10"))
                //.contentType(MediaType.APPLICATION_JSON)
                //.content("{\"name\":\"test\"}"))
                //.content("Test name"))
                //.with(userHttpBasic(ADMIN)))
                //.andExpect(status().isOk())
                .andDo(print())
                .andExpect(status().isOk());
        //.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        //.andExpect(result -> assertMatch(readFromJsonMvcResult(result, Meal.class), ADMIN_MEAL1));
    }

    @Test
    void allRestaurants() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL_2))
                //.with(userHttpBasic(ADMIN)))
                //.andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        //.andExpect(result -> assertMatch(readFromJsonMvcResult(result, Meal.class), ADMIN_MEAL1));
    }

    @Test
    void deleteRestaurant() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL_2 + "100006"))
                .andDo(print())
                .andExpect(status().isNoContent());

        System.out.println(restaurantRepository.getAll());
    }

    @Test
    void deleteDish() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL_2 + "dish/100007"))
                .andDo(print())
                .andExpect(status().isNoContent());

        System.out.println(dishRepository.getAll(LocalDate.of(2019, 8, 16)));
    }



}