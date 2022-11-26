package mainpage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Header {
    private WebDriver driver;

    public Header(WebDriver driver) {
        this.driver = driver;
    }

    private By homeButton = By.xpath("//button[@ng-click='home()']");

    public void pressHomeButton() {
        driver.findElement(homeButton).click();
    }
}
