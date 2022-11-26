package mainpage.customerlogin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import mainpage.Header;

public class ChooseCustomer extends Header {
    private WebDriver driver;

    public ChooseCustomer(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    private By selectCustomerDropDown = By.id("userSelect");
    private By loginButton = By.xpath("//button[@type='submit']");

    public void selectCustomer(String name) {
        Select select = new Select(driver.findElement(selectCustomerDropDown));
        select.selectByVisibleText(name);
    }

    public void pressLoginButton() {
        driver.findElement(loginButton).click();
    }

    public boolean isLoginButtonExist() {
        return driver.findElement(loginButton).isDisplayed();
    }
}
