package tqs.hw.covidtracker;

import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ApiRequestServiceTests {
    
    @Mock(lenient = true)
    private JsonHttpClient client;

    @InjectMocks
    private ApiRequestService apiRequestService;

    private JSONObject pt1;
    private JSONObject pt2;
    private JSONObject pt3;
    private JSONObject glo1;
    private JSONObject glo2;
    private JSONObject glo3;
    private JSONObject regions;

    private IncidenceData pt1Data;
    private IncidenceData pt2Data;
    private IncidenceData pt3Data;
    private IncidenceData glo1Data;
    private IncidenceData glo2Data;
    private IncidenceData glo3Data;


    @BeforeEach
    public void setupEach() throws ParseException, java.text.ParseException {
        pt1 = (JSONObject) new JSONParser().parse(
            "{\"data\":[{\"date\":\"2020-04-16\",\"confirmed\":18841,\"deaths\":629,\"recovered\":493,\"confirmed_diff\":750,\"deaths_diff\":30,\"recovered_diff\":110,\"last_update\":\"2020-04-16 23:30:31\",\"active\":17719,\"active_diff\":610,\"fatality_rate\":0.0334,\"region\":{\"iso\":\"PRT\",\"name\":\"Portugal\",\"province\":\"\",\"lat\":\"39.3999\",\"long\":\"-8.2245\",\"cities\":[]}}]}"
        );
        pt2 = (JSONObject) new JSONParser().parse(
            "{\"data\":[{\"date\":\"2020-04-15\",\"confirmed\":18091,\"deaths\":599,\"recovered\":383,\"confirmed_diff\":643,\"deaths_diff\":32,\"recovered_diff\":36,\"last_update\":\"2020-04-15 22:56:32\",\"active\":17109,\"active_diff\":575,\"fatality_rate\":0.0331,\"region\":{\"iso\":\"PRT\",\"name\":\"Portugal\",\"province\":\"\",\"lat\":\"39.3999\",\"long\":\"-8.2245\",\"cities\":[]}}]}"
        );
        pt3 = (JSONObject) new JSONParser().parse(
            "{\"data\":[{\"date\":\"2020-04-14\",\"confirmed\":17448,\"deaths\":567,\"recovered\":347,\"confirmed_diff\":514,\"deaths_diff\":32,\"recovered_diff\":70,\"last_update\":\"2020-04-14 23:33:12\",\"active\":16534,\"active_diff\":412,\"fatality_rate\":0.0325,\"region\":{\"iso\":\"PRT\",\"name\":\"Portugal\",\"province\":\"\",\"lat\":\"39.3999\",\"long\":\"-8.2245\",\"cities\":[]}}]}"
        );

        glo1 = (JSONObject) new JSONParser().parse(
            "{\"data\":{\"date\":\"2020-04-16\",\"last_update\":\"2020-04-16 23:38:19\",\"confirmed\":2152647,\"confirmed_diff\":96592,\"deaths\":143801,\"deaths_diff\":9624,\"recovered\":542107,\"recovered_diff\":31088,\"active\":1466739,\"active_diff\":55880,\"fatality_rate\":0.0668}}"
        );
        glo2 = (JSONObject) new JSONParser().parse(
            "{\"data\":{\"date\":\"2020-04-15\",\"last_update\":\"2020-04-15 23:04:26\",\"confirmed\":2056055,\"confirmed_diff\":79863,\"deaths\":134177,\"deaths_diff\":8193,\"recovered\":511019,\"recovered_diff\":36758,\"active\":1410859,\"active_diff\":34912,\"fatality_rate\":0.0653}}"
        );
        glo3 = (JSONObject) new JSONParser().parse(
            "{\"data\":{\"date\":\"2020-04-14\",\"last_update\":\"2020-04-14 23:41:11\",\"confirmed\":1976192,\"confirmed_diff\":58872,\"deaths\":125984,\"deaths_diff\":6502,\"recovered\":474261,\"recovered_diff\":25606,\"active\":1375947,\"active_diff\":26764,\"fatality_rate\":0.0638}}"
        );

        regions = (JSONObject) new JSONParser().parse(
            "{\"data\":[{\"iso\":\"CHN\",\"name\":\"China\"},{\"iso\":\"TWN\",\"name\":\"Taipei and environs\"},{\"iso\":\"USA\",\"name\":\"US\"},{\"iso\":\"JPN\",\"name\":\"Japan\"}]}"
        );

        pt1Data = new IncidenceData(pt1);
        pt2Data = new IncidenceData(pt2);
        pt3Data = new IncidenceData(pt3);
        glo1Data = new IncidenceData(glo1);
        glo2Data = new IncidenceData(glo2);
        glo3Data = new IncidenceData(glo3);

        when(client.makeApiCall(Mockito.contains("date=2020-04-16&region_name=Portugal"))).thenReturn(Optional.of(pt1));
        when(client.makeApiCall(Mockito.contains("date=2020-04-16&iso=PRT"))).thenReturn(Optional.of(pt1));
        when(client.makeApiCall(Mockito.contains("date=2020-04-15&iso=PRT"))).thenReturn(Optional.of(pt2));
        when(client.makeApiCall(Mockito.contains("date=2020-04-14&iso=PRT"))).thenReturn(Optional.of(pt3));

        when(client.makeApiCall(Mockito.endsWith("date=2020-04-16"))).thenReturn(Optional.of(glo1));
        when(client.makeApiCall(Mockito.endsWith("date=2020-04-15"))).thenReturn(Optional.of(glo2));
        when(client.makeApiCall(Mockito.endsWith("date=2020-04-14"))).thenReturn(Optional.of(glo3));

        when(client.makeApiCall(Mockito.endsWith("regions"))).thenReturn(Optional.of(regions));
    }


    @Test
    void getGlobalDataForDayTest() throws java.text.ParseException {
        LocalDate day = LocalDate.parse("2020-04-15");
        Optional<IncidenceData> result = apiRequestService.getGlobalDataForDay(day);
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(glo2Data);
    }

    @Test
    void getCountryDataForDayByIsoTest() throws java.text.ParseException {
        LocalDate day = LocalDate.parse("2020-04-15");
        Optional<IncidenceData> result = apiRequestService.getCountryDataForDayByIso("PRT", day);
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(pt2Data);
    }

    @Test
    void getGlobalDataForPeriodTest() throws java.text.ParseException {
        LocalDate start = LocalDate.parse("2020-04-14");
        LocalDate end = LocalDate.parse("2020-04-16");
        List<IncidenceData> result = apiRequestService.getGlobalDataForPeriod(start, end);

        assertThat(result).containsExactlyInAnyOrder(glo1Data, glo2Data, glo3Data);
    }

    @Test
    void getCountryDataForPeriodByIsoTest() throws java.text.ParseException {
        LocalDate start = LocalDate.parse("2020-04-14");
        LocalDate end = LocalDate.parse("2020-04-16");
        List<IncidenceData> result = apiRequestService.getCountryDataForPeriodByIso("PRT", start, end);

        assertThat(result).containsExactlyInAnyOrder(pt1Data, pt2Data, pt3Data);
    }

    @Test
    void getRegionsTest() {
        List<String> result = apiRequestService.getRegions();
        assertThat(result).containsExactlyInAnyOrder("China", "Taipei and environs", "US", "Japan");
    }


}
