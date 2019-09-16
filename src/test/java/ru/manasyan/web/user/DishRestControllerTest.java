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
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void vote() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL_MENU + "/vote/100006")
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string("true"));
    }

    @Test
    void history() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL_MENU + "/vote/history")
                .with(userHttpBasic(USER3)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void historyMenu() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL_MENU + "/restaurants/100005/2019-08-26")
                .with(userHttpBasic(USER3)))
                .andExpect(status().isOk())
                .andDo(print());
    }

}
