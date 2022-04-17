package tqs.hw.covidtracker;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Map;

import org.json.simple.JSONObject;

public class IncidenceData {
    
    private LocalDate day;
    private int totalCases;
    private int newCases;
    private int activeCases;
    private int newActiveCases;
    private int deathTotal;
    private int newDeaths;
    private int recovered;
    private int newRecovered;

    public IncidenceData(JSONObject json) throws ParseException {
        Map<String, Object> data = (Map<String, Object>) json.get("data");

        this.day = LocalDate.parse((String) data.get("date"));
        //this.day = (LocalDate) data.get("date");
        this.totalCases = (int) data.get("confirmed");
        this.newCases = (int) data.get("confirmed_diff");
        this.activeCases = (int) data.get("active");
        this.newActiveCases = (int) data.get("active_diff");
        this.deathTotal = (int) data.get("deaths");
        this.newDeaths = (int) data.get("deaths_diff");
        this.recovered = (int) data.get("recovered");
        this.newRecovered = (int) data.get("recovered_diff");
    }

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public int getTotalCases() {
        return totalCases;
    }

    public void setTotalCases(int totalCases) {
        this.totalCases = totalCases;
    }

    public int getNewCases() {
        return newCases;
    }

    public void setNewCases(int newCases) {
        this.newCases = newCases;
    }

    public int getActiveCases() {
        return activeCases;
    }

    public void setActiveCases(int activeCases) {
        this.activeCases = activeCases;
    }

    public int getNewActiveCases() {
        return newActiveCases;
    }

    public void setNewActiveCases(int newActiveCases) {
        this.newActiveCases = newActiveCases;
    }

    public int getDeathTotal() {
        return deathTotal;
    }

    public void setDeathTotal(int deathTotal) {
        this.deathTotal = deathTotal;
    }

    public int getNewDeaths() {
        return newDeaths;
    }

    public void setNewDeaths(int newDeaths) {
        this.newDeaths = newDeaths;
    }

    public int getRecovered() {
        return recovered;
    }

    public void setRecovered(int recovered) {
        this.recovered = recovered;
    }

    public int getNewRecovered() {
        return newRecovered;
    }

    public void setNewRecovered(int newRecovered) {
        this.newRecovered = newRecovered;
    }

    
}
