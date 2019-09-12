package ru.manasyan.web.admin;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.manasyan.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static ru.manasyan.TestUtil.userHttpBasic;
import static ru.manasyan.UserTestData.ADMIN;

public class VotesRestControllerTest extends AbstractControllerTest {
    @Test
    void votesToday() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL_RESTAURANT + "votes")
                .with(userHttpBasic(ADMIN)))
                .andDo(print());
    }

    @Test
    void votesHistory() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL_RESTAURANT + "votes/2019-08-27")
                .with(userHttpBasic(ADMIN)))
                .andDo(print());
    }

    @Test
    void votesHistoryRestaurant() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL_RESTAURANT + "100005/votes")
                .with(userHttpBasic(ADMIN)))
                .andDo(print());
    }

}
