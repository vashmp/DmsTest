
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

/**
 * @author Nikita Blokhin
 */
public class DmsTest extends BaseTest{
    private final By topBarSelect = By.xpath(".//a[contains(text(),'Страхование')]");
    private final By dmsSelect = By.xpath(".//a[contains(text(),'ДМС')]");
    private final By headerContainer = By.xpath(".//h1");
    private final By sendButton = By.xpath("//a[contains(text(),'Отправить заявку')]");
    private final By headerFormContent = By.xpath(".//h4[@class='modal-title']/b[text()!='']");

    private final By prsLastName = By.xpath(".//input[@name='LastName']");
    private final By prsFirstName = By.xpath(".//input[@name='FirstName']");
    private final By prsMiddleName = By.xpath(".//input[@name='MiddleName']");
    private final By prsRegion = By.xpath(".//select[@name='Region']");
    private final By prsPhone = By.xpath(".//label[@class='control-label' and text()='Телефон']/following::input");
    private final By prsEmail = By.xpath(".//input[@name='Email']");
    private final By prsComment = By.xpath(".//textarea[@name='Comment']");
    private final By prsCheckBox = By.xpath(".//input[@class='checkbox']");
    private final By prsErrorMsg = By.xpath(".//span[@class='validation-error-text' and text()='Введите адрес электронной почты']");
    private final By prsButton = By.xpath(".//button[@id='button-m']");




    @Test
    public void dmsTest() throws InterruptedException {
        Person person = Person.generateRandomPerson(Person.getRandomValues());
        WebElement topBar = driver.findElement(topBarSelect);
        new Actions(driver).moveToElement(topBar).perform();
        topBar.click();
        waitVisibilityOf(dmsSelect);
        WebElement control = driver.findElement(dmsSelect);
        control.click();
        //проверка заголовка
        WebElement header = driver.findElement(headerContainer);
        String factHeader = header.getText();
        Assert.assertTrue("Заголовок не соответствует ", factHeader.contains("добровольное медицинское страхование"));
        //вызов формы отправки
        WebElement sendForm = driver.findElement(sendButton);
        Thread.sleep(500);
        sendForm.click();
        waitVisibilityOf(headerFormContent);
        WebElement headerForm = driver.findElement(headerFormContent);
        Assert.assertEquals("Титульник не соответствует",
                "Заявка на добровольное медицинское страхование", headerForm.getText());

        //заполнение полей
        fillDMSForm(person);
        //проверка заполненных полей
        checkDMSForm(person);

        //отправка результатов
        WebElement buttonCommit = driver.findElement(prsButton);
        buttonCommit.click();
        //проверка сообщения об ошибке email
        waitVisibilityOf(prsErrorMsg);
        WebElement errorMessage = driver.findElement(prsErrorMsg);
        Assert.assertEquals("Сообщения об ошибке нет",
                "Введите адрес электронной почты", errorMessage.getText());
    }

    public void fillDMSForm(Person person){
        WebElement lastName = driver.findElement(prsLastName);
        lastName.sendKeys(person.lastName);
        WebElement firstName = driver.findElement(prsFirstName);
        firstName.sendKeys(person.firstName);
        WebElement middleName = driver.findElement(prsMiddleName);
        middleName.sendKeys(person.middleName);
        WebElement region = driver.findElement(prsRegion);
        Select regionSelect = new Select(region);
        regionSelect.selectByValue("77");
        WebElement telephone = driver.findElement(prsPhone);
        telephone.sendKeys(person.telephoneName);
        WebElement email = driver.findElement(prsEmail);
        email.sendKeys("qwertyqwerty");
        WebElement comment = driver.findElement(prsComment);
        comment.sendKeys("Комментарий");
        WebElement checkBox = driver.findElement(prsCheckBox);
        checkBox.click();

    }
    public void checkDMSForm (Person person){
        WebElement lastName = driver.findElement(prsLastName);
        WebElement firstName = driver.findElement(prsFirstName);
        WebElement middleName = driver.findElement(prsMiddleName);
        WebElement telephone = driver.findElement(prsPhone);
        WebElement email = driver.findElement(prsEmail);
        WebElement comment = driver.findElement(prsComment);
        Assert.assertEquals("Содержимое поля соответстувет выбору в селекте",
                person.lastName, lastName.getAttribute("value"));
        Assert.assertEquals("Содержимое поля соответстувет выбору в селекте",
                person.firstName, firstName.getAttribute("value"));
        Assert.assertEquals("Содержимое поля соответстувет выбору в селекте",
                person.middleName, middleName.getAttribute("value"));
        Assert.assertEquals("Содержимое поля соответстувет выбору в селекте",
                "+7 "+ "("+ person.telephoneName.substring(0,3)+") " + person.telephoneName.substring(3,6) +"-"+ person.telephoneName.substring(6,8) +"-"+ person.telephoneName.substring(8,10)
                , telephone.getAttribute("value"));
        Assert.assertEquals("Содержимое поля соответстувет выбору в селекте",
                "qwertyqwerty", email.getAttribute("value"));
        Assert.assertEquals("Содержимое поля соответстувет выбору в селекте",
                "Комментарий", comment.getAttribute("value"));
    }
}


