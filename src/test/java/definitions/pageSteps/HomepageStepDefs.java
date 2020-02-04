package definitions.pageSteps;


import support.Page;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomepageStepDefs extends Page {

    //Selectors//

    @FindBy (xpath = "//a[@title='Log in']")
    private WebElement logIn;



    //Methods//

    public void clickLogIn() {
        logIn.click();
    }


}

