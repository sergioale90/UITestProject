package framework.selenium;

import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.v85.page.Page;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import utils.LoggerManager;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import static constants.DomainAppConstants.*;
public class DriverManager {
    private static final LoggerManager log = LoggerManager.getInstance();
    public static final DriverConfig driverConfig = DriverConfig.getInstance();
    private WebDriver driver;
    private static DriverManager instance;
    private Wait<WebDriver> wait;
    private static final String pathAddBlocker = System.getProperty("user.dir") + File.separator + "src/test/resources/utils/extensions/ublock.crx";
    protected DriverManager() {
        initialize();
    }
    public static DriverManager getInstance() {
        if ((instance == null) || (instance.driver == null)) {
            instance = new DriverManager();
        }
        return instance;
    }
    private void initialize() {
        log.info("Initializing Selenium webdriver manager");
        switch (driverConfig.getBrowser()) {
            case CHROME:
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                chromeOptions.setExperimentalOption("useAutomationExtension", false);
                chromeOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
                chromeOptions.addExtensions(new File(pathAddBlocker));
                chromeOptions.addArguments("--password-store=basic");
                Map<String, Object> prefs = new HashMap<String, Object>();
                prefs.put("credentials_enable_service", false);
                prefs.put("profile.password_manager_enabled", false);
                chromeOptions.setExperimentalOption("prefs", prefs);
                if (driverConfig.getHeadlessMode()) {
                    chromeOptions.addArguments("--headless");
                }
                driver = new ChromeDriver(chromeOptions);
                break;
            case FIREFOX:
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                firefoxOptions.setLogLevel(FirefoxDriverLogLevel.FATAL);
                if (driverConfig.getHeadlessMode()) {
                    firefoxOptions.addArguments("--headless");
                }
                driver = new FirefoxDriver(firefoxOptions);
                break;
            case EDGE:
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                edgeOptions.setExperimentalOption("useAutomationExtension", false);
                edgeOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
                edgeOptions.addExtensions(new File (pathAddBlocker));
                edgeOptions.addArguments("--password-store=basic");
                Map<String, Object> prefsEdge = new HashMap<String, Object>();
                prefsEdge.put("credentials_enable_service", false);
                prefsEdge.put("profile.password_manager_enabled", false);
                edgeOptions.setExperimentalOption("prefs", prefsEdge);
                if (driverConfig.getHeadlessMode()) {
                    edgeOptions.addArguments("--headless");
                }
                driver = new EdgeDriver(edgeOptions);
                break;
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(driverConfig.getImplicitWaitTime());
        wait = new FluentWait<>(driver)
                .withTimeout(driverConfig.getTimeOut())
                .pollingEvery(driverConfig.getPollingTime())
                .ignoring(NoSuchElementException.class)
                .ignoring(NotFoundException.class)
                .ignoring(StaleElementReferenceException.class);
    }
    public WebDriver getWebDriver() {
        return driver;
    }
    public Wait<WebDriver> getFluentWait() {
        return wait;
    }
    public void quitWebDriver() {
        try {
            log.info("closing WebDriver");
            driver.quit();
        } catch (Exception e) {
            log.error (e.getMessage());
        }
    }
}
