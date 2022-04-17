package tqs.hw.covidtracker;

import java.text.ParseException;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class IncidenceDataUnitTests {
    
    @Test
    void testConstructor() throws ParseException {
        HashMap<String, Object> map = new HashMap<>();
        map.put("date", "2020-04-16");
        map.put("confirmed", 18800);
        map.put("deaths", 630);
        map.put("recovered", 500);
        map.put("confirmed_diff", 750);
        map.put("deaths_diff", 30);
        map.put("recovered_diff", 110);
        map.put("active", 17700);
        map.put("active_diff", 610);

        HashMap<String, Object> data = new HashMap<>();
        data.put("data", map);

        JSONObject json = new JSONObject(data);
        IncidenceData incidenceData = new IncidenceData(json);

        assertThat(incidenceData.getDay()).isEqualTo("2020-04-16");
        assertThat(incidenceData.getTotalCases()).isEqualTo(18800);
        assertThat(incidenceData.getDeathTotal()).isEqualTo(630);
        assertThat(incidenceData.getRecovered()).isEqualTo(500);
        assertThat(incidenceData.getNewCases()).isEqualTo(750);
        assertThat(incidenceData.getNewDeaths()).isEqualTo(30);
        assertThat(incidenceData.getNewRecovered()).isEqualTo(110);
        assertThat(incidenceData.getActiveCases()).isEqualTo(17700);
        assertThat(incidenceData.getNewActiveCases()).isEqualTo(610);
    }

}
