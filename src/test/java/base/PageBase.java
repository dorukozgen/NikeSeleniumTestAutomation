package base;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class PageBase {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public PageBase(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    public static void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void sleep(double seconds) {
        try {
            Thread.sleep((long) (seconds * 1000L));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void type(WebElement element, String text) {
        String[] textChars = text.split("");
        for (String _char : textChars) {
            sleep(0.1);
            element.sendKeys(_char);
        };
    }

    public void type(WebElement element, String text, double delay) {
        String[] textChars = text.split("");
        for (String _char : textChars) {
            sleep(delay);
            element.sendKeys(_char);
        };
    }

    public void type(WebElement element, String text, int delay) {
        String[] textChars = text.split("");
        for (String _char : textChars) {
            sleep(delay);
            element.sendKeys(_char);
        };
    }

    public void waitPageLoad() {
        wait.until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete")
        );
    }

}
