package tqs.hw.covidtracker;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class ApiRequestService {

    private static final String BASE_URI = "https://covid-19-statistics.p.rapidapi.com/";

    private JsonHttpClient client;
    private Cache<Optional<JSONObject>> cache;

    public ApiRequestService(JsonHttpClient client) {
        this.client = client;
        this.cache = new Cache<>(20000);
    }

    public Optional<IncidenceData> getLatestGlobalData() {
        String uri = BASE_URI + "reports/total";
        Optional<JSONObject> response = getFromCacheOrFetch(uri);
        return makeIncidenceData(response);
    }

    public Optional<IncidenceData> getLatestCountryDataByIso(String countryIso) {
        String uri = BASE_URI + "reports?iso=" + countryIso;
        Optional<JSONObject> response = getFromCacheOrFetch(uri);
        return makeIncidenceData(response);
    }

    public Optional<IncidenceData> getGlobalDataForDay(LocalDate day) {
        String uri = BASE_URI + "reports/total?date=" + day.toString();
        Optional<JSONObject> response = getFromCacheOrFetch(uri);
        return makeIncidenceData(response);
    }

    public Optional<IncidenceData> getCountryDataForDayByIso(String countryIso, LocalDate day) {
        String uri = BASE_URI + "reports?date=" + day.toString() + "&iso=" + countryIso;
        Optional<JSONObject> response = getFromCacheOrFetch(uri);
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

    /**
     * Get list of available regions names and iso codes
     * @return A list of String arrays of size 2, where index 0 is the iso code, and index 1 is the region name
     */
    public List<String[]> getRegions() {
        String uri = BASE_URI + "regions";
        Optional<JSONObject> response = getFromCacheOrFetch(uri);

        if (response.isEmpty())
            return new ArrayList<>();
        else {
            @SuppressWarnings("unchecked")
            List<Map<String, String>> data = (List<Map<String, String>>) response.get().get("data");
            ArrayList<String[]> regions = new ArrayList<>();
            data.forEach((Map<String, String> region) -> regions.add(new String[] {region.get("iso"), region.get("name")}));
            return regions;
        }
    }

    public Map<String, Long> getCacheStats() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("requests", cache.getRequests());
        stats.put("hits", cache.getHits());
        stats.put("misses", cache.getMisses());
        stats.put("time-to-live", (long) cache.getTimeToLive());
        return stats;
    }


    private Optional<IncidenceData> makeIncidenceData(Optional<JSONObject> data) {
        if (data.isEmpty()) {
            return Optional.empty();
        }
        else {
            return Optional.of(new IncidenceData(data.get()));
        }
    }
    
    private Optional<JSONObject> getFromCacheOrFetch(String uri) {
        Optional<JSONObject> response;
        Optional<Optional<JSONObject>> cached = cache.get(uri);
        if (cached.isPresent())
            response = cached.get();
        else {
            response = client.makeApiCall(uri);
            cache.add(uri, response);
        }
        return response;
    }
    
}
