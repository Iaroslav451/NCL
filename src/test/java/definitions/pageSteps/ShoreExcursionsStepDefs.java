package definitions.pageSteps;

import support.Page;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class ShoreExcursionsStepDefs extends Page {

    //Selectors//

    @FindBy(xpath = "//div[@id='search_destinations_chosen']")
    private WebElement mainPageDestination;

    @FindBy(xpath = "//li[contains(text(),'Alaska Cruises')]")
    private WebElement alaskaCruises;

    @FindBy(xpath = "//button[@class='btn-cta btn-primary btn-large search-submit']")
    private WebElement findExcursions;

    @FindBy(xpath = "//a[@title='Port']")
    private WebElement port;

    @FindBy(xpath = "//div[@id='explore_resultperpage_chosen']")
    private WebElement resultPerPage;

    @FindBy(xpath = "//li[@data-option-array-index='3']")
    private WebElement resultPerPage48;

    @FindBy(xpath = "//span[@class='legend-column extremes']")
    private WebElement legendColumnExtremes;

    //Methods//

    public String getRangePrice() {
        return legendColumnExtremes.getText()
                .replace("$0", "")
                .replace("$", "")
                .replace("-", "");
    }

    public void selectResultsPerPage48() {
        resultPerPage.click();
        resultPerPage48.click();
    }

    private void clickDestination() {
        mainPageDestination.click();
    }

    public void clickPort() {
        port.click();
    }

    public void selectAlaskaCruises() {
        clickDestination();
        alaskaCruises.click();
    }

    public void clickFindExcursions() {
        findExcursions.click();
    }


}
