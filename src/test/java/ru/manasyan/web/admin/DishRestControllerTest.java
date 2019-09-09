package ru.manasyan.web.admin;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.manasyan.AbstractControllerTest;
import ru.manasyan.model.Dish;
import ru.manasyan.web.json.JsonUtil;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.manasyan.TestUtil.userHttpBasic;
import static ru.manasyan.UserTestData.ADMIN;

public class DishRestControllerTest extends AbstractControllerTest {

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
}
