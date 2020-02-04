package support;

import org.openqa.selenium.support.PageFactory;

import static support.TestContext.driver;

public class Page {

    private String url;

    public Page() {
        PageFactory.initElements(driver(), this);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void open() {
        driver().get(url);
    }
}
