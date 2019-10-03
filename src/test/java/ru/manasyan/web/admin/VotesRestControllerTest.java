package ru.manasyan.web.admin;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.manasyan.AbstractControllerTest;

import java.time.*;


import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static ru.manasyan.TestUtil.userHttpBasic;
import static ru.manasyan.UserTestData.ADMIN;

public class VotesRestControllerTest extends AbstractControllerTest {



    @Test
    void votesToday() throws Exception {

//        Clock clock = Clock.fixed(Instant.parse("2018-08-19T16:02:42.00Z"), ZoneId.of("UTC"));
//        String dateTimeExpected = "2018-08-19T16:02:42";
//
//        LocalDateTime dateTime = LocalDateTime.now(clock);
//        assertThat(dateTime.toString()).isEqualTo(dateTimeExpected);


        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL_RESTAURANT + "votes")
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string("[{\"id\":100033,\"restaurant_id\":100005,\"restaurant\":\"Cafe Pushkin\",\"date\":\""+
                        LocalDate.now()+"\",\"votes\":1},{\"id\":100034,\"restaurant_id\":100006,\"restaurant\":\"White Rabbit\",\"date\":\""+
                        LocalDate.now()+"\",\"votes\":1}]"));
    }

    @Test
    void votesHistoryByDate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL_RESTAURANT + "votes/2019-08-27")
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string("[{\"id\":100031,\"restaurant_id\":100005,\"restaurant\":\"Cafe Pushkin\",\"date\":\"2019-08-27\",\"votes\":2},{\"id\":100032,\"restaurant_id\":100006,\"restaurant\":\"White Rabbit\",\"date\":\"2019-08-27\",\"votes\":1}]"));
    }

    @Test
    void votesHistoryByRestaurant() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL_RESTAURANT + "100005/votes")
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string("[{\"id\":100030,\"restaurant_id\":100005,\"restaurant\":\"Cafe Pushkin\",\"date\":\"2019-08-26\",\"votes\":1},{\"id\":100031,\"restaurant_id\":100005,\"restaurant\":\"Cafe Pushkin\",\"date\":\"2019-08-27\",\"votes\":2},{\"id\":100033,\"restaurant_id\":100005,\"restaurant\":\"Cafe Pushkin\",\"date\":\""+
                        LocalDate.now()+"\",\"votes\":1}]"));
    }

}
