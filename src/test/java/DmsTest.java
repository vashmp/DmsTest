
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author Nikita Blokhin
 */
public class DmsTest {
    WebDriver driver;

    @Before
    public void setUp(){
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://www.rgs.ru");
    }

    @After
    public void tearDown(){
        driver.quit();
    }


    @Test
    public void dmsTest() throws InterruptedException {

        WebElement webElement = driver.findElement(By.xpath(".//*[@id='main-navbar-collapse']/ol[1]/li[2]/a")); // не придумал как обратиться по другому
        new Actions(driver).moveToElement(webElement).perform();
        webElement.click();
        WebDriverWait wait = new WebDriverWait(driver, 3);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(".//*[@id='rgs-main-menu-insurance-dropdown']/div[1]/div[1]/div/div[1]/div[3]/ul/li[2]/a"))));
        WebElement control = driver.findElement(By.xpath(".//*[@id='rgs-main-menu-insurance-dropdown']/div[1]/div[1]/div/div[1]/div[3]/ul/li[2]/a"));
        control.click();
        //проверка заголовка
        WebElement header = driver.findElement(By.xpath(".//h1"));
        String factHeader = header.getText();
        Assert.assertTrue("Заголовок не соответствует ", factHeader.contains("добровольное медицинское страхование"));
        WebElement sendForm = driver.findElement(By.xpath(".//div[@class='rgs-context-bar-content-call-to-action-buttons']/a[@class='btn btn-default text-uppercase hidden-xs adv-analytics-navigation-desktop-floating-menu-button']"));
        Thread.sleep(500); //без задержки вылетает краш. Не знаю как подругому сделать
        sendForm.click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(".//h4[@class='modal-title']/b[text()!='']"))));
        WebElement headerForm = driver.findElement(By.xpath(".//h4[@class='modal-title']/b[text()!='']"));
        Assert.assertEquals("Титульник не соответствует",
                "Заявка на добровольное медицинское страхование", headerForm.getText());

        //заполнение полей
        WebElement lastName = driver.findElement(By.xpath(".//input[@name='LastName']"));
        lastName.sendKeys("Фамилия");
        WebElement firstName = driver.findElement(By.xpath(".//input[@name='FirstName']"));
        firstName.sendKeys("Имя");
        WebElement middleName = driver.findElement(By.xpath(".//input[@name='MiddleName']"));
        middleName.sendKeys("Отчество");
        /*WebElement region = driver.findElement(By.xpath(".//select[@name='Region']"));
        region.click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(".//select[@name='Region']/option[@value='77']"))));
        WebElement regionMSK = driver.findElement(By.xpath(".//select[@name='Region']/option[@value='77']"));
        regionMSK.click();*/
        WebElement region = driver.findElement(By.xpath(".//select[@name='Region']"));
        Select regionSelect = new Select(region);
        regionSelect.selectByValue("77");
        WebElement telephone = driver.findElement(By.xpath(".//label[@class='control-label' and text()='Телефон']/following::input"));
        telephone.sendKeys("9999999999");
        WebElement email = driver.findElement(By.xpath(".//input[@name='Email']"));
        email.sendKeys("qwertyqwerty");
        WebElement comment = driver.findElement(By.xpath(".//textarea[@name='Comment']"));
        comment.sendKeys("Комментарий");
        WebElement checkBox = driver.findElement(By.xpath(".//input[@class='checkbox']"));
        checkBox.click();

        //проверка введеных полей
        Assert.assertEquals("Содержимое поля соответстувет выбору в селекте",
                "Фамилия", lastName.getAttribute("value"));
        Assert.assertEquals("Содержимое поля соответстувет выбору в селекте",
                "Имя", firstName.getAttribute("value"));
        Assert.assertEquals("Содержимое поля соответстувет выбору в селекте",
                "Отчество", middleName.getAttribute("value"));
        Assert.assertEquals("Содержимое поля соответстувет выбору в селекте",
                "+7 (999) 999-99-99", telephone.getAttribute("value"));
        Assert.assertEquals("Содержимое поля соответстувет выбору в селекте",
                "qwertyqwerty", email.getAttribute("value"));
        Assert.assertEquals("Содержимое поля соответстувет выбору в селекте",
                "Комментарий", comment.getAttribute("value"));

        WebElement buttonCommit = driver.findElement(By.xpath(".//button[@id='button-m']"));
        buttonCommit.click();
        //проверка сообщения об ошибке email
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(".//span[@class='validation-error-text' and text()='Введите адрес электронной почты']"))));
        WebElement errorMessage = driver.findElement(By.xpath(".//span[@class='validation-error-text' and text()='Введите адрес электронной почты']"));
        Assert.assertEquals("Сообщения об ошибке нет",
                "Введите адрес электронной почты", errorMessage.getText());
    }

}


