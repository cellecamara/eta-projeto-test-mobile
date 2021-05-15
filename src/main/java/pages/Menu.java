package pages;

import helpers.DriverManager;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

import java.net.MalformedURLException;

public class Menu {
    private AndroidDriver driver;

    private By menuBtn = By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']");
    private By loginExpander = By.id("com.leavjenn.hews:id/iv_expander");
    private By loggedUser = By.id("com.leavjenn.hews:id/tv_account");
    private By loginBtn = By.id("com.leavjenn.hews:id/design_menu_item_text");

    public Menu() throws MalformedURLException {
        this.driver = DriverManager.getDriver();
    }

    public void open(){
        driver.findElement(menuBtn).click();
    }

    public void clickLogin(){
        driver.findElement(loginExpander).click();
        driver.findElement(loginBtn).click();
    }

    public String getLoggedUser(){
        return driver.findElement(loggedUser).getText();
    }

    public void logout(){
        driver.findElement(loginExpander).click();
        driver.findElement(loginExpander).click();
        driver.findElement(loginBtn).click();
    }
}