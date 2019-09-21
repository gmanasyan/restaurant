package ru.manasyan.web.user;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.manasyan.AbstractControllerTest;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.manasyan.TestUtil.userHttpBasic;
import static ru.manasyan.UserTestData.*;
import static ru.manasyan.UserTestData.USER3;

public class DishRestControllerTest extends AbstractControllerTest {

    @Test
    void get() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL_MENU + "2019-08-16")
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string("{\"Restaurant{id=100005, dateTime=2019-08-16T13:00, name='Cafe Pushkin'}\":[{\"id\":100007,\"name\":\"Summer squash soup with creamed feta biscuits\",\"price\":875},{\"id\":100008,\"name\":\"Girolle, lemon & parsley risotto\",\"price\":1500},{\"id\":100009,\"name\":\"Mediterranean salad with quinoa, beetroot, datterini & olives\",\"price\":2250}],\"Restaurant{id=100006, dateTime=2019-08-16T13:00, name='White Rabbit'}\":[{\"id\":100016,\"name\":\"Squash tabbouleh \",\"price\":1275},{\"id\":100017,\"name\":\"The Ivy vegetarian Shepherds Pie\",\"price\":1433},{\"id\":100018,\"name\":\"Linguine primavera \",\"price\":1600}]}"));
    }

    @Test
    void vote() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL_MENU + "/vote/100006")
                .with(userHttpBasic(USER3)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string("true"));
    }

    @Test
    void history() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL_MENU + "/vote/history")
                .with(userHttpBasic(USER3)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string("[{\"id\":100024,\"user_id\":100003,\"restaurant_id\":100005,\"date\":\"2019-08-26\"},{\"id\":100025,\"user_id\":100003,\"restaurant_id\":100005,\"date\":\"2019-08-27\"}]"));
    }

    @Test
    void historyMenu() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL_MENU + "/restaurants/100005/2019-08-26")
                .with(userHttpBasic(USER3)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string("[{\"id\":100010,\"name\":\"Summer squash soup with creamed feta biscuits\",\"price\":875,\"date\":\"2019-08-26\"},{\"id\":100011,\"name\":\"Girolle, lemon & parsley risotto\",\"price\":1500,\"date\":\"2019-08-26\"},{\"id\":100012,\"name\":\"Mediterranean salad with quinoa, beetroot, datterini & olives\",\"price\":2250,\"date\":\"2019-08-26\"}]"));

    }

}
