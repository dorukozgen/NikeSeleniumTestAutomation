package base;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Properties;

public abstract class TestBase {

    private static String browserType;
    public static Logger logger;
    public static Properties config;
    public static WebDriver driver;
    public static WebDriverWait wait;
    private static boolean inited = false;

    @BeforeTest
    @Parameters({"browser"})
    public void setup(String browser) {
        setLogger();
        setBrowserType(browser);
        setConfig();
        setDriver();
        inited = true;
    }

    public static void setBrowserType(String browser) {
        logger.info("Setting browser: " + browser + "...");
        browserType = browser;
        logger.info("Browser successfully set!");
    }

    public void setLogger() {
        LoggerContext context = LoggerContext.getContext(false);
        context.setConfigLocation(
                Paths
                        .get("")
                        .resolve("./config/log4j2.xml")
                        .toAbsolutePath()
                        .toUri()
        );

        logger = context.getLogger(TestBase.class);
        logger.info("Logger successfully set!");
    }

    private void setConfig() {
        Properties props = new Properties();
        try {
            FileInputStream fis = new FileInputStream(
                    Paths
                            .get("")
                            .resolve("./config/config.properties")
                            .toAbsolutePath()
                            .toString()
            );
            props.load(fis);
            config = props;
            logger.info("Config successfully set!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setDriver()  {
        Path basePath = Paths.get("");
        if (browserType.equals("chrome")) {
            System.setProperty(
                    "webdriver.chrome.driver",
                    basePath
                            .resolve("./drivers/chromedriver.exe")
                            .toAbsolutePath()
                            .toString()
            );

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-notifications");
            options.addArguments("--disable-infobars");
            options.addArguments("--start-maximized");
            options.addArguments("--disable-extensions");

            driver = new ChromeDriver(options);
            wait = new WebDriverWait(driver, Duration.of(10, ChronoUnit.SECONDS));

            logger.info("Driver successfully set!");
        } else if (browserType.equals("firefox")) {
            System.setProperty(
                    "webdriver.gecko.driver",
                    basePath
                            .resolve("./drivers/geckodriver.exe")
                            .toAbsolutePath()
                            .toString()
            );

            FirefoxProfile profile = new FirefoxProfile();
            profile.setPreference("dom.webnotifications.enabled", false);
            profile.setPreference("dom.push.enabled", false);
            profile.setPreference("browser.download.folderList", 2);

            FirefoxBinary firefoxBinary = new FirefoxBinary(
                    new File(
                            "C:\\Program Files\\Mozilla Firefox\\firefox.exe"
                    )
            );

            FirefoxOptions options = new FirefoxOptions();
            options.setBinary(firefoxBinary);
            options.setProfile(profile);


            driver = new FirefoxDriver(options);
            driver.manage().window().maximize();
            wait = new WebDriverWait(driver, Duration.of(10, ChronoUnit.SECONDS));
        }
    }

    public void destroyDriver() {
        driver.quit();
        driver = null;
    }

    @AfterTest(alwaysRun = true)
    public void afterSuite() {
        destroyDriver();
        logger.info("Driver destroyed!");
    }

}
