package tests;

import base.TestBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.NikePage;

public class Test1 extends TestBase {

    NikePage nikePage;

    public Test1() {
        super();
    }

    @BeforeClass
    void beforeClass() {
        nikePage = new NikePage(driver, wait);
    }

    @Test(priority = 1, description = "Open Nike page")
    void OpenPage() {
        nikePage.open(
                config.getProperty("base.url")
        );
    }

    @Test(priority = 2, description = "Search for products")
    void SearchProducts() {
        nikePage.search(
                config.getProperty("searchInput.string")
        );
    }

    @Test(priority = 3, description = "Check products with specific name")
    void CheckProducts() {
        boolean check = nikePage.checkProducts(
                config.getProperty("searchInput.string")
        );

        Assert.assertTrue(check == true);
    }

}
