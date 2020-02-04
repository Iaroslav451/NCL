package definitions.cucumberSteps;


import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import support.Page;
import definitions.pageSteps.HomepageStepDefs;
import definitions.pageSteps.LogInHomepageStepDefs;
import definitions.pageSteps.PortOfCallStepDefs;
import definitions.pageSteps.ShoreExcursionsStepDefs;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.assertj.core.api.Assertions.assertThat;

import static support.TestContext.driver;

public class TestScenariosSteps extends Page {

    PortOfCallStepDefs portOfCall = new PortOfCallStepDefs();
    HomepageStepDefs homepage = new HomepageStepDefs();
    LogInHomepageStepDefs logIn = new LogInHomepageStepDefs();
    ShoreExcursionsStepDefs shoreExcursions = new ShoreExcursionsStepDefs();
    WebDriverWait wait = new WebDriverWait(driver(), 30);

    @Given("a Guest")
    public void aGuest() {
        homepage.clickLogIn();
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//input[@id='userName']")));
        logIn.fillUsername("IaroslavSlav");
        logIn.fillPassword("Momo_451");
        logIn.clickLogIn();
    }

    @And("I am on Homepage")
    public void iAmOnHomepage() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@data-js='logged-in-first-name']")));
        String value = driver().getCurrentUrl();
        assertThat(value).isEqualTo("https://www.ncl.com/?login_method=ncl&login_source=booked%20guest&login_status=success");
    }

    @And("I navigated to {string} page")
    public void iNavigatedToPage(String value) {
        switch (value) {
            case "Ports":
                logIn.explorePorts();
                break;
            case "Shore Excursion":
                logIn.exploreShoreExcursion();
                break;
            default:
                System.out.println("404 Unknown actions: " + value);
        }
    }

    @When("I search for {string} port")
    public void iSearchForPort(String value) {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//input[@id='searchbar']")));
        driver().findElement(By.xpath("//input[@id='searchbar']")).isEnabled();
        switch (value) {
            case "Honolulu":
                driver().findElement(By.xpath("//input[@id='searchbar']")).isEnabled();
                portOfCall.searchSendKeys("Honolulu");
                driver().findElement(By.xpath("//a[@title='Honolulu, Oahu']")).click();
                break;
            default:
                System.out.println("404 Unknown actions: " + value);
        }
    }

    @Then("Map zoomed to show selected port")
    public void mapZoomedToShowSelectedPort() {
        assertThat(driver().findElement(By.xpath("//div[@style='position: absolute; overflow: hidden; opacity: 0.01; margin-left: 15px; margin-top: -27px; left: -1901px; top: -252px; z-index: -251; display: none;']")).isEnabled());
    }

    @And("Port displayed as {string}")
    public void portDisplayedAs(String value) {
        switch (value) {
            case "Port Of Departure":
                assertThat(driver().findElement(By.xpath("//div[@style='width: 32px; height: 37px; overflow: hidden; position: absolute; opacity: 0; cursor: pointer; touch-action: none; left: -16px; top: -37px; z-index: 0;']//img[@src='/resources/images/icons/pin-port-of-departure.png']")).isEnabled());
                break;
            default:
                System.out.println("404 Unknown actions: " + value);
        }
    }

    @When("I search for destination {string}")
    public void iSearchForDestination(String value) {
        switch (value) {
            case "Alaska Cruises":
                shoreExcursions.selectAlaskaCruises();
                break;
            default:
                System.out.println("404 Unknown actions: " + value);
        }
    }

    @Then("Shore Excursions page is present")
    public void shoreExcursionsPageIsPresent() {
        assertThat(driver().getCurrentUrl()).contains("https://www.ncl.com/shore-excursions");
    }

    @And("I click Find Excursions")
    public void iClickFindExcursions() {
        shoreExcursions.clickFindExcursions();
    }

    @And("Results are filtered by {string}")
    public void resultsAreFilteredBy(String value) {
        switch (value) {
            case "Alaska Cruises":
                assertThat(driver().findElement(By.xpath("//span[@class='items-text']")).equals("Alaska Cruises"));
                break;
            default:
                System.out.println("404 Unknown actions: " + value);
        }
    }

    @And("Filter By Ports are only belong to {string}")
    public void filterByPortsAreOnlyBelongTo(String value) {
        switch (value) {
            case "Alaska, British Columbia":
                shoreExcursions.clickPort();
                String ports = driver().findElement(By.xpath("//ul[@id='ports']")).getText();
                assertThat(ports).isEqualTo("Icy Strait Point, Alaska\n" +
                        "Juneau, Alaska\n" +
                        "Ketchikan, Alaska\n" +
                        "Seward, Alaska\n" +
                        "Sitka, Alaska\n" +
                        "Skagway, Alaska\n" +
                        "Victoria, British Columbia");
                break;
            default:
                System.out.println("404 Unknown actions: " + value);
        }
    }

    @When("Price Range is filtered to {string}")
    public void priceRangeIsFilteredTo(String value) {
        String newValue = value.replace("$", "").replace("-", "+");
        String newurl = "https://www.ncl.com/shore-excursions/search?sort=searchWeight&perPage=12&priceRange=" + newValue;
        driver().navigate().to(newurl);
        assertThat(value).isEqualTo(driver().findElement(By.xpath("//span[@class='legend-column extremes']")).getText());
    }

    @Then("Only shore excursions within range are displayed")
    public void onlyShoreExcursionsWithinRangeAreDisplayed() {
        shoreExcursions.selectResultsPerPage48();
        String rangePrice = driver().findElement(By.xpath("//span[@class='legend-column extremes']")).getText();
        String updRangePrice = rangePrice.replace("$0", "").replace("$", "").replace("-", "");
        //Assertion for list-holders //I know this is a mess
        //list 1_____!___________________________________________________________________________!_____Copy past and replace numbers__________
        String holder1 = driver().findElement(By.xpath("//ul[@class='holders-list ng-scope']//li[1]//article//div//div[@class='price-different clearfix']//ul//li//strong"))
                .getText()
                .replace("$", "").replace("USD", "").replace(".00", "").replace(" ", "");
        if (holder1.equals(holder1)) {
            assertThat(holder1).isLessThanOrEqualTo(updRangePrice);
            System.out.println("No Errors in: " + holder1);
        } else {
            System.out.println("Error in: " + holder1);
        }
        //list 2_____!___________________________________________________________________________!_____
        String holder2 = driver().findElement(By.xpath("//ul[@class='holders-list ng-scope']//li[2]//article//div//div[@class='price-different clearfix']//ul//li//strong"))
                .getText()
                .replace("$", "").replace("USD", "").replace(".00", "").replace(" ", "");
        if (holder2.equals(holder2)) {
            assertThat(holder2).isLessThanOrEqualTo(updRangePrice);
            System.out.println("No Errors in: " + holder2);
        } else {
            System.out.println("Error in: " + holder2);
        }
        //list 3_____!___________________________________________________________________________!_____
        String holder3 = driver().findElement(By.xpath("//ul[@class='holders-list ng-scope']//li[3]//article//div//div[@class='price-different clearfix']//ul//li//strong"))
                .getText()
                .replace("$", "").replace("USD", "").replace(".00", "").replace(" ", "");
        if (holder3.equals(holder3)) {
            assertThat(holder3).isLessThanOrEqualTo(updRangePrice);
            System.out.println("No Errors in: " + holder3);
        } else {
            System.out.println("Error in: " + holder3);
        }
        //list 4_____!___________________________________________________________________________!_____
        String holder4 = driver().findElement(By.xpath("//ul[@class='holders-list ng-scope']//li[4]//article//div//div[@class='price-different clearfix']//ul//li//strong"))
                .getText()
                .replace("$", "").replace("USD", "").replace(".00", "").replace(" ", "");
        if (holder4.equals(holder4)) {
            assertThat(holder4).isLessThanOrEqualTo(updRangePrice);
            System.out.println("No Errors in: " + holder4);
        } else {
            System.out.println("Error in: " + holder4);
        }
        //list 5_____!___________________________________________________________________________!_____
        String holder5 = driver().findElement(By.xpath("//ul[@class='holders-list ng-scope']//li[5]//article//div//div[@class='price-different clearfix']//ul//li//strong"))
                .getText()
                .replace("$", "").replace("USD", "").replace(".00", "").replace(" ", "");
        if (holder5.equals(holder5)) {
            assertThat(holder5).isLessThanOrEqualTo(updRangePrice);
            System.out.println("No Errors in: " + holder5);
        } else {
            System.out.println("Error in: " + holder5);
        }
        //list 6_____!___________________________________________________________________________!_____
        String holder6 = driver().findElement(By.xpath("//ul[@class='holders-list ng-scope']//li[6]//article//div//div[@class='price-different clearfix']//ul//li//strong"))
                .getText()
                .replace("$", "").replace("USD", "").replace(".00", "").replace(" ", "");
        if (holder6.equals(holder6)) {
            assertThat(holder6).isLessThanOrEqualTo(updRangePrice);
            System.out.println("No Errors in: " + holder6);
        } else {
            System.out.println("Error in: " + holder6);
        }


    }
}

