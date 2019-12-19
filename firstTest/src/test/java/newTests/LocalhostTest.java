package newTests;

import com.codeborne.selenide.Configuration;
import configuration.BaseTests;
import configuration.Config;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.io.IOException;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.*;

public class LocalhostTest extends Config {
    static String url;

    @BeforeClass
    public static void beforeTest() throws IOException {
        Configuration.baseUrl = "http://localhost";

        clearBrowserCache();
        BaseTests.bitrixAuthorize();
        addNews();
        BaseTests.bitrixDeauthorize();
    }

    public static void addNews() throws IOException {
        open("/bitrix/admin/iblock_element_edit.php?IBLOCK_ID=" + Config.newsIblockID + "&type=" + Config.directoryType + "&ID=0&lang=ru&find_section_section=0&IBLOCK_SECTION_ID=0&from=iblock_list_admin");
        BaseTests.shouldResponseCode(200);

        $(By.name("NAME")).setValue("ТЕСТОВАЯ НОВОСТЬ");
        //$(By.name("XML_ID")).setValue("123456789TEST");
        $(By.name("PROP[47][]")).selectOption(" ЖК Fjord");
        $(By.name("apply")).pressEnter();

        url = url().replace(Configuration.baseUrl, "");
    }

    public static void deleteNews() throws IOException {
        open(url);
        BaseTests.shouldResponseCode(200);

        WebDriver driver = driver().getWebDriver();

        $(By.cssSelector(".adm-btn.adm-btn-add.adm-btn-menu")).click();
        screenshot("fails");
        $(By.cssSelector(".bx-core-popup-menu-item:last-child")).click();

        driver.switchTo().alert().accept();
        driver.quit();
    }

    @AfterClass
    public static void fails() throws IOException {
        BaseTests.bitrixAuthorize();
        deleteNews();
        close();
    }

    @Before
    public void clearCache() {
        clearBrowserCache();
    }

    @Test
    public void testMainResponseCode400() throws IOException {
        open("/");
        BaseTests.shouldResponseCode(400);
    }

    @Test
    public void test404ResponseCode() throws IOException {
        BaseTests.bitrixAuthorize();

        open("/404/");
        screenshot("test404ResponseCode");
        BaseTests.shouldResponseCode(404);
    }
}
