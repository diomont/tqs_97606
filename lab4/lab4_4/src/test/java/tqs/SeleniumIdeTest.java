package tqs;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;

import io.github.bonigarcia.seljup.BrowserType;
import io.github.bonigarcia.seljup.DockerBrowser;
import io.github.bonigarcia.seljup.SeleniumJupiter;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SeleniumJupiter.class)
public class SeleniumIdeTest {
  

  @Test
  public void test(@DockerBrowser(type = BrowserType.CHROME) WebDriver driver) {

    StartPage startPage = new StartPage(driver);
    driver.manage().window().setSize(new Dimension(1064, 760));

    startPage.selectDeparture("San Diego");
    startPage.selectDestination("New York");
    startPage.clickSearch();

    ResultsPage resultsPage = new ResultsPage(driver);

    assertThat(resultsPage.getDepartureText()).isEqualTo("Departs: San Diego");
    assertThat(resultsPage.getDestinationText()).isEqualTo("Arrives: New York");

    resultsPage.chooseFlight();

    PurchasePage purchasePage = new PurchasePage(driver);

    assertThat(purchasePage.getFlightAirline()).isEqualTo("Airline: United");
    assertThat(purchasePage.getFlightPrice()).isEqualTo("Price: 400");

    purchasePage.setName("John Smith");
    purchasePage.setAddress("123 Main Street");
    purchasePage.setCity("Somewhere");
    purchasePage.setState("State");
    purchasePage.setZipCode("12345");
    purchasePage.chooseCardType("Visa");
    purchasePage.setCardNumber("123456789");
    purchasePage.setCardMonth("12");
    purchasePage.setCardYear("2022");
    purchasePage.setNameOnCard("John Smith");

    purchasePage.clickOnPurchaseFlight();

    assertThat(driver.getTitle()).isEqualTo("BlazeDemo Confirmation");
  }
}
