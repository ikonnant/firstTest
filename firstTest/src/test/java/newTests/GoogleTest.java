package newTests;

import com.codeborne.selenide.Configuration;
import configuration.BaseTests;
import configuration.Config;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import java.io.*;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class GoogleTest extends Config{

    @Before
    public void updateConfig() {
        Configuration.headless = false;
        Configuration.holdBrowserOpen = true;
        //Config.baseName = "1";
        //Config.basePassword = "1";
    }

    @Test
    public void test() throws IOException {
        //open("/", "", Config.baseName, Config.basePassword);
        open("/");
        BaseTests.shouldResponseCode(200);

        $(By.name("q")).setValue("Selenide").pressEnter();

        $$("#rso .g").shouldHaveSize(6);
        $("#rso .g").shouldHave(text("Se1lenide: лаконичные и стабильные UI тесты на Java"));
        //Assert.fail(String.valueOf($("#rso .g").getSize().getWidth()));
    }

    @AfterClass
    public static void fails() {
        open("https://ruseller.com/lessons/les1641/demo/index.html");
    }
}
