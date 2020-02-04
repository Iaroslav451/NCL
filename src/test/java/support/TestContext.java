// Created by Iaroslav Bashtanar

package support;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class TestContext {

    private static WebDriver driver;

    public static JavascriptExecutor getExecutor() {
        // casting WebDriver to JavascriptExecutor
        return (JavascriptExecutor) driver;
    }

    public static WebDriver driver() {
        return driver;
    }

    public static void initialize(String Browser) {
        initialize(Browser, false); //Switch thru Browsers
    }

    public static void initialize(String browser, boolean isHeadless) {
        String osName = System.getProperty("os.name");
        switch (browser) {
            case "chrome":
                String chromeDriverName = "chromedriver.exe";
                if (osName != null && (osName.contains("Mac") || osName.contains("Linux"))) {
                    chromeDriverName = "chromedriver";
                }
                System.setProperty("webdriver.chrome.driver", getDriversDirPath() + chromeDriverName);
                Map<String, Object> chromePreferences = new HashMap<>();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--start-maximized");
                chromeOptions.setExperimentalOption("prefs", chromePreferences);
                if (isHeadless) {
                    chromeOptions.setHeadless(true);
                    chromeOptions.addArguments("--window-size=1920,1080");
                    chromeOptions.addArguments("--disable-gpu");
                }
                driver = new ChromeDriver(chromeOptions);
                break;
            case "any browser":
            default:
                throw new RuntimeException("I don`t work with this browser: " + browser);
        }
    }

    private static String getDriversDirPath() {
        return System.getProperty("user.dir") + String.format("%1$ssrc%1$stest%1$sresources%1$sdrivers%1$s", File.separator);
    }

    private static String getDownloadsPath() {
        return System.getProperty("user.dir") + String.format("%1$ssrc%1$stest%1$sresources%1$sdownloads%1$s", File.separator);
    }

    @Before(order = 0)
    public void beforeMessage() {
        System.out.println("\n----------------- Start of Scenario -----------------\n");
    }

    @Before(order = 1)
    public void scenarioStart() {
        TestContext.initialize("chrome"); //type what browser you want to use
        driver().manage().deleteAllCookies();
        driver().manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        driver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver().navigate().to("https://www.ncl.com/");

    }


    @After(order = 0)
    public void scenarioEnd(Scenario scenario) {
        if (scenario.isFailed()) {
            System.out.println("\n----------------- Houston we have a problem -----------------\n");
        } else if (!scenario.isFailed()) {
            System.out.println("\n----------------- Houston everything is good -----------------\n");
        }
        driver().quit();
    }

}
