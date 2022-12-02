package mainpage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MainPage extends Header {
    private WebDriver driver;

    public MainPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    private By customerLoginButton = By.xpath("//button[@ng-click='customer()']");
    private By bankManagerLoginButton = By.xpath("//button[@ng-click='manager()']");

    public void pressCustomerLoginButton() {
        driver.findElement(customerLoginButton).click();
    }

    public void pressBankManagerLoginButton() {
        driver.findElement(bankManagerLoginButton).click();
    }

    public static void waiting(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
