package pages;

import helpers.DriverManager;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

import java.net.MalformedURLException;

public class Login {
    private AndroidDriver driver;

    private By usernameInput = By.id("com.leavjenn.hews:id/et_user_name");
    private By passwordInput = By.id("com.leavjenn.hews:id/et_password");
    private By errorMessage = By.id("com.leavjenn.hews:id/tv_prompt");
    private By alertTitle = By.id("android:id/alertTitle");
    private By cancelBtn = By.id("android:id/button2");
    private By loginBtn = By.id("android:id/button1");
    private By progress = By.id("com.leavjenn.hews:id/progressbar_login");
    private By form = By.id("android:id/parentPanel");
    private Menu menu = new Menu();


    public Login() throws MalformedURLException {
        this.driver = DriverManager.getDriver();
    }

    public void openForm(){
        menu.open();
        menu.clickLogin();
    }

    public void fillInUsername(String username){
        driver.findElement(usernameInput).sendKeys(username);
    }

    public String getUsernameFieldValue(){
        return driver.findElement(usernameInput).getText();
    }

    public void fillInPassword(String password){
        driver.findElement(passwordInput).sendKeys(password);
    }

    public String getPasswordFieldValue(){
        return driver.findElement(passwordInput).getText();
    }

    public void clickLogin(){
        driver.findElement(loginBtn).click();
    }

    public void cancel(){
        driver.findElement(cancelBtn).click();
    }

    public void login(String username, String password){
        this.fillInUsername(username);
        this.fillInPassword(password);
        this.clickLogin();
    }

    public boolean isProgressDisplayed(){
        return driver.findElement(progress).isDisplayed();
    }

    public boolean isFormDisplayed() {
        try{
            return driver.findElement(form).isDisplayed();
        }catch(NoSuchElementException e){
            return false;
        }
    }

    public String getErrorMessage(){
        return driver.findElement(errorMessage).getText();
    }

}
