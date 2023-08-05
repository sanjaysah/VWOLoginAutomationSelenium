package com.thetestingacademy;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.time.Duration;

public class VWOLogin {

    ChromeOptions options;
    WebDriver driver;
    WebDriverWait wait;

    @BeforeSuite
    public void setUp(){
        options = new ChromeOptions();
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    @Test(enabled = true,priority = 1,groups = {"sanity","negative"})
    @Severity(SeverityLevel.BLOCKER)
    @Description("TC#1-Invalid Username and Password, login Unsuccessfull!!")
    public void invalidLoginTest() throws InterruptedException {
        driver.get("https://app.vwo.com");
        driver.findElement(By.id("login-username")).sendKeys("93npu2yyb0@esiix1.com");
        driver.findElement(By.id("login-password")).sendKeys("Wingify@1231");
        driver.findElement(By.id("js-login-btn")).click();
        //Thread.sleep(2000);
        //Explicit wait
        WebElement errorMess = driver.findElement(By.id("js-notification-box-msg"));
        wait.until(ExpectedConditions.visibilityOf(errorMess));
        //Assertion
        String actual = driver.findElement(By.id("js-notification-box-msg")).getText();
        String expected = "Your email, password, IP address or location did not match";
        Assert.assertEquals(actual,expected);
    }

    @Test(enabled = true,priority = 2,groups = {"sanity", "positive"})
    @Severity(SeverityLevel.BLOCKER)
    @Description("TC#1-Valid Username and Password, login Successfull!!")
    public void validLoginTest() throws InterruptedException {
        driver.get("https://app.vwo.com");
        driver.findElement(By.id("login-username")).sendKeys("93npu2yyb0@esiix.com");
        driver.findElement(By.id("login-password")).sendKeys("Wingify@123");
        driver.findElement(By.id("js-login-btn")).click();
        //Thread.sleep(4000);
        //Explicit wait
        WebElement welcomeMess = driver.findElement(By.xpath("//span[normalize-space()='Need Help?']"));
        wait.until(ExpectedConditions.elementToBeClickable(welcomeMess));
        //WebElement welcomeMess = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//span[normalize-space()='Need Help?']"))));
        //Assertion
        String actual = driver.getCurrentUrl();
        String expected = "https://app.vwo.com/#/dashboard";
        Assert.assertEquals(actual,expected);

        //driver.get("https://app.vwo.com/logout");
    }

    @AfterSuite
    public void tearDown(){
        driver.close();
    }
}
