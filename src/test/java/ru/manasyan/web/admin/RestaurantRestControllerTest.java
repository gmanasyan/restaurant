package ru.manasyan.web.admin;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.manasyan.AbstractControllerTest;
import ru.manasyan.model.Restaurant;
import ru.manasyan.web.json.JsonUtil;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.manasyan.TestUtil.userHttpBasic;
import static ru.manasyan.UserTestData.ADMIN;

public class RestaurantRestControllerTest extends AbstractControllerTest {

    @Test
    void allRestaurants() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL_RESTAURANT)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        //.andExpect(result -> assertMatch(readFromJsonMvcResult(result, Meal.class), ADMIN_MEAL1));
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void newRestaurant() throws Exception {
        Restaurant restaurant = new Restaurant(null, "The New Restaurant", null);
        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL_RESTAURANT)
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
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL_RESTAURANT+ "/100006")
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
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL_RESTAURANT + "100006")
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());

        System.out.println(restaurantRepository.getAll());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void updateRestaurantConflict() throws Exception {
        Restaurant restaurant = new Restaurant(null, "The Ivy", null);
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL_RESTAURANT+ "/100006")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(restaurant))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        //.andExpect(result -> assertMatch(readFromJsonMvcResult(result, Meal.class), ADMIN_MEAL1));
        //System.out.println(restaurantRepository.getAll());
    }


}
