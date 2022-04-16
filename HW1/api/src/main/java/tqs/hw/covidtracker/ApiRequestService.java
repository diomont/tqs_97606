package tqs.hw.covidtracker;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApiRequestService {
    
    @Autowired
    private JsonHttpClient client;

    public ApiRequestService(JsonHttpClient client) {
        this.client = client;
    }


    public IncidenceData getGlobalDataForDay(Date day) {
        return null;
    }

    public IncidenceData getCountryDataForDayByIso(String countryIso, Date day) {
        return null;
    }

    public List<IncidenceData> getGlobalDataForPeriod(Date start, Date end) {
        return null;
    }

    public List<IncidenceData> getCountryDataForPeriodByIso(String country, Date start, Date end) {
        return null;
    }

    public List<String> getRegions() {
        return null;
    }


    
}
