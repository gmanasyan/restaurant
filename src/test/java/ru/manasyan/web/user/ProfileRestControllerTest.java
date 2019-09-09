package ru.manasyan.web.user;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.manasyan.AbstractControllerTest;
import ru.manasyan.model.Role;
import ru.manasyan.model.User;
import ru.manasyan.web.json.JsonUtil;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.manasyan.TestUtil.userHttpBasic;
import static ru.manasyan.UserTestData.*;
import static ru.manasyan.model.Role.ROLE_USER;

public class ProfileRestControllerTest extends AbstractControllerTest {

    protected static final String REST_URL = "/rest/profile/";

    @Test
    void get() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
                //.andExpect(contentJson(USER1));
    }

    @Test
    void getUnAuth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void register() throws Exception {
        User newUser = new User(null, "UserName", "useremail@gmail", "newPassword", ROLE_USER);

        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newUser)))
                .andDo(print())
                .andExpect(status().isCreated());

        //User returned = readFromJson(action, User.class);

//        User created = UserUtil.createNewFromTo(createdTo);
//        created.setId(returned.getId());
//
//        assertMatch(returned, created);
        //assertMatch(userService.getByEmail("newemail@ya.ru"), created);
    }

    @Test
    void update() throws Exception {
        User newUser = new User(null, "UserName", "useremail@gmail", "newPassword", ROLE_USER);
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL).contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER1))
                .content(JsonUtil.writeValue(newUser)))
                .andDo(print())
                .andExpect(status().isNoContent());

        //assertMatch(userService.getByEmail("newemail@ya.ru"), UserUtil.updateFromTo(new User(USER), updatedTo));
    }

}
