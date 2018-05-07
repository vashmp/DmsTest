import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.Properties;


public class BaseTest {
    WebDriver driver;
    private final int defTime = 3;
    String baseUrl;
    public static Properties properties = TestProperties.getInstance().getProperties();

    @Before
    public void setUp() throws Exception{
        System.setProperty("webdriver.chrome.driver", properties.getProperty("webdriver.chrome.driver"));
        driver = new ChromeDriver();
        baseUrl = properties.getProperty("app.url");
        driver.get(baseUrl);
        driver.manage().window().maximize();
    }

    @After
    public void tearDown(){
        driver.quit();
    }
    public void waitVisibilityOf(By locator) {
        waitVisibilityOf(locator, defTime);
    }

    public void waitVisibilityOf(By locator, int timeout){
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(locator)));
    }


}
