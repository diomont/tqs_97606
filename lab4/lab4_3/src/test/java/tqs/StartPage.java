package tqs;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class StartPage {
    
    @SuppressWarnings("unused")
    private WebDriver driver;

    @FindBy(name = "fromPort")
    private WebElement departureSelect;

    @FindBy(name = "toPort")
    private WebElement destinationSelect;

    @FindBy(xpath = "//input[@value=\'Find Flights\']")
    private WebElement findFlightsButton;


    public StartPage(WebDriver driver) {
        this.driver = driver;
        driver.get("https://blazedemo.com/");
        PageFactory.initElements(driver, this);
    }


    public void selectDeparture(String city) {
        departureSelect.click();
        Select select = new Select(departureSelect);
        select.selectByValue(city);
    }

    public void selectDestination(String city) {
        destinationSelect.click();
        Select select = new Select(destinationSelect);
        select.selectByValue(city);
    }

    public void clickSearch() {
        findFlightsButton.click();
    }
}
