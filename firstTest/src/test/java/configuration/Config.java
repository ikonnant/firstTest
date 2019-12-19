package configuration;

import com.codeborne.selenide.Configuration;
import org.junit.BeforeClass;

public class Config {
    protected static String userName = "###";
    protected static String userPassword = "###";
    protected static int newsIblockID = 0;
    protected static String directoryType = "###";
    protected static String baseName;
    protected static String basePassword;

    @BeforeClass
    public static void setConfig() {
        Configuration.browser = "chrome";
        //Configuration.browserSize = "1024x800";
        Configuration.headless = true;
        Configuration.baseUrl = "https://google.com";
    }
}
