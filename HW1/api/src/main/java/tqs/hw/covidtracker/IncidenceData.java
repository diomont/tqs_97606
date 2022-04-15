package tqs.hw.covidtracker;

import java.util.Date;

import org.json.simple.JSONObject;

public class IncidenceData {
    
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


    public Date getDay() {
        return day;
    }

    public int getTotalCases() {
        return totalCases;
    }

    public int getNewCases() {
        return newCases;
    }

    public int getActiveCases() {
        return activeCases;
    }

    public int getNewActiveCases() {
        return newActiveCases;
    }

    public int getDeathTotal() {
        return deathTotal;
    }

    public int getNewDeaths() {
        return newDeaths;
    }

    public int getRecovered() {
        return recovered;
    }

    public int getNewRecovered() {
        return newRecovered;
    }
}
