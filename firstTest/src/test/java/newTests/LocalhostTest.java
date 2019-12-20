package newTests;

import com.codeborne.selenide.Configuration;
import configuration.BaseTests;
import configuration.Config;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.clearBrowserCache;

public class LocalhostTest extends Config {
    static String url;

    @BeforeClass
    public static void beforeTest() throws IOException {
        Configuration.baseUrl = "http://localhost";

        BaseTests.bitrixAuthorize();

        url = BaseTests.bitrixAddNews(new String[][]{
            {"NAME", "ТЕСТОВАЯ НОВОСТЬ", "setValue"},
            {"PROP[47][]", " ЖК Fjord", "selectOption"},
            {"XML_ID", "123456789TEST", "setValue"}
        });

        BaseTests.bitrixDeauthorize();
    }

    @AfterClass
    public static void fails() throws IOException {
        BaseTests.bitrixAuthorize();
        BaseTests.bitrixDeleteNews(url);
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
