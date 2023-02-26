package framework;

import utils.LoggerManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
public class CredentialsManager {
    private Properties properties;
    private static final LoggerManager log = LoggerManager.getInstance();
    private static final String envFilePath = System.getProperty("user.dir") + File.separator + "environments.properties";
    private static CredentialsManager instance;
    private String envId;
    private CredentialsManager() {
        initialize();
    }
    private void initialize() {
        log.info("Reading credentials");
        String autoEnvironmentsId = System.getProperty("envId");
        if ((autoEnvironmentsId == null) || (autoEnvironmentsId.isEmpty())) {
            envId = "qa01";
        } else {
            envId = autoEnvironmentsId.toLowerCase();
        }
        log.info("automation Environment Id --> " + envId);
        properties = new Properties();
        Properties envProperties = new Properties();
        try {
            envProperties.load(new FileInputStream(envFilePath));
        } catch (IOException e) {
            log.error("unable to load properties file");
        }
        properties.putAll(envProperties);
    }
    public static CredentialsManager getInstance() {
        if (instance == null) {
            instance = new CredentialsManager();
        }
        return instance;
    }
    private String getEnvironmentSetting(String setting) {
        return (String) getInstance().properties.get(setting);
    }
    public String getEnvId() {
        return envId;
    }
    public String getBaseURL() {
        return getEnvironmentSetting(getEnvId() + ".baseURL");
    }
    public String getLoginEndpoint() {
        return getBaseURL() + getEnvironmentSetting(getEnvId() + ".login.endpoint");
    }
    public String getCartEndpoint() {
        return getBaseURL() + getEnvironmentSetting(getEnvId() + ".cart.endpoint");
    }
    public String getProductsEndpoint() {
        return getBaseURL() + getEnvironmentSetting(getEnvId() + ".products.endpoint");
    }
    public String getProductDetailsEndpoint() {
        return getBaseURL() + getEnvironmentSetting(getEnvId() + ".productdetails.endpoint");
    }
    public String getCheckoutEndpoint() {
        return getBaseURL() + getEnvironmentSetting(getEnvId() + ".checkout.endpoint");
    }
    public String getUserEmail() {
        return getEnvironmentSetting(getEnvId() + ".user.email");
    }
    public String getUserPassword() {
        return getEnvironmentSetting(getEnvId() + ".user.password");
    }
}
