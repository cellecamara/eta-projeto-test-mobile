package main;

import helpers.DriverManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.GsmCallActions;
import io.appium.java_client.android.connection.ConnectionStateBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pages.Login;
import pages.Menu;

import java.net.MalformedURLException;


public class Main {

    private AndroidDriver driver;
    static AppiumDriver appiumDriver;

    @Before
    public void setUp() throws MalformedURLException {
        driver = DriverManager.getDriver();
        setConnectionToON();
    }

    @After
    public void tearDown(){
        DriverManager.endSession();
    }

    @Test
    public void loginWithSuccess() throws InterruptedException, MalformedURLException {
        Thread.sleep(5000);
        Menu menu = new Menu();
        Login login = new Login();

        login.openForm();
        Thread.sleep(5000);
        login.login("cellecamara", "12345678");
        Assert.assertTrue(login.isProgressDisplayed());
        Thread.sleep(2000);
        menu.open();
        Assert.assertEquals(menu.getLoggedUser(), "cellecamara");
    }

    @Test
    public void logout() throws InterruptedException, MalformedURLException {
        Thread.sleep(5000);
        Menu menu = new Menu();
        Login login = new Login();

        login.openForm();
        Thread.sleep(5000);
        login.login("cellecamara", "12345678");
        Assert.assertTrue(login.isProgressDisplayed());
        Thread.sleep(2000);
        menu.open();
        menu.logout();
        Thread.sleep(2000);
        menu.open();
        Assert.assertEquals("Logout", menu.getLoggedUser());
    }

    @Test
    public void loginWithWrongCredentials() throws InterruptedException, MalformedURLException {
        Thread.sleep(5000);
        Menu menu = new Menu();
        Login login = new Login();

        login.openForm();
        Thread.sleep(5000);
        login.login("cellecamara", "1234567");
        Thread.sleep(2000);
        Assert.assertEquals("Arrr…wrong username/password", login.getErrorMessage());
    }

    @Test
    public void cancelLogin() throws InterruptedException, MalformedURLException {
        Thread.sleep(5000);
        Login login = new Login();

        login.openForm();
        Thread.sleep(2000);
        Assert.assertTrue(login.isFormDisplayed());
        Thread.sleep(5000);
        login.fillInUsername("cellecamara");
        login.cancel();
        Thread.sleep(2000);
        Assert.assertFalse(login.isFormDisplayed());
    }

    @Test
    public void receiveCallDuringLoginKeepsFieldsValues() throws InterruptedException, MalformedURLException {
        String username = "cellecamara";
        String password = "12345678";

        Thread.sleep(5000);
        Login login = new Login();

        login.openForm();
        Thread.sleep(2000);
        login.fillInUsername(username);
        login.fillInPassword(password);

        driver.makeGsmCall("5551237890", GsmCallActions.CALL);
        Thread.sleep(2000);
        driver.makeGsmCall("5551237890", GsmCallActions.ACCEPT);
        Thread.sleep(2000);
        driver.makeGsmCall("5551237890", GsmCallActions.CANCEL);
        Thread.sleep(2000);

        Assert.assertTrue(login.isFormDisplayed());
        Assert.assertEquals(username, login.getUsernameFieldValue());
        Assert.assertEquals("••••••••", login.getPasswordFieldValue());
    }

    @Test
    public void loginWithoutConnection() throws InterruptedException, MalformedURLException {
        Thread.sleep(5000);
        Login login = new Login();

        login.openForm();
        Thread.sleep(5000);

        setConnectionToOFF();
        login.login("cellecamara", "12345678");
        Thread.sleep(2000);
        Assert.assertEquals("Where's the Internet? Can't get it", login.getErrorMessage());
    }

    @Test
    public void loginAfterConnectionReestablished() throws InterruptedException, MalformedURLException {
        Thread.sleep(5000);
        Login login = new Login();
        Menu menu = new Menu();

        login.openForm();
        Thread.sleep(5000);

        setConnectionToOFF();
        login.login("cellecamara", "12345678");
        Thread.sleep(2000);

        setConnectionToON();
        Thread.sleep(2000);
        login.clickLogin();
        Assert.assertTrue(login.isProgressDisplayed());
        Thread.sleep(2000);
        menu.open();
        Assert.assertEquals(menu.getLoggedUser(), "cellecamara");
    }

    public void setConnectionToOFF() {
        try {
            driver.setConnection(new ConnectionStateBuilder().withWiFiDisabled().build());
        } catch (Exception e) {
            System.out.println("Connection could not be switch OFF");
        }
    }

    public void setConnectionToON() {
        try {
            driver.setConnection(new ConnectionStateBuilder().withWiFiEnabled().build());
        } catch (Exception e) {
            System.out.println("Connection could not be switch ON");
        }
    }


}



