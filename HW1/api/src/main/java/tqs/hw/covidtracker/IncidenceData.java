package tqs.hw.covidtracker;

import java.util.Date;

import org.json.simple.JSONObject;

public class IncidenceData {
    
    private String country;
    private Date day;
    private int totalCases;
    private int newCases;
    private int activeCases;
    private int newActiveCases;
    private int deathTotal;
    private int newDeaths;
    private int recovered;
    private int newRecovered;

    public IncidenceData(JSONObject data) {

    }

}
