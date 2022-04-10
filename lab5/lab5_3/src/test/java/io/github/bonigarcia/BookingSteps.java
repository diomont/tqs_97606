/*
 * (C) Copyright 2020 Boni Garcia (https://bonigarcia.github.io/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package io.github.bonigarcia;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import io.cucumber.java.After;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.assertj.core.api.Assertions.assertThat;

public class BookingSteps {

    private WebDriver driver;

    @When("I navigate to {string}")
    public void iNavigateTo(String url) {
        driver = new FirefoxDriver();
        driver.get(url);
    }

    @When("I select {string} as my departure city")
    public void iSelectDepartureCity(String city) {
        WebElement el = driver.findElement(By.name("fromPort"));
        el.click();
        Select select = new Select(el);
        select.selectByValue(city);
    }

    @When("I select {string} as my destination city")
    public void iSelectDestinationCity(String city) {
        WebElement el = driver.findElement(By.name("toPort"));
        el.click();
        Select select = new Select(el);
        select.selectByValue(city);
    }

    @When("I click Search")
    public void iPressEnter() {
        driver.findElement(By.xpath("//input[@value=\'Find Flights\']")).click();
    }

    @Then("the results page should show {string} in the departures column header")
    public void resultsShouldShowDeparture(String result) {
        String actual = driver.findElement(By.cssSelector("th:nth-child(4)")).getText();
        assertThat(actual).isEqualTo(result);
    }

    @Then("the results page should show {string} in the arrivals column header")
    public void resultsShouldShowArrival(String result) {
        String actual = driver.findElement(By.cssSelector("th:nth-child(5)")).getText();
        assertThat(actual).isEqualTo(result);
    }


    @After
    public void cleanup(){
        driver.quit();
    }


}
