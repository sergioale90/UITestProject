package ui;

import framework.CredentialsManager;
import framework.selenium.DriverManager;
import org.openqa.selenium.WebDriver;
import ui.user.pages.*;
import utils.LoggerManager;

public class PageTransporter {
    private static final LoggerManager log = LoggerManager.getInstance();
    private static final CredentialsManager credentialsManager = CredentialsManager.getInstance();

    private WebDriver driver;
    private String loginUserURL;
    private String homeURL;
    private String cartURL;
    private String productsURL;
    private String productDetailsURL;
    private String checkoutURL;
    private static PageTransporter instance;

    protected PageTransporter() {
        initialize();
    }

    public static PageTransporter getInstance() {
        if (instance == null) {
            instance = new PageTransporter();
        }
        return instance;
    }

    private void initialize() {
        log.info("Initializing Page Transporter");
        loginUserURL = credentialsManager.getLoginEndpoint();
        homeURL = credentialsManager.getBaseURL();
        cartURL = credentialsManager.getCartEndpoint();
        productsURL = credentialsManager.getProductsEndpoint();
        productDetailsURL = credentialsManager.getProductDetailsEndpoint();
        checkoutURL = credentialsManager.getCheckoutEndpoint();
        driver = DriverManager.getInstance().getWebDriver();
    }

    private void goToURL(String url) {
        driver.navigate().to(url);
    }

    public boolean isOnLoginUserPage() {
        return driver.getCurrentUrl().contains(loginUserURL);
    }
    public boolean isOnProductsPage() {
        return driver.getCurrentUrl().contains(productsURL);
    }
    public boolean isOnProductDetailsPage() {
        return driver.getCurrentUrl().contains(productDetailsURL);
    }
    public boolean isOnCartPage() {
        return driver.getCurrentUrl().contains(cartURL);
    }
    public boolean isOnCheckoutPage() {
        return driver.getCurrentUrl().contains(checkoutURL);
    }

    public boolean isOnHomePage() {
        return driver.getCurrentUrl().contains(homeURL);
    }
}
