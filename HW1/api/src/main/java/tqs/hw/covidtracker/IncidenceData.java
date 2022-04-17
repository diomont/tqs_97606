package tqs.hw.covidtracker;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
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

    public IncidenceData() {};

    @SuppressWarnings("unchecked")
    public IncidenceData(JSONObject json) throws ParseException {
        Map<String, Object> data;
        try {
            data = (Map<String, Object>) (((List<Map<String, Object>>) json.get("data")).get(0));
        }
        catch (Exception e) {
            data = (Map<String, Object>) json.get("data");
        }

        this.day = LocalDate.parse((String) data.get("date"));
        this.totalCases = ((Long) data.get("confirmed")).intValue();
        this.newCases = ((Long) data.get("confirmed_diff")).intValue();
        this.activeCases = ((Long) data.get("active")).intValue();
        this.newActiveCases = ((Long) data.get("active_diff")).intValue();
        this.deathTotal = ((Long) data.get("deaths")).intValue();
        this.newDeaths = ((Long) data.get("deaths_diff")).intValue();
        this.recovered = ((Long) data.get("recovered")).intValue();
        this.newRecovered = ((Long) data.get("recovered_diff")).intValue();
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + activeCases;
        result = prime * result + ((day == null) ? 0 : day.hashCode());
        result = prime * result + deathTotal;
        result = prime * result + newActiveCases;
        result = prime * result + newCases;
        result = prime * result + newDeaths;
        result = prime * result + newRecovered;
        result = prime * result + recovered;
        result = prime * result + totalCases;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        IncidenceData other = (IncidenceData) obj;
        if (activeCases != other.activeCases)
            return false;
        if (day == null) {
            if (other.day != null)
                return false;
        } else if (!day.equals(other.day))
            return false;
        if (deathTotal != other.deathTotal)
            return false;
        if (newActiveCases != other.newActiveCases)
            return false;
        if (newCases != other.newCases)
            return false;
        if (newDeaths != other.newDeaths)
            return false;
        if (newRecovered != other.newRecovered)
            return false;
        if (recovered != other.recovered)
            return false;
        if (totalCases != other.totalCases)
            return false;
        return true;
    }
    
}
