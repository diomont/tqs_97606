package tqs.hw.covidtracker;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class ApiRequestService {
    
    private static final int TIMEOUT = 5;  // timeout for external API requests in seconds

    public void makeApiCall(String requestUrl) {

        // Global data for given date
        // https://covid-19-statistics.p.rapidapi.com/reports/total?date=2020-04-07

        // Data for given date and country ISO or name
        // https://covid-19-statistics.p.rapidapi.com/reports?date=2020-04-16&region_name=US&iso=USA

        try {
            HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(requestUrl))
            .header("X-RapidAPI-Host", "covid-19-statistics.p.rapidapi.com")
            .header("X-RapidAPI-Key", "2e80b150efmsha5f9e8009deca47p14cf71jsn2f93e7f1494f")
            .method("GET", HttpRequest.BodyPublishers.noBody())
            .timeout(Duration.ofSeconds(TIMEOUT))
            .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
        }
        catch (IOException e) {
            //TODO: handle exception
        }
        catch (InterruptedException e) {
            //TODO: handle exception
        }
    }
}
