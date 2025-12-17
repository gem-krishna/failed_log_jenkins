package org.example.stepdefs;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileWriter;
import java.io.IOException;

public class GoogleSearchSteps {
    private WebDriver driver;
    private String scenarioName;

    @Given("I am on the Google search page")
    public void i_am_on_the_google_search_page() {
        driver = new ChromeDriver();
        driver.get("https://www.google.com");
    }

    @When("I search for {string}")
    public void i_search_for(String query) {
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys(query);
        searchBox.submit();
    }

    @Then("the page title should contain {string}")
    public void the_page_title_should_contain(String expected) {
        try {
            Thread.sleep(2000); // Wait for results
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String title = driver.getTitle();
        try {
            Assert.assertFalse(title.contains(expected));
        } catch (AssertionError e) {
            logFailure("Expected title to contain: " + expected + ", but was: " + title);
            throw e;
        }
    }


    @After
    public void tearDown(io.cucumber.java.Scenario scenario) {
        if (driver != null) {
            driver.quit();
        }
        this.scenarioName = scenario.getName();
        if (scenario.isFailed()) {
            logFailure("Scenario failed: " + scenarioName);
        }
    }

    private void logFailure(String message) {
        try (FileWriter fw = new FileWriter("failed_log.txt", true)) {
            fw.write(message + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

