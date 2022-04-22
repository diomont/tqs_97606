package tqs.hw.covidtracker.unit;

import java.text.ParseException;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tqs.hw.covidtracker.IncidenceData;

import static org.assertj.core.api.Assertions.assertThat;

class IncidenceDataUnitTests {
    
    private IncidenceData incidenceData1;
    private IncidenceData incidenceData2;
    private IncidenceData incidenceData3;


    @BeforeEach
    void setup() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("date", "2020-04-16");
        map.put("confirmed", 18800L);
        map.put("deaths", 630L);
        map.put("recovered", 500L);
        map.put("confirmed_diff", 750L);
        map.put("deaths_diff", 30L);
        map.put("recovered_diff", 110L);
        map.put("active", 17700L);
        map.put("active_diff", 610L);

        HashMap<String, Object> data = new HashMap<>();
        data.put("data", map);

        JSONObject json = new JSONObject(data);
        incidenceData1 = new IncidenceData(json);
        incidenceData2 = new IncidenceData(json);

        map.put("date", "2022-03-20");
        map.put("confirmed", 50000L);
        JSONObject json2 = new JSONObject(data);
        incidenceData3 = new IncidenceData(json2);
    }


    @Test
    void testConstructor() throws ParseException {
        assertThat(incidenceData1.getDay()).isEqualTo("2020-04-16");
        assertThat(incidenceData1.getTotalCases()).isEqualTo(18800);
        assertThat(incidenceData1.getDeathTotal()).isEqualTo(630);
        assertThat(incidenceData1.getRecovered()).isEqualTo(500);
        assertThat(incidenceData1.getNewCases()).isEqualTo(750);
        assertThat(incidenceData1.getNewDeaths()).isEqualTo(30);
        assertThat(incidenceData1.getNewRecovered()).isEqualTo(110);
        assertThat(incidenceData1.getActiveCases()).isEqualTo(17700);
        assertThat(incidenceData1.getNewActiveCases()).isEqualTo(610);
    }

    @Test
    void testEquals() {
        assertThat(incidenceData1.hashCode()).isEqualTo(incidenceData2.hashCode());
        assertThat(incidenceData1).isEqualTo(incidenceData2);
        assertThat(incidenceData1).isNotEqualTo(incidenceData3);
    }

}
