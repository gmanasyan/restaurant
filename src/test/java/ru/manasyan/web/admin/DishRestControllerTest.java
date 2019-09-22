package ru.manasyan.web.admin;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.manasyan.AbstractControllerTest;
import ru.manasyan.model.Dish;
import ru.manasyan.web.json.JsonUtil;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.manasyan.DishTestData.assertMatch;
import static ru.manasyan.TestUtil.*;
import static ru.manasyan.UserTestData.ADMIN;

public class DishRestControllerTest extends AbstractControllerTest {

    @Test
    void newDish() throws Exception {
        Dish dish = new Dish(null, "New Dish", 2050, null, null);
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL_RESTAURANT + "100006/dishes")
                .contentType(MediaType.APPLICATION_JSON)
//                .param("name", "New dish")
//                .param("price", "20.10")
                .content(JsonUtil.writeValue(dish))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk());

        Dish dishReturned = readFromJson(result, Dish.class);
        dish.setId(dishReturned.getId());
        dish.setRestaurant(dishReturned.getRestaurant());
        dish.setDate(dish.getDate());

        assertMatch(dishReturned, dish);
   }

    @Test
    void updateDish() throws Exception {
        Dish dish = new Dish(null, "New Dish", 2050, null, null);
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL_RESTAURANT + "100006/dishes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(dish))
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk());

        Dish newDish = readFromJson(result, Dish.class);

        Dish dishUpdated = new Dish(newDish.getId(), "Updated dish", 3510, null, null);
        ResultActions resultUpdate = mockMvc.perform(MockMvcRequestBuilders.put(REST_URL_RESTAURANT + "dishes/" + newDish.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(dishUpdated))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk());

        Dish dishReturned = readFromJson(resultUpdate, Dish.class);
        assertMatch(dishReturned, dishUpdated);

    }

    @Test
    void updateDishWrongId() throws Exception {
        Dish dishUpdate = new Dish(100015, "Updated dish", 3510, null, null);
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL_RESTAURANT + "dishes/100014")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(dishUpdate))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void updateNotTodayDish() throws Exception {
        Dish dishUpdate = new Dish(100015, "Updated dish", 3510, null, null);
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL_RESTAURANT + "dishes/100015")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(dishUpdate))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    void deleteDish() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL_RESTAURANT + "dishes/100007")
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertMatch(dishRepository.getWithRestaurant(100007), null);
    }

    @Test
    void newDishNotValid() throws Exception {
        Dish dish = new Dish(null, "New Dish", 5, null, null);
        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL_RESTAURANT + "100006/dishes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(dish))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}
