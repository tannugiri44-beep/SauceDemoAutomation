
package com.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest {

    // Common login method
    public void login(WebDriver driver) {
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
    }

    // Test 1: Valid Login
    @Test
    public void validLoginTest() {
        WebDriver driver = new ChromeDriver();
        try {
            driver.manage().window().maximize();
            login(driver);

            Assert.assertTrue(driver.getCurrentUrl().contains("inventory"));
            System.out.println("Valid Login Test Passed!");
        } finally {
            driver.quit();
        }
    }

    // Test 2: Invalid Login
    @Test
    public void invalidLoginTest() {
        WebDriver driver = new ChromeDriver();
        try {
            driver.manage().window().maximize();
            driver.get("https://www.saucedemo.com/");

            driver.findElement(By.id("user-name")).sendKeys("wrong_user");
            driver.findElement(By.id("password")).sendKeys("wrong_password");
            driver.findElement(By.id("login-button")).click();

            String error = driver.findElement(
                By.cssSelector("[data-test='error']")
            ).getText();

            Assert.assertTrue(error.contains("Username and password"));
            System.out.println("Invalid Login Test Passed!");
        } finally {
            driver.quit();
        }
    }

    // Test 3: Add to Cart
    @Test
    public void addToCartTest() {
        WebDriver driver = new ChromeDriver();
        try {
            driver.manage().window().maximize();
            login(driver);

            driver.findElement(
                By.id("add-to-cart-sauce-labs-backpack")
            ).click();

            String count = driver.findElement(
                By.className("shopping_cart_badge")
            ).getText();

            Assert.assertEquals(count, "1");
            System.out.println("Add To Cart Test Passed!");
        } finally {
            driver.quit();
        }
    }

    // Test 4: Remove from Cart
    @Test
    public void removeFromCartTest() {
        WebDriver driver = new ChromeDriver();
        try {
            driver.manage().window().maximize();
            login(driver);

            driver.findElement(
                By.id("add-to-cart-sauce-labs-backpack")
            ).click();

            driver.findElement(By.className("shopping_cart_link")).click();

            driver.findElement(
                By.id("remove-sauce-labs-backpack")
            ).click();

            Assert.assertTrue(
                driver.findElements(By.className("cart_item")).isEmpty()
            );

            System.out.println("Remove From Cart Test Passed!");
        } finally {
            driver.quit();
        }
    }

    // Test 5: Complete Checkout
    @Test
    public void checkoutTest() {
        WebDriver driver = new ChromeDriver();
        try {
            driver.manage().window().maximize();
            login(driver);

            driver.findElement(
                By.id("add-to-cart-sauce-labs-backpack")
            ).click();

            driver.findElement(By.className("shopping_cart_link")).click();
            driver.findElement(By.id("checkout")).click();

            driver.findElement(By.id("first-name")).sendKeys("Tannu");
            driver.findElement(By.id("last-name")).sendKeys("Giri");
            driver.findElement(By.id("postal-code")).sendKeys("247667");

            driver.findElement(By.id("continue")).click();
            driver.findElement(By.id("finish")).click();

            String message = driver.findElement(
                By.className("complete-header")
            ).getText();

            Assert.assertTrue(message.contains("Thank you"));

            System.out.println("Checkout Test Passed!");
        } finally {
            driver.quit();
        }
    }
}

