package pages;

import base.PageBase;
import base.TestBase;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class NikePage extends PageBase {

    Logger logger = TestBase.logger;

    @FindBy(xpath = "/html/body/div[3]/div/div[3]/header/div/div[1]/div[2]/div/div/div[1]/div/div/input")
    WebElement searchInput;

    List<WebElement> searchResults;

    public NikePage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void open(String url) {
        logger.info("Opening page: " + url + "...");

        driver.get(url);
        waitPageLoad();

        logger.info("Opened page: " + url);
    }

    public void search(String text) {
        logger.info("Searching for: " + text + "...");

        type(searchInput, text);
        sleep(1);
        waitPageLoad();

        logger.info("Searched for: " + text);
    }

    public boolean checkProducts(String name) {
        logger.info("Checking products: " + name + "...");

        searchResults = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.xpath("/html/body/div[3]/div/div[3]/header/div/div[1]/div[2]/div/div/div[2]/div[2]/ul/li/a/figcaption/h4")
                )
        );

        boolean check = searchResults.stream().anyMatch(
                element -> {
                    return element.getText().toLowerCase().contains(name.toLowerCase());
                }
        );

        logger.info("Checked products: " + check);

        return check;
    }
}
