package ru.manasyan.web.admin;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.manasyan.AbstractControllerTest;
import ru.manasyan.model.Restaurant;
import ru.manasyan.web.json.JsonUtil;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.manasyan.TestUtil.readFromJson;
import static ru.manasyan.TestUtil.userHttpBasic;
import static ru.manasyan.UserTestData.ADMIN;

public class RestaurantRestControllerTest extends AbstractControllerTest {

    @Test
    void allRestaurants() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL_RESTAURANT)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string("{\"Restaurant{id=100005, dateTime=2019-08-16T13:00, name='Cafe Pushkin'}\":[{\"id\":100019,\"name\":\"Summer squash soup with creamed feta biscuits\",\"price\":875},{\"id\":100020,\"name\":\"Girolle, lemon & parsley risotto\",\"price\":1500},{\"id\":100021,\"name\":\"Mediterranean salad with quinoa, beetroot, datterini & olives\",\"price\":2250}],\"Restaurant{id=100004, dateTime=2019-08-16T13:00, name='The Ivy'}\":[],\"Restaurant{id=100006, dateTime=2019-08-16T13:00, name='White Rabbit'}\":[{\"id\":100022,\"name\":\"The Ivy vegetarian Shepherds Pie\",\"price\":1433},{\"id\":100023,\"name\":\"Linguine primavera \",\"price\":1600}]}"));
    }

    @Test
    void newRestaurant() throws Exception {
        Restaurant restaurant = new Restaurant(null, "The New Restaurant", null);
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL_RESTAURANT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(restaurant))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk());

        Restaurant returned = readFromJson(action, Restaurant.class);
        assertThat(returned).isEqualToIgnoringGivenFields(restaurant, "dateTime", "id");
    }

    @Test
    void updateRestaurant() throws Exception {
        Restaurant restaurant = new Restaurant(100005, "New Restaurant", null);
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL_RESTAURANT+ "/100005")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(restaurant))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string("{\"id\":100005,\"name\":\"New Restaurant\",\"dateTime\":\"2019-08-16T13:00:00\"}"));
    }

    @Test
    void deleteRestaurant() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL_RESTAURANT + "100006")
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());

        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL_RESTAURANT + "100006")
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    void updateRestaurantConflict() throws Exception {
        Restaurant restaurant = new Restaurant(null, "The Ivy", null);
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL_RESTAURANT+ "/100005")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(restaurant))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isConflict());
    }
}
