package mainpage.customerlogin;

import mainpage.MainPage;
import org.openqa.selenium.*;
import mainpage.Header;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.Select;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
    private By currentBalance = By.xpath("//div[@ng-hide='noAccount']/strong[2]");
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

    //метод выбирает счет из выпадающего списка по его индексу в этом списке. Отсчет идет от 0.
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

    public int getCurrentBalance() {
        return Integer.parseInt(driver.findElement(currentBalance).getText().trim());
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
        private By transactionList = By.xpath("//tr[@class='ng-scope']");
        private By dateTimeList = By.xpath("//tr[@class='ng-scope']/td[1]");

        public void pressBackButton() {
            driver.findElement(backButton).click();
        }

        public void pressResetButton() {
            driver.findElement(resetButton).click();
        }

        public void pressDateTimeSortButton() {
            driver.findElement(dateTimeSortButton).click();
        }

        public int getCountTransactions() {
            try {
                return driver.findElements(transactionList).size();

            }catch (NoSuchElementException e) {
                return 0;
            }
        }

        //метод получает все даты в строковом виде и парсит их в формат Date и возвращает их список
        private List<Date> getDateTimeList() {
            List<Date> result = new ArrayList<>();
            List<WebElement> allElements = driver.findElements(dateTimeList);
            SimpleDateFormat formatter = new SimpleDateFormat("MMM d, yyyy h:mm:ss a", Locale.ENGLISH);

            for (int i = 0; i < allElements.size(); i++) {
                String dateTime = allElements.get(i).getText().trim();
                try {
                    Date date = formatter.parse(dateTime);
                    result.add(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            return result;
        }

        public boolean isSortAscending() {
            boolean result = true;
            List<Date> allDateTime = getDateTimeList();
            for (int i = 0; i < allDateTime.size() - 1; i++) {
                if (allDateTime.get(i).after(allDateTime.get(i + 1)))
                    result = false;
            }
            return result;
        }

        public boolean isSortDescending() {
            boolean result = true;
            List<Date> allDateTime = getDateTimeList();
            for (int i = 0; i < allDateTime.size() - 1; i++) {
                if (allDateTime.get(i).before(allDateTime.get(i + 1)))
                    result = false;
            }
            return result;
        }
    }

    public class Deposit {
        private By amountField = By.xpath("//input[@type='number']");
        private By depositButton = By.xpath("//button[@type='submit']");
        private By infoMessage = By.xpath("//span[@class='error ng-binding']");

        public void typeInAmountField(String amount) {
            WebElement element = driver.findElement(amountField);
            element.clear();
            element.sendKeys(amount);
            pressDepositButton();
        }

        public void pressOnAmountField() {
            driver.findElement(amountField).click();
        }

        public void pressArrowUp() {
            driver.findElement(amountField).sendKeys(Keys.ARROW_UP);
        }

        public void pressArrowDown() {
            driver.findElement(amountField).sendKeys(Keys.ARROW_DOWN);
        }

        public String getValueFromAmountField() {
            return driver.findElement(amountField).getAttribute("value");
        }

        private void pressDepositButton() {
            driver.findElement(depositButton).click();
        }

        public String getInfoMessage() {
            return driver.findElement(infoMessage).getText().trim();
        }
    }

    public class Withdraw {
        private By amountField = By.xpath("//input[@type='number']");
        private By withdrawButton = By.xpath("//button[@type='submit']");
        private By infoMessage = By.xpath("//span[@class='error ng-binding']");

        public void typeInAmountField(String amount) {
            WebElement element = driver.findElement(amountField);
            element.clear();
            element.sendKeys(amount);
            pressWithdrawButton();
        }

        public void pressOnAmountField() {
            driver.findElement(amountField).click();
        }

        public void pressArrowUp() {
            driver.findElement(amountField).sendKeys(Keys.ARROW_UP);
        }

        public void pressArrowDown() {
            driver.findElement(amountField).sendKeys(Keys.ARROW_DOWN);
        }

        public String getValueFromAmountField() {
            return driver.findElement(amountField).getAttribute("value");
        }

        private void pressWithdrawButton() {
            driver.findElement(withdrawButton).click();
        }

        public String getInfoMessage() {
            return driver.findElement(infoMessage).getText().trim();
        }
    }
}
