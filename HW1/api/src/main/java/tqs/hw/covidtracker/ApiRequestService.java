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


    public IncidenceData get_global_data_for_day(Date day) {
        return null;
    }

    public IncidenceData get_country_data_for_day(String country, Date day) {
        return null;
    }

    public List<IncidenceData> get_global_data_for_period(Date start, Date end) {
        return null;
    }

    public List<IncidenceData> get_country_data_for_period(String country, Date start, Date end) {
        return null;
    }

    public List<String> get_countries() {
        return null;
    }


    
}
