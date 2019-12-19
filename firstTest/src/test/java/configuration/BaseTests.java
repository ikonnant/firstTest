package configuration;

import org.apache.commons.codec.binary.Base64;
import org.junit.Assert;
import org.openqa.selenium.By;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.codeborne.selenide.Selenide.*;
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
}
