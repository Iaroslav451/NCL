package definitions.pageSteps;

import support.Page;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class PortOfCallStepDefs extends Page {

    //Selectors//

    @FindBy (xpath = "//input[@id='searchbar']")
    private WebElement searchTab;

    //Methods//

    public void searchSendKeys (String value){
        searchTab.sendKeys(value);
    }





}
