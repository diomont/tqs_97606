package tqs.hw.covidtracker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    public IncidenceData(JSONObject data) throws ParseException {
        this.day = new SimpleDateFormat().parse((String) data.get("date"));
        this.totalCases = (int) data.get("confirmed");
        this.newCases = (int) data.get("confirmed_diff");
        this.activeCases = (int) data.get("active");
        this.newActiveCases = (int) data.get("active_diff");
        this.deathTotal = (int) data.get("deaths");
        this.newDeaths = (int) data.get("deaths_diff");
        this.recovered = (int) data.get("recovered");
        this.newRecovered = (int) data.get("recovered_diff");
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
