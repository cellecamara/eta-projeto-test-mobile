package helpers;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;


public class DriverManager {

    private static AndroidDriver driver;

    public static AndroidDriver getDriver() throws MalformedURLException {
        if (driver == null) {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability( "deviceName", "testdevice" );
            capabilities.setCapability( "platformName", "Android" );
            capabilities.setCapability( "app", "/Users/marcellecamara/Documents/Pessoal/Pos/hacker_news.apk" );
            //Nota: Não consegui fazer funcionar com o apk nesta pasta
//            capabilities.setCapability( "app", "src/main/apps/hacker_news.apk" );
            driver = new AndroidDriver( new URL( "http://localhost:4723/wd/hub" ), capabilities );
        }

        return driver;
    }

    public static void endSession() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}