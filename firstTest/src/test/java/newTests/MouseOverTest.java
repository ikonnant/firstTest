package newTests;

import com.codeborne.selenide.Configuration;
import configuration.BaseTests;
import configuration.Config;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class MouseOverTest extends Config {
    @Before
    public void updateConfig() {
        Configuration.baseUrl = "https://ruseller.com/lessons/les1641/demo/index.html";
        //Configuration.holdBrowserOpen = true;
    }

    @Test
    public void test() throws IOException {
        open("");
        BaseTests.shouldResponseCode(200);

        $("#ddmenu > li:nth-child(2) > a").hover();
        //$("#ddmenu > li:nth-child(2) > ul > li:nth-child(2) > a").hover();

        $("#ddmenu > li:nth-child(2) > ul > li:nth-child(2) > a").shouldBe(visible);
        $("#ddmenu > li:nth-child(2) > ul > li:nth-child(2) > a").shouldHave(text("Команда"));
    }
}
