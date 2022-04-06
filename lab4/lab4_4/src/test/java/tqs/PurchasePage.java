package tqs;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class PurchasePage {
    
    @SuppressWarnings("unused")
    private WebDriver driver;

    @FindBy(css = "p:nth-child(2)")
    private WebElement flightAirline;

    @FindBy(css = "p:nth-child(4)")
    private WebElement flightPrice;

    @FindBy(id = "inputName")
    private WebElement nameInput;

    @FindBy(id = "address")
    private WebElement addressInput;

    @FindBy(id = "city")
    private WebElement cityInput;

    @FindBy(id = "state")
    private WebElement stateInput;

    @FindBy(id = "zipCode")
    private WebElement zipCodeInput;

    @FindBy(id = "cardType")
    private WebElement cardTypeInput;

    @FindBy(id = "creditCardNumber")
    private WebElement creditCardNumberInput;

    @FindBy(id = "creditCardMonth")
    private WebElement creditCardMonthInput;

    @FindBy(id = "creditCardYear")
    private WebElement creditCardYearInput;

    @FindBy(id = "nameOnCard")
    private WebElement nameOnCardInput;

    @FindBy(xpath = "//input[@value=\'Purchase Flight\']")
    private WebElement purchaseFlightButton;
    

    public PurchasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }


    public void setName(String value) {
        nameInput.clear();
        nameInput.sendKeys(value);
    }

    public void setAddress(String value) {
        addressInput.clear();
        addressInput.sendKeys(value);
    }

    public void setCity(String value) {
        cityInput.clear();
        cityInput.sendKeys(value);
    }

    public void setState(String value) {
        stateInput.clear();
        stateInput.sendKeys(value);
    }

    public void setZipCode(String value) {
        zipCodeInput.clear();
        zipCodeInput.sendKeys(value);
    }

    public void chooseCardType(String text) {
        cardTypeInput.click();
        Select select = new Select(cardTypeInput);
        select.selectByVisibleText(text);
    }

    public void setCardNumber(String value) {
        creditCardNumberInput.clear();
        creditCardNumberInput.sendKeys(value);
    }

    public void setCardMonth(String value) {
        creditCardMonthInput.clear();
        creditCardMonthInput.sendKeys(value);
    }

    public void setCardYear(String value) {
        creditCardYearInput.clear();
        creditCardYearInput.sendKeys(value);
    }

    public void setNameOnCard(String value) {
        nameOnCardInput.clear();
        nameOnCardInput.sendKeys(value);
    }

    public void clickOnPurchaseFlight() {
        purchaseFlightButton.click();
    }

    public String getFlightAirline() {
        return flightAirline.getText();
    }

    public String getFlightPrice() {
        return flightPrice.getText();
    }
}
