package ui;

import framework.selenium.DriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Wait;
public abstract class BasePageObject {
    protected WebDriver driver;
    protected Wait<WebDriver> wait;
    public BasePageObject() {
        this.driver = DriverManager.getInstance().getWebDriver();
        this.wait = DriverManager.getInstance().getFluentWait();
        PageFactory.initElements(driver, this);
    }
    public abstract void waitUntilPageObjectIsLoaded() throws WebDriverException;
}
