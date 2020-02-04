package definitions.pageSteps;

import support.Page;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import static support.TestContext.driver;

public class LogInHomepageStepDefs extends Page {

    //Selectors//

    @FindBy (xpath = "//input[@id='userName']")
    private WebElement username;

    @FindBy(xpath = "//input[@id='password']")
    private WebElement password;

    @FindBy (xpath = "//button[@id='login_submit']")
    private WebElement logIn;

    @FindBy (xpath = "//a[@title='Explore']")
    private WebElement explore;

    @FindBy (xpath = "//div[@class='drophover_menu_content']//a[@title='Ports']")
    private WebElement ports;

    @FindBy (xpath = "//li//a[@title='Shore Excursions']")
    private WebElement shoreExcursion;


    //Methods//

    public void fillUsername (String value){
        username.sendKeys(value);
    }

    public void fillPassword (String value){
        password.sendKeys(value);
    }

    public void clickLogIn (){
        logIn.click();
    }

    //Tab Explore//

    private void performExplore (){
        new Actions(driver()).moveToElement(explore).perform();
    }

    public void explorePorts(){
        performExplore();
        ports.click();
    }

    public void exploreShoreExcursion(){
        performExplore();
        shoreExcursion.click();
    }


}
