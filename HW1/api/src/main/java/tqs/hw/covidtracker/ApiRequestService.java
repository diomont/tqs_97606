package tqs.hw.covidtracker;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class ApiRequestService {

    private final String BASE_URI = "https://covid-19-statistics.p.rapidapi.com/";

    private JsonHttpClient client;

    public ApiRequestService(JsonHttpClient client) {
        this.client = client;
    }


    public Optional<IncidenceData> getLatestGlobalData() {
        String uri = BASE_URI + "reports/total";
        Optional<JSONObject> response = client.makeApiCall(uri);
        return makeIncidenceData(response);
    }

    public Optional<IncidenceData> getLatestCountryDataByIso(String countryIso) {
        String uri = BASE_URI + "reports?iso=" + countryIso;
        Optional<JSONObject> response = client.makeApiCall(uri);
        return makeIncidenceData(response);
    }

    public Optional<IncidenceData> getGlobalDataForDay(LocalDate day) {
        String uri = BASE_URI + "reports/total?date=" + day.toString();
        Optional<JSONObject> response = client.makeApiCall(uri);
        return makeIncidenceData(response);
    }

    public Optional<IncidenceData> getCountryDataForDayByIso(String countryIso, LocalDate day) {
        String uri = BASE_URI + "reports?date=" + day.toString() + "&iso=" + countryIso;
        Optional<JSONObject> response = client.makeApiCall(uri);
        return makeIncidenceData(response);
    }

    public List<IncidenceData> getGlobalDataForPeriod(LocalDate start, LocalDate end) {
        List<IncidenceData> ret = new ArrayList<>();

        for (;start.compareTo(end) <= 0; start = start.plusDays(1)) {
            Optional<IncidenceData> res = getGlobalDataForDay(start);
            if (res.isPresent())
                ret.add(res.get());
        }
        return ret;
    }

    public List<IncidenceData> getCountryDataForPeriodByIso(String countryIso, LocalDate start, LocalDate end) {
        List<IncidenceData> ret = new ArrayList<>();

        for (;start.compareTo(end) <= 0; start = start.plusDays(1)) {
            Optional<IncidenceData> res = getCountryDataForDayByIso(countryIso, start);
            if (res.isPresent())
                ret.add(res.get());
        }
        return ret;
    }

    public List<String> getRegions() {
        Optional<JSONObject> response = client.makeApiCall(BASE_URI + "regions");

        if (response.isEmpty())
            return new ArrayList<>();
        else {
            System.out.println(
                response.get().get("data")
            );
            return null;
        }
    }


    private Optional<IncidenceData> makeIncidenceData(Optional<JSONObject> data) {
        if (data.isEmpty()) {
            return Optional.empty();
        }
        else {
            try {
                return Optional.of(new IncidenceData(data.get()));
            } catch (ParseException e) {
                return Optional.empty();
            }
        }
    }
    
}
