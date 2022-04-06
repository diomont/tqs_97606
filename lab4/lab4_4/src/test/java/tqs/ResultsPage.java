package tqs;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ResultsPage {
    
    @SuppressWarnings("unused")
    private WebDriver driver;

    @FindBy(css = "th:nth-child(4)")
    private WebElement departureColumnHeader;

    @FindBy(css = "th:nth-child(5)")
    private WebElement destinationColumnHeader;

    @FindBy(css = "tr:nth-child(5) .btn")
    private WebElement flightPickerButton;


    public ResultsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void chooseFlight() {
        flightPickerButton.click();
    }

    public String getDepartureText() {
        return departureColumnHeader.getText();
    }

    public String getDestinationText() {
        return destinationColumnHeader.getText();
    }
}
