package configuration;

import com.codeborne.selenide.Configuration;
import org.apache.commons.codec.binary.Base64;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.driver;
import static com.codeborne.selenide.WebDriverRunner.url;

public class BaseTests {
    public static void shouldResponseCode(int respCode) throws IOException {
        URL url = new URL(url());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        if (Config.baseName != null && Config.basePassword != null) {
            String authString = Config.baseName + ":" + Config.basePassword;
            byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
            String authStringEnc = new String(authEncBytes);

            connection.setRequestProperty("Authorization", "Basic " + authStringEnc);
        }

        connection.connect();

        if (connection.getResponseCode() != respCode) {
            Assert.fail(url() + " Error: Response code not " + respCode + " : " + connection.getResponseCode());
            close();
        }
    }

    public static void bitrixAuthorize() throws IOException {
        open("/bitrix/admin/");
        shouldResponseCode(200);

        if (!$(By.cssSelector(".adm-main-menu")).isDisplayed()) {
            $(By.name("USER_LOGIN")).setValue(Config.userName);
            $(By.name("USER_PASSWORD")).setValue(Config.userPassword);
            $(By.name("Login")).pressEnter();
        }
    }

    public static void bitrixDeauthorize() throws IOException {
        open("/bitrix/admin/");
        shouldResponseCode(200);

        if (!$(By.name("form_auth")).isDisplayed()) {
            $(By.cssSelector(".adm-header-exit")).click();
        }
    }

    @NotNull
    public static String bitrixAddNews(@NotNull String[][] inputs) throws IOException {
        open("/bitrix/admin/iblock_element_edit.php?IBLOCK_ID=" + Config.newsIblockID + "&type=" + Config.directoryType + "&ID=0&lang=ru&find_section_section=0&IBLOCK_SECTION_ID=0&from=iblock_list_admin");
        shouldResponseCode(200);

        for (String[] input : inputs) {
            switch (input[2]) {
                case ("setValue"):
                    $(By.name(input[0])).setValue(input[1]);
                    break;
                case ("selectOption"):
                    $(By.name(input[0])).selectOption(input[1]);
                    break;
            }
        }
        $(By.name("apply")).pressEnter();

        return url().replace(Configuration.baseUrl, "");
    }

    public static void bitrixDeleteNews(String url) throws IOException {
        open(url);
        BaseTests.shouldResponseCode(200);

        WebDriver driver = driver().getWebDriver();

        $(By.cssSelector(".adm-btn.adm-btn-add.adm-btn-menu")).click();
        screenshot("fails");
        $(By.cssSelector(".bx-core-popup-menu-item:last-child")).click();

        driver.switchTo().alert().accept();
        driver.quit();
    }
}
