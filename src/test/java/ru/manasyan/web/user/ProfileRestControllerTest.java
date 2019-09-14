package ru.manasyan.web.user;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.manasyan.AbstractControllerTest;
import ru.manasyan.model.Role;
import ru.manasyan.model.User;
import ru.manasyan.to.UserToIn;
import ru.manasyan.web.json.JsonUtil;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.manasyan.TestUtil.readFromJson;
import static ru.manasyan.TestUtil.userHttpBasic;
import static ru.manasyan.UserTestData.*;
import static ru.manasyan.model.Role.ROLE_ADMIN;
import static ru.manasyan.model.Role.ROLE_USER;

public class ProfileRestControllerTest extends AbstractControllerTest {

    @Test
    void get() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL_PROFILE)
                .with(userHttpBasic(USER2)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
                //.andExpect(contentJson(USER1));
    }

    @Test
    void getUnAuth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL_PROFILE))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void register() throws Exception {
        User newUser = new User(null, "UserName", "useremail@gmail", "newPassword", ROLE_USER);

        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL_PROFILE + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newUser)))
                .andDo(print())
                .andExpect(status().isCreated());

        User returned = readFromJson(action, User.class);

        newUser.setId(returned.getId());
        assertMatch(returned, newUser);
    }

    @Test
    void update() throws Exception {
        UserToIn newUser = new UserToIn(null, "UserName", "newPassword");
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL_PROFILE).contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER1))
                .content(JsonUtil.writeValue(newUser)))
                .andDo(print())
                .andExpect(status().isNoContent());

        log.info(userRepository.get(USER1_ID).toString());
    }

}
