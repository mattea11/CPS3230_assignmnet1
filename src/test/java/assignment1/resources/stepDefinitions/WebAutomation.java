package assignment1.resources.stepDefinitions;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import assignment1.itemInfo;
import assignment1.postApi;
import assignment1.task2;
import assignment1.webScrap;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class WebAutomation {
    webScrap ws = new webScrap();
    postApi pa = new postApi();
    task2  t2 = new task2();
    public WebDriver driver = null;
    WebElement table = null;

    @Given("I am a user of marketalertum")
    public void iAmAUserOfMarketalertum() {
        driver = t2.accessMarketUm(driver);
    }

    @When("I login using valid credentials")
    public void iLoginUsingValidCredentials() {
        driver = t2.goToLogIn(driver, "7f84a00a-eeac-47fa-b15c-ee7e7ff9378d");
    }

    @Then("I should see my alerts")
    public void iShouldSeeMyAlerts() throws IOException {
        assertTrue(driver.findElement(By.tagName("h1")).isDisplayed());
        pa.deleteAlerts();

        driver.close();
        driver = null;
    }

    @When("I login using invalid credentials")
    public void iLoginUsingInvalidCredentials() {
        driver = t2.goToLogIn(driver, "Invalidcredentials");
    }

    @Then("I should see the login screen again")
    public void iShouldSeeTheLoginScreenAgain() {
        assertEquals("User ID:", driver.findElement(By.tagName("b")).getText());
        driver.close();
        driver = null;
    }

    @Given("I am an administrator of the website and I upload {int} alerts")
    public void iAmAnAdministratorOfTheWebsiteAndIUploadAlerts(int arg0) throws JSONException, IOException {

        List<itemInfo> items = new ArrayList<>();
        List<String> alerts = new ArrayList<>();

        pa.deleteAlerts(); //clear alerts to ensure that the amount of alerts posted equates to the actual amount we wished to post

        driver = ws.setUpDriver(driver);
        driver = ws.searchItem(driver, "headphones");
        items = ws.QueryItemInfo(driver, arg0);

        alerts = pa.createJsonObject(items,6);

        for (int i = 0; i < alerts.size(); i++) {
            pa.postAlert(alerts.get(i));
        }
        assertEquals(arg0, alerts.size()-1);

        driver.close();
        driver = null;
    }

    @When("I view a list of alerts")
    public void iViewAListOfAlerts() {
        iLoginUsingValidCredentials();
        table = driver.findElement(By.className("pb-3"));
        assertNotEquals(null, table);
    }

    @Then("each alert should contain an icon")
    public void eachAlertShouldContainAnIcon() {
        for (int i = 1; i <= 3; i++) {
            assertNotEquals(null, driver.findElement(By.xpath("/html/body/div/main/table["+i+"]/tbody/tr[1]/td/h4/img")));
        }
    }

    @Then("each alert should contain a heading")
    public void each_alert_should_contain_a_heading() {
        for (int i = 1; i <= 3; i++) {
            assertNotEquals(null, driver.findElement(By.xpath("/html/body/div/main/table[1]/tbody/tr[1]/td/h4/img")).getText());
        }
    }

    @Then("each alert should contain a description")
    public void each_alert_should_contain_a_description() {
        for (int i = 1; i <= 3; i++) {
            assertNotEquals(null, driver.findElement(By.xpath("/html/body/div/main/table["+i+"]/tbody/tr[3]/td")).getText());
        }
    }

    @Then("each alert should contain a price")
    public void each_alert_should_contain_a_price() {
        for (int i = 1; i <= 3; i++) {
            assertNotEquals(null, driver.findElement(By.xpath("/html/body/div/main/table["+i+"]/tbody/tr[4]/td/b")).getText());
        }
    }

    @Then("each alert should contain an image")
    public void each_alert_should_contain_an_image() {
        for (int i = 1; i <= 3; i++) {
            assertNotEquals(null, driver.findElement(By.xpath("/html/body/div/main/table["+i+"]/tbody/tr[2]/td/img")));
        }
    }

    @Then("each alert should contain a link to the original product website")
    public void each_alert_should_contain_a_link_to_the_original_product_website() throws IOException {
        for (int i = 1; i <= 3; i++) {
            assertNotEquals(null, driver.findElement(By.xpath("/html/body/div/main/table["+i+"]/tbody/tr[5]/td/a")));
        }
        pa.deleteAlerts();
        driver.close();
        driver = null;
    }

    @Given("I am an administrator of the website and I upload more than {int} alerts")
    public void iAmAnAdministratorOfTheWebsiteAndIUploadMoreThanAlerts(int arg0) throws JSONException, IOException {

        List<itemInfo> items = new ArrayList<>();
        List<String> alerts = new ArrayList<>();

        pa.deleteAlerts();

        driver = ws.setUpDriver(driver);
        driver = ws.searchItem(driver, "headphones");
        items = ws.QueryItemInfo(driver, arg0);

        alerts = pa.createJsonObject(items,6);

        for (int i = 0; i < alerts.size(); i++) {
            pa.postAlert(alerts.get(i));
        }
        assertEquals(arg0, alerts.size()-1);
        driver.close();
        driver = null;
    }

    @Then("I should see {int} alerts")
    public void iShouldSeeAlerts(int arg0) throws IOException {
        assertEquals(arg0, driver.findElements(By.tagName("table")).size());
    }

    @Given("^I am an administrator of the website and I upload an alert of type? ([0-9]+)")
    public void iAmAnAdministratorOfTheWebsiteAndIUploadAnAlertOfTypeAlertType(int alertType) throws JSONException, IOException {
        
        List<itemInfo> items = new ArrayList<>();
        List<String> alerts = new ArrayList<>();

        pa.deleteAlerts(); //remove all previous alerts

        driver = ws.setUpDriver(driver);
        driver = ws.searchItem(driver, "headphones");
        items = ws.QueryItemInfo(driver, 0);

        alerts = pa.createJsonObject(items, alertType);

        for (int i = 0; i < alerts.size(); i++) {
            pa.postAlert(alerts.get(i));
        }

        assertEquals(1, alerts.size());

        driver.close();
        driver = null;
    }

    @Then("^The icon displayed should be (.+)")
    public void the_icon_displayed_should_be_file_icon_file_name(String iconFileName) throws IOException {
        String iconFile = driver.findElement(By.cssSelector("body > div > main > table:nth-child(2) > tbody > tr:nth-child(1) > td > h4 > img")).getAttribute("src");
        String[] cleanedFileName = iconFile.split("/");
        assertEquals(iconFileName, cleanedFileName[4]); 

        driver.close();
        driver = null;
    }

}
