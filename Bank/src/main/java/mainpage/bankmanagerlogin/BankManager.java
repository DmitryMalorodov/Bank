package mainpage.bankmanagerlogin;

import enums.Currency;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import mainpage.Header;

import java.util.List;

public class BankManager extends Header {
    private WebDriver driver;

    public BankManager(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    private By addCustomerButton = By.xpath("//button[@ng-class='btnClass1']");
    private By openAccountButton = By.xpath("//button[@ng-class='btnClass2']");
    private By customersButton = By.xpath("//button[@ng-class='btnClass3']");

    public void pressAddCustomerButton() {
        driver.findElement(addCustomerButton).click();
    }

    public void pressOpenAccountButton() {
        driver.findElement(openAccountButton).click();
    }

    public void pressCustomersButton() {
        driver.findElement(customersButton).click();
    }

    public class AddCustomer {
        private By firstNameField = By.xpath("//input[@placeholder='First Name']");
        private By lastNameField = By.xpath("//input[@placeholder='Last Name']");
        private By postCodeField = By.xpath("//input[@placeholder='Post Code']");
        private By addCustomerButton = By.xpath("//button[@type='submit']");

        public void typeInFirstNameField(String firstName) {
            driver.findElement(firstNameField).sendKeys(firstName);
        }

        public void typeInLastNameField(String lastName) {
            driver.findElement(lastNameField).sendKeys(lastName);
        }

        public void typeInPostCodeField(String postCode) {
            driver.findElement(postCodeField).sendKeys(postCode);
        }

        public void pressAddCustomerButton() {
            driver.findElement(addCustomerButton).click();
        }

        public void addCustomer(String firstName, String lastName, String postCode) {
            typeInFirstNameField(firstName);
            typeInLastNameField(lastName);
            typeInPostCodeField(postCode);
            pressAddCustomerButton();
            driver.switchTo().alert().accept();
        }
    }

    public class OpenAccount {
        private By customerNameDropDown = By.id("userSelect");
        private By currencyDropDown = By.id("currency");
        private By processButton = By.xpath("//button[@type='submit']");

        public void selectCustomerName(String name) {
            Select select = new Select(driver.findElement(customerNameDropDown));
            select.selectByVisibleText(name);
        }

        public void selectCurrency(Currency currency) {
            Select select = new Select(driver.findElement(currencyDropDown));
            select.selectByVisibleText(currency.getCurrency());
        }

        public void pressProcessButton() {
            driver.findElement(processButton).click();
        }

        public void openAccount(String name, Currency currency) {
            selectCustomerName(name);
            selectCurrency(currency);
            pressProcessButton();
            driver.switchTo().alert().accept();
        }
    }

    public class Customers {
        private By searchField = By.xpath("//input[@type='text']");
        private By accountNumber = By.xpath("//tr[@class='ng-scope']//span");
        private By deleteButtons = By.xpath("//button[@ng-click='deleteCust(cust)']");

        public void typeInSearchField(String searchRequest) {
            WebElement element = driver.findElement(searchField);
            element.clear();
            element.sendKeys(searchRequest);
        }

        public int getCountAccountsNumber() {
            try {
                return driver.findElements(accountNumber).size();
            } catch (NoSuchElementException e) {
                return 0;
            }
        }

        public int getCountCustomers() {
            try {
                return driver.findElements(deleteButtons).size();
            } catch (NoSuchElementException e) {
                return 0;
            }
        }

        public void deleteAllCustomers() {
            List<WebElement> elements = driver.findElements(deleteButtons);
            for (WebElement e: elements) {
                e.click();
            }
        }
    }
}
