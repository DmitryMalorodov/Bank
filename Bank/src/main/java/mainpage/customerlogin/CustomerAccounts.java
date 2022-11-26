package mainpage.customerlogin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import mainpage.Header;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class CustomerAccounts extends Header {
    private WebDriver driver;

    public CustomerAccounts(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    private By logoutButton = By.xpath("//button[@ng-show='logout']");
    private By infoMessage = By.xpath("//span[@ng-show='noAccount']");
    private By selectAccountDropDown = By.id("accountSelect");
    private By currentAccountNumber = By.xpath("//div[@ng-hide='noAccount']/strong[1]");
    private By currentCurrency = By.xpath("//div[@ng-hide='noAccount']/strong[3]");

    private By transactionsButton = By.xpath("//button[@ng-class='btnClass1']");
    private By depositButton = By.xpath("//button[@ng-class='btnClass2']");
    private By withdrawButton = By.xpath("//button[@ng-class='btnClass3']");

    public void pressLogoutButton() {
        driver.findElement(logoutButton).click();
    }

    public String getInfoMessage() {
        return driver.findElement(infoMessage).getText().trim();
    }

    //метод выбирает счет из выпадающего списка но его индексу в этом списке. Отсчет идет от 0.
    public String selectAccount(int index) {
        Select select = new Select(driver.findElement(selectAccountDropDown));
        select.selectByIndex(index);
        List<WebElement> element = select.getAllSelectedOptions();
        return element.get(0).getText().trim();
    }

    public int getCountAccounts() {
        Select select = new Select(driver.findElement(selectAccountDropDown));
        return select.getOptions().size();
    }

    public String getCurrentAccountNumber() {
        return driver.findElement(currentAccountNumber).getText().trim();
    }

    public String getCurrentCurrency() {
        return driver.findElement(currentCurrency).getText().trim();
    }

    public void pressTransactionsButton() {
        driver.findElement(transactionsButton).click();
    }

    public void pressDepositButton() {
        driver.findElement(depositButton).click();
    }

    public void pressWithdrawButton() {
        driver.findElement(withdrawButton).click();
    }

    public class Transaction {
        private By backButton = By.xpath("//button[@ng-click='back()']");
        private By resetButton = By.xpath("//button[@ng-click='reset()']");
        private By dateTimeSortButton = By.xpath("//a[@href='#']");

        public void pressBackButton() {
            driver.findElement(backButton).click();
        }

        public void pressResetButton() {
            driver.findElement(resetButton).click();
        }

        public void pressDateTimeSortButton() {
            driver.findElement(dateTimeSortButton).click();
        }
    }

    public class Deposit {
        private By amountField = By.xpath("//input[@type='number']");
        private By depositButton = By.xpath("//button[@type='submit']");

        public void typeInAmountField(String amount) {
            driver.findElement(amountField).sendKeys(amount);
        }

        public void pressDepositButton() {
            driver.findElement(depositButton).click();
        }
    }

    public class Withdraw {
        private By amountField = By.xpath("//input[@type='number']");
        private By withdrawButton = By.xpath("//button[@type='submit']");

        public void typeInAmountField(String amount) {
            driver.findElement(amountField).sendKeys(amount);
        }

        public void pressWithdrawButton() {
            driver.findElement(withdrawButton).click();
        }
    }
}
