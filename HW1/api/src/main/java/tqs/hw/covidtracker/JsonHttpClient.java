package tqs.hw.covidtracker;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

@Service
public class JsonHttpClient {
    
    private static final int TIMEOUT = 5;  // timeout for external API requests in seconds

    public JsonHttpClient() {}

    public Optional<JSONObject> makeApiCall(String requestUri) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(requestUri))
            .header("X-RapidAPI-Host", "covid-19-statistics.p.rapidapi.com")
            .header("X-RapidAPI-Key", "2e80b150efmsha5f9e8009deca47p14cf71jsn2f93e7f1494f")
            .method("GET", HttpRequest.BodyPublishers.noBody())
            .timeout(Duration.ofSeconds(TIMEOUT))
            .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
            JSONObject data = (JSONObject) new JSONParser().parse(response.body());
            return Optional.of(data);
        }
        catch (Exception e) {
           return Optional.empty();
        }
    }
}
