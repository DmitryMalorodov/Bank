import enums.Currency;
import mainpage.bankmanagerlogin.BankManager;
import mainpage.customerlogin.ChooseCustomer;
import mainpage.MainPage;
import mainpage.customerlogin.CustomerAccounts;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import resources.Resources;

import java.time.Duration;

public class BankManagerTest {
    private static WebDriver driver;
    private static MainPage mainPage;
    private static BankManager bankManager;
    private static BankManager.AddCustomer addCustomer;
    private static BankManager.OpenAccount openAccount;
    private static BankManager.Customers customers;
    private static ChooseCustomer chooseCustomer;
    private static CustomerAccounts customerAccounts;

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

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();

        driver.get(Resources.mainPageUrl);
    }

    @BeforeEach
    public void precondition() {
        mainPage.pressHomeButton();
        mainPage.pressBankManagerLoginButton();
        bankManager.pressCustomersButton();
        if (customers.getCountCustomers() > 0) customers.deleteAllCustomers();
        bankManager.pressHomeButton();
    }

    @Test
    public void case1() {
        mainPage.pressBankManagerLoginButton();
        bankManager.pressAddCustomerButton();

        addCustomer.addCustomer("Jack", "Reacher", "222555");

        bankManager.pressCustomersButton();
        Assertions.assertTrue(customers.getCountCustomers() == 1);
        bankManager.pressHomeButton();

        mainPage.pressCustomerLoginButton();
        chooseCustomer.selectCustomer("Jack Reacher");
        chooseCustomer.pressLoginButton();

        Assertions.assertEquals("Please open an account with us.", customerAccounts.getInfoMessage());
    }

    @Test
    public void case2() {
        mainPage.pressBankManagerLoginButton();
        bankManager.pressAddCustomerButton();

        addCustomer.typeInFirstNameField("Jack");
        addCustomer.typeInLastNameField("Reacher");
        addCustomer.pressAddCustomerButton();
        bankManager.pressCustomersButton();
        Assertions.assertTrue(customers.getCountCustomers() == 0);
        bankManager.pressAddCustomerButton();

        addCustomer.typeInFirstNameField("Jack");
        addCustomer.typeInPostCodeField("222555");
        addCustomer.pressAddCustomerButton();
        bankManager.pressCustomersButton();
        Assertions.assertTrue(customers.getCountCustomers() == 0);
        bankManager.pressAddCustomerButton();

        addCustomer.typeInLastNameField("Reacher");
        addCustomer.typeInPostCodeField("222555");
        addCustomer.pressAddCustomerButton();
        bankManager.pressCustomersButton();
        Assertions.assertTrue(customers.getCountCustomers() == 0);
        bankManager.pressAddCustomerButton();
    }

    @Test
    public void case3() {
        mainPage.pressBankManagerLoginButton();
        bankManager.pressAddCustomerButton();

        addCustomer.addCustomer("Jack", "Reacher", "222555");
        addCustomer.addCustomer("Jack", "Reacher", "222555");

        bankManager.pressCustomersButton();
        Assertions.assertTrue(customers.getCountCustomers() == 1);
    }

    @Test
    public void case4() {
        mainPage.pressBankManagerLoginButton();
        bankManager.pressAddCustomerButton();

        addCustomer.addCustomer("Jack", "Reacher", "222555");
        bankManager.pressCustomersButton();
        Assertions.assertTrue(customers.getCountAccountsNumber() == 0);

        bankManager.pressOpenAccountButton();
        openAccount.openAccount("Jack Reacher", Currency.DOLLAR);
        bankManager.pressCustomersButton();
        Assertions.assertTrue(customers.getCountAccountsNumber() == 1);

        bankManager.pressOpenAccountButton();
        openAccount.openAccount("Jack Reacher", Currency.DOLLAR);
        bankManager.pressCustomersButton();
        Assertions.assertTrue(customers.getCountAccountsNumber() == 2);
    }

    @Test
    public void case5() {
        mainPage.pressBankManagerLoginButton();
        bankManager.pressAddCustomerButton();

        addCustomer.addCustomer("Jack", "Reacher", "222555");

        bankManager.pressCustomersButton();
        bankManager.pressOpenAccountButton();
        openAccount.openAccount("Jack Reacher", Currency.DOLLAR);
        openAccount.openAccount("Jack Reacher", Currency.POUND);

        bankManager.pressHomeButton();
        mainPage.pressCustomerLoginButton();
        chooseCustomer.selectCustomer("Jack Reacher");
        chooseCustomer.pressLoginButton();
        Assertions.assertTrue(customerAccounts.getCountAccounts() == 2);

        String accNumb = customerAccounts.selectAccount(1);
        Assertions.assertEquals(customerAccounts.getCurrentAccountNumber(), accNumb);

        Assertions.assertEquals("Pound", customerAccounts.getCurrentCurrency());
    }

    @Test
    public void case6() {
        mainPage.pressBankManagerLoginButton();
        bankManager.pressAddCustomerButton();

        addCustomer.addCustomer("Jack", "Reacher", "222555");
        addCustomer.addCustomer("Jon", "Green", "222556");
        addCustomer.addCustomer("Jesse", "Marsh", "111255");
        addCustomer.addCustomer("Robert", "David", "666888");

        bankManager.pressCustomersButton();
        customers.typeInSearchField("Jack");
        Assertions.assertTrue(customers.getCountCustomers() == 1);

        customers.typeInSearchField("Reacher");
        Assertions.assertTrue(customers.getCountCustomers() == 1);

        customers.typeInSearchField("222555");
        Assertions.assertTrue(customers.getCountCustomers() == 1);
    }

    @Test
    public void case7() {
        mainPage.pressBankManagerLoginButton();
        bankManager.pressAddCustomerButton();

        addCustomer.addCustomer("Jack", "Reacher", "222555");
        addCustomer.addCustomer("Jon", "Green", "222556");

        bankManager.pressCustomersButton();
        customers.deleteAllCustomers();
        Assertions.assertTrue(customers.getCountCustomers() == 0);
    }

    @Test
    public void case8() {
        mainPage.pressBankManagerLoginButton();
        bankManager.pressAddCustomerButton();

        addCustomer.addCustomer("Jack", "Reacher", "222555");

        bankManager.pressHomeButton();
        mainPage.pressCustomerLoginButton();

        chooseCustomer.selectCustomer("Jack Reacher");
        Assertions.assertTrue(chooseCustomer.isLoginButtonExist());
        chooseCustomer.selectCustomer("---Your Name---");
        Assertions.assertFalse(chooseCustomer.isLoginButtonExist());
    }

    @AfterAll
    public static void exit() {
        driver.quit();
    }
}
