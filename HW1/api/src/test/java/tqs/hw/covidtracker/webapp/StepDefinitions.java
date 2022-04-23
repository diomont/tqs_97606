package tqs.hw.covidtracker.webapp;

import static org.assertj.core.api.Assertions.assertThat;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


public class StepDefinitions {

    private WebDriver driver;
    private boolean isPeriodTab;

    @Before
    public void setup() {
        isPeriodTab = false;
        driver = new FirefoxDriver();
    }


    @When("I navigate to {string}")
    public void iNavigateTo(String url) {
        driver.get(url);
    }

    @When("I select {string} as the region")
    public void iSelectRegion(String region) {
        driver.findElement(By.id("regionSelect")).click();
        {
            WebElement dropdown = driver.findElement(By.id("regionSelect"));
            dropdown.findElement(By.xpath("//option[. = '" + region + "']")).click();
        }
    }

    @When("I enter {string} on the date picker")
    public void iEnterDate(String date) {
        driver.findElement(By.id("datePicker")).click();
        driver.findElement(By.id("datePicker")).sendKeys(date);
    }

    @When("I click the Period tab")
    public void iClickThePeriodTab() {
        driver.findElement(By.id("period-tab")).click();
        isPeriodTab = true;
    }

    @When("I enter {string} on the start date picker")
    public void iEnterStartDate(String date) {
        driver.findElement(By.id("startDatePicker")).click();
        driver.findElement(By.id("startDatePicker")).sendKeys(date);
    }

    @When("I enter {string} on the end date picker")
    public void iEnterEndDate(String date) {
        driver.findElement(By.id("endDatePicker")).click();
        driver.findElement(By.id("endDatePicker")).sendKeys(date);
    }

    @When("I click the Lookup button")
    public void iLookup() {
        if (isPeriodTab)
            driver.findElement(By.xpath("//div[@id=\'periodContent\']/button")).click();
        else
            driver.findElement(By.xpath("//div[@id=\'dateContent\']/button")).click();
    }

    @Then("I should see {string} in the results")
    public void iShouldSee(String expect) {
        assertThat(driver.findElement(By.cssSelector(".mb-4")).getText())
        .contains(expect);
    }


    @After
    public void cleanup(){
        driver.quit();
    }

}
