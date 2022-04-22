package tqs.hw.covidtracker.integration;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.isA;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class TrackerRestControllerITest {
    
    @LocalServerPort
    int randomServerPort;

    @Autowired
    private MockMvc mvc;


    private void expectNumberFields(ResultActions actions) throws Exception {
        actions
            .andExpect(jsonPath("$.totalCases", isA(Number.class)))
            .andExpect(jsonPath("$.newCases", isA(Number.class)))
            .andExpect(jsonPath("$.activeCases", isA(Number.class)))
            .andExpect(jsonPath("$.newActiveCases", isA(Number.class)))
            .andExpect(jsonPath("$.deathTotal", isA(Number.class)))
            .andExpect(jsonPath("$.newDeaths", isA(Number.class)))
            .andExpect(jsonPath("$.recovered", isA(Number.class)))
            .andExpect(jsonPath("$.newRecovered", isA(Number.class)));
    }


    @Test
    void whenGetIncidence_thenReturnData_thenStatus200() throws Exception {
        ResultActions actions = mvc.perform(get("/api/v1/incidence").contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasKey("day")));
        expectNumberFields(actions);
    }

    @Test
    void whenGetIncidenceWithIso_thenReturnData_thenStatus200() throws Exception {
        ResultActions actions = mvc.perform(get("/api/v1/incidence?iso=PRT").contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasKey("day")));
        expectNumberFields(actions);
    }

    @Test
    void whenGetIncidenceWithDate_thenReturnDataForDate_thenStatus200() throws Exception {
        ResultActions actions = mvc.perform(get("/api/v1/incidence?date=2022-04-12").contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.day", is("2022-04-12")));
        expectNumberFields(actions);
    }

    @Test
    void whenGetIncidenceWithStartAndEndDate_thenReturnDataForPeriod_thenStatus200() throws Exception {
        ResultActions actions = mvc.perform(get("/api/v1/incidence?start=2022-04-12&end=2022-04-14").contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.day", is("2022-04-14")));
        expectNumberFields(actions);
    }

    @Test
    void whenGetIncidenceWithStartDateLaterThanEndDate_thenStatus400() throws Exception {
        mvc.perform(get("/api/v1/incidence?start=2022-04-15&end=2022-04-03").contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isBadRequest());
    }

    @Test
    void whenGetIncidenceWithInvalidDateFormat_thenStatus400() throws Exception {
        mvc.perform(get("/api/v1/incidence?date=badformat").contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isBadRequest());
    }

    @Test
    void whenGetCacheStats_thenReturnStats_thenStatus200() throws Exception {
        mvc.perform(get("/api/v1/cache_stats").contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.hits", isA(Number.class)))
            .andExpect(jsonPath("$.time-to-live", isA(Number.class)))
            .andExpect(jsonPath("$.misses", isA(Number.class)))
            .andExpect(jsonPath("$.requests", isA(Number.class)));
    }


}
