package org.example.core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class browserSetup {

    public static WebDriver driver;

    public static WebDriverWait wait;

    private static Properties properties = new Properties();

    public static String readProperty(String key) {
        try {
            if (properties.isEmpty()) {
                String module = System.getProperty("module");
                InputStream commonConfig = browserSetup.class.getClassLoader()
                        .getResourceAsStream("common.properties");
                properties.load(commonConfig);
                InputStream moduleConfig = browserSetup.class.getClassLoader()
                        .getResourceAsStream(module + ".properties");
                properties.load(moduleConfig);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return properties.getProperty(key);
    }

    public static void invokeBrowser() {
        System.setProperty("webdriver.chrome.driver", "/Users/kartik/Documents/bin/chromedriver");
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("autofill.credit_card_enabled", false);

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }

    public static void quitBrowser() {
        driver.close();
        driver.quit();
    }
}
