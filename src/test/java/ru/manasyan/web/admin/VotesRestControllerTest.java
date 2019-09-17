package ru.manasyan.web.admin;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.manasyan.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static ru.manasyan.TestUtil.userHttpBasic;
import static ru.manasyan.UserTestData.ADMIN;

public class VotesRestControllerTest extends AbstractControllerTest {

    @Test
    void votesToday() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL_RESTAURANT + "votes")
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string("{\"Restaurant{id=100005, dateTime=2019-08-16T13:00, name='Cafe Pushkin'}\":2,\"Restaurant{id=100006, dateTime=2019-08-16T13:00, name='White Rabbit'}\":1}"));
    }

    @Test
    void votesHistory() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL_RESTAURANT + "votes/2019-08-27")
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string("[{\"id\":100024,\"restaurant\":{\"id\":100005,\"name\":\"Cafe Pushkin\",\"dateTime\":\"2019-08-16T13:00:00\"},\"date\":\"2019-08-27\",\"votes\":2},{\"id\":100025,\"restaurant\":{\"id\":100006,\"name\":\"White Rabbit\",\"dateTime\":\"2019-08-16T13:00:00\"},\"date\":\"2019-08-27\",\"votes\":1}]"));
    }

    @Test
    void votesHistoryRestaurant() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL_RESTAURANT + "100005/votes")
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string("[{\"id\":100023,\"restaurant_id\":100005,\"date\":\"2019-08-26\",\"votes\":1},{\"id\":100024,\"restaurant_id\":100005,\"date\":\"2019-08-27\",\"votes\":2}]"));
    }

}
