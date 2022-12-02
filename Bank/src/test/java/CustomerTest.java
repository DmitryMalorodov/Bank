import enums.Currency;
import mainpage.MainPage;
import mainpage.bankmanagerlogin.BankManager;
import mainpage.customerlogin.ChooseCustomer;
import mainpage.customerlogin.CustomerAccounts;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import resources.Resources;

import java.time.Duration;

public class CustomerTest {
    private static WebDriver driver;
    private static MainPage mainPage;
    private static BankManager bankManager;
    private static BankManager.AddCustomer addCustomer;
    private static BankManager.OpenAccount openAccount;
    private static BankManager.Customers customers;
    private static ChooseCustomer chooseCustomer;
    private static CustomerAccounts customerAccounts;
    private static CustomerAccounts.Transaction transaction;
    private static CustomerAccounts.Deposit deposit;
    private static CustomerAccounts.Withdraw withdraw;

    @BeforeAll
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", Resources.chromeDriverPath);

        driver = new ChromeDriver();
        mainPage = new MainPage(driver);

        bankManager = new BankManager(driver);
        addCustomer = bankManager.new AddCustomer();
        openAccount = bankManager.new OpenAccount();
        customers = bankManager.new Customers();

        chooseCustomer = new ChooseCustomer(driver);
        customerAccounts = new CustomerAccounts(driver);
        transaction = customerAccounts.new Transaction();
        deposit = customerAccounts.new Deposit();
        withdraw = customerAccounts.new Withdraw();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();

        //предусловия тест-сьюта
        driver.get(Resources.mainPageUrl);
        mainPage.pressBankManagerLoginButton();
        bankManager.pressCustomersButton();
        if (customers.getCountCustomers() > 0) customers.deleteAllCustomers();

        bankManager.pressAddCustomerButton();
        addCustomer.addCustomer("Jack", "Reacher", "222555");

        bankManager.pressOpenAccountButton();
        openAccount.openAccount("Jack Reacher", Currency.DOLLAR);
        openAccount.openAccount("Jack Reacher", Currency.POUND);

        bankManager.pressHomeButton();
        mainPage.pressCustomerLoginButton();
        chooseCustomer.selectCustomer("Jack Reacher");
        chooseCustomer.pressLoginButton();
    }

    @Test
    public void case1() {
        customerAccounts.pressDepositButton();
        int balanceBefore = customerAccounts.getCurrentBalance();
        deposit.typeInAmountField("100");
        Assertions.assertEquals("Deposit Successful", deposit.getInfoMessage());
        Assertions.assertEquals(balanceBefore + 100, customerAccounts.getCurrentBalance());

        customerAccounts.pressWithdrawButton();
        balanceBefore = customerAccounts.getCurrentBalance();
        MainPage.waiting(500);
        withdraw.typeInAmountField("50");
        Assertions.assertEquals("Transaction successful", withdraw.getInfoMessage());
        Assertions.assertEquals(balanceBefore - 50, customerAccounts.getCurrentBalance());
    }

    @Test
    public void case2() {
        customerAccounts.selectAccount(0);
        int firstAccBalance = customerAccounts.getCurrentBalance();
        customerAccounts.selectAccount(1);
        int secondAccBalance = customerAccounts.getCurrentBalance();

        customerAccounts.selectAccount(0);
        customerAccounts.pressDepositButton();
        deposit.typeInAmountField("100");
        Assertions.assertEquals(firstAccBalance + 100, customerAccounts.getCurrentBalance());

        customerAccounts.selectAccount(1);
        Assertions.assertEquals(secondAccBalance, customerAccounts.getCurrentBalance());
        customerAccounts.pressDepositButton();
        deposit.typeInAmountField("50");
        Assertions.assertEquals(secondAccBalance + 50, customerAccounts.getCurrentBalance());

        customerAccounts.selectAccount(0);
        Assertions.assertEquals(firstAccBalance + 100, customerAccounts.getCurrentBalance());
    }

    @Test
    public void case3() {
        int balanceBefore = customerAccounts.getCurrentBalance();

        customerAccounts.pressDepositButton();
        deposit.typeInAmountField("0");
        Assertions.assertEquals(balanceBefore, customerAccounts.getCurrentBalance());

        deposit.typeInAmountField("-1");
        Assertions.assertEquals(balanceBefore, customerAccounts.getCurrentBalance());
    }

    @Test
    public void case4() {
        customerAccounts.pressDepositButton();
        deposit.typeInAmountField("50");
        int balanceBefore = customerAccounts.getCurrentBalance();

        customerAccounts.pressWithdrawButton();
        withdraw.typeInAmountField("0");
        Assertions.assertEquals(balanceBefore, customerAccounts.getCurrentBalance());

        withdraw.typeInAmountField("-1");
        Assertions.assertEquals(balanceBefore, customerAccounts.getCurrentBalance());
    }

    @Test
    public void case5() {
        int balanceBefore = customerAccounts.getCurrentBalance();

        customerAccounts.pressDepositButton();
        deposit.typeInAmountField("Hello!!!");
        Assertions.assertEquals(balanceBefore, customerAccounts.getCurrentBalance());

        customerAccounts.pressWithdrawButton();
        withdraw.typeInAmountField("Hello!!!");
        Assertions.assertEquals(balanceBefore, customerAccounts.getCurrentBalance());
    }

    @Test
    public void case6() {
        customerAccounts.pressDepositButton();
        deposit.pressOnAmountField();
        deposit.pressArrowUp();
        Assertions.assertEquals("1", deposit.getValueFromAmountField());
        deposit.pressArrowDown();
        Assertions.assertEquals("0", deposit.getValueFromAmountField());

        customerAccounts.pressWithdrawButton();
        withdraw.pressOnAmountField();
        withdraw.pressArrowUp();
        Assertions.assertEquals("1", withdraw.getValueFromAmountField());
        withdraw.pressArrowDown();
        Assertions.assertEquals("0", withdraw.getValueFromAmountField());
    }

    @Test
    public void case7() {
        //предусловие кейса
        customerAccounts.pressDepositButton();
        deposit.typeInAmountField("100");
        MainPage.waiting(1100);
        deposit.typeInAmountField("100");
        MainPage.waiting(1100);
        deposit.typeInAmountField("100");
        MainPage.waiting(1100);

        customerAccounts.pressWithdrawButton();
        withdraw.typeInAmountField("50");
        MainPage.waiting(1100);
        withdraw.typeInAmountField("50");
        MainPage.waiting(1100);
        withdraw.typeInAmountField("50");
        MainPage.waiting(2000);

        customerAccounts.pressTransactionsButton();
        Assertions.assertTrue(transaction.isSortAscending());

        transaction.pressDateTimeSortButton();
        Assertions.assertTrue(transaction.isSortDescending());
        transaction.pressBackButton();
    }

    @Test
    public void case8() {
        customerAccounts.pressLogoutButton();
        Assertions.assertEquals("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/customer", driver.getCurrentUrl());
        chooseCustomer.selectCustomer("Jack Reacher");
        chooseCustomer.pressLoginButton();
    }

    @Test
    public void case9() {
        customerAccounts.pressHomeButton();
        Assertions.assertEquals(Resources.mainPageUrl, driver.getCurrentUrl());

        mainPage.pressCustomerLoginButton();
        chooseCustomer.selectCustomer("Jack Reacher");
        chooseCustomer.pressLoginButton();
    }

    @AfterAll
    public static void exit() {
        driver.quit();
    }
}
