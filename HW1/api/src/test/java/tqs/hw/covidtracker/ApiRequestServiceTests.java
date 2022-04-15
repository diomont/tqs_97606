package tqs.hw.covidtracker;

import static org.mockito.Mockito.when;

import java.util.Optional;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ApiRequestServiceTests {
    
    @Mock(lenient = true)
    private JsonHttpClient client;

    @InjectMocks
    private ApiRequestService apiRequestService;


    @BeforeEach
    public void setup() throws ParseException {

        JSONObject pt1 = (JSONObject) new JSONParser().parse(
            "{\"data\":[{\"date\":\"2020-04-16\",\"confirmed\":18841,\"deaths\":629,\"recovered\":493,\"confirmed_diff\":750,\"deaths_diff\":30,\"recovered_diff\":110,\"last_update\":\"2020-04-16 23:30:31\",\"active\":17719,\"active_diff\":610,\"fatality_rate\":0.0334,\"region\":{\"iso\":\"PRT\",\"name\":\"Portugal\",\"province\":\"\",\"lat\":\"39.3999\",\"long\":\"-8.2245\",\"cities\":[]}}]}"
        );
        JSONObject pt2 = (JSONObject) new JSONParser().parse(
            "{\"data\":[{\"date\":\"2020-04-15\",\"confirmed\":18091,\"deaths\":599,\"recovered\":383,\"confirmed_diff\":643,\"deaths_diff\":32,\"recovered_diff\":36,\"last_update\":\"2020-04-15 22:56:32\",\"active\":17109,\"active_diff\":575,\"fatality_rate\":0.0331,\"region\":{\"iso\":\"PRT\",\"name\":\"Portugal\",\"province\":\"\",\"lat\":\"39.3999\",\"long\":\"-8.2245\",\"cities\":[]}}]}"
        );
        JSONObject pt3 = (JSONObject) new JSONParser().parse(
            "{\"data\":[{\"date\":\"2020-04-14\",\"confirmed\":17448,\"deaths\":567,\"recovered\":347,\"confirmed_diff\":514,\"deaths_diff\":32,\"recovered_diff\":70,\"last_update\":\"2020-04-14 23:33:12\",\"active\":16534,\"active_diff\":412,\"fatality_rate\":0.0325,\"region\":{\"iso\":\"PRT\",\"name\":\"Portugal\",\"province\":\"\",\"lat\":\"39.3999\",\"long\":\"-8.2245\",\"cities\":[]}}]}"
        );

        JSONObject glo1 = (JSONObject) new JSONParser().parse(
            "{\"data\":{\"date\":\"2020-04-16\",\"last_update\":\"2020-04-16 23:38:19\",\"confirmed\":2152647,\"confirmed_diff\":96592,\"deaths\":143801,\"deaths_diff\":9624,\"recovered\":542107,\"recovered_diff\":31088,\"active\":1466739,\"active_diff\":55880,\"fatality_rate\":0.0668}}"
        );
        JSONObject glo2 = (JSONObject) new JSONParser().parse(
            "{\"data\":{\"date\":\"2020-04-15\",\"last_update\":\"2020-04-15 23:04:26\",\"confirmed\":2056055,\"confirmed_diff\":79863,\"deaths\":134177,\"deaths_diff\":8193,\"recovered\":511019,\"recovered_diff\":36758,\"active\":1410859,\"active_diff\":34912,\"fatality_rate\":0.0653}}"
        );
        JSONObject glo3 = (JSONObject) new JSONParser().parse(
            "{\"data\":{\"date\":\"2020-04-14\",\"last_update\":\"2020-04-14 23:41:11\",\"confirmed\":1976192,\"confirmed_diff\":58872,\"deaths\":125984,\"deaths_diff\":6502,\"recovered\":474261,\"recovered_diff\":25606,\"active\":1375947,\"active_diff\":26764,\"fatality_rate\":0.0638}}"
        );

        when(client.makeApiCall(Mockito.contains("date=2020-04-16&region_name=Portugal"))).thenReturn(Optional.of(pt1));
        when(client.makeApiCall(Mockito.contains("date=2020-04-16&iso=PRT"))).thenReturn(Optional.of(pt1));
        when(client.makeApiCall(Mockito.contains("date=2020-04-15&iso=PRT"))).thenReturn(Optional.of(pt2));
        when(client.makeApiCall(Mockito.contains("date=2020-04-14&iso=PRT"))).thenReturn(Optional.of(pt3));

        when(client.makeApiCall(Mockito.endsWith("date=2020-04-16"))).thenReturn(Optional.of(glo1));
        when(client.makeApiCall(Mockito.endsWith("date=2020-04-15"))).thenReturn(Optional.of(glo2));
        when(client.makeApiCall(Mockito.endsWith("date=2020-04-14"))).thenReturn(Optional.of(glo3));

    }



}
