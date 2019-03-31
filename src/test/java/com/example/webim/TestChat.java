package com.example.webim;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.Assert.*;

public class TestChat {

    private WebDriver driver;
    private ChatPage chatPage;


    @Before
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "target/test-classes/chromedriver.exe");
        driver = new ChromeDriver();
        chatPage = new ChatPage(driver);
    }

    @Test
    public void testSurvey() throws Exception {
        chatPage.open();
        Thread.sleep(5000);
        chatPage.openChat();
        driver.switchTo().window(
                driver.getWindowHandles().stream()
                        .filter(o -> !o.equals(driver.getWindowHandle()))
                .findAny().orElseThrow(() -> new IllegalStateException("Chat window was not opened!"))
        );
        assertEquals( "Онлайн-консультант", driver.getTitle());

        Thread.sleep(15000);
        String msg = RandomStringUtils.randomAlphanumeric(RandomUtils.nextInt(3, 11));
        chatPage.addText(msg);
        chatPage.sendMessage();
        Thread.sleep(3000);
        assertEquals(msg, chatPage.getLastMessageText());

        chatPage.addText("");
        chatPage.sendMessage();
        Thread.sleep(3000);
        assertNotEquals("", chatPage.getLastMessageText());

        chatPage.addFile("D:\\webim\\src\\test\\resources\\pic.jpg");
        Thread.sleep(3000);

        assertEquals("Отправил(а) файл pic.jpg", chatPage.getLastMessageText());

        chatPage.closeChat();
        assertEquals("https://demo-pro.webim.ru/", driver.getCurrentUrl());

    }

    @After
    public void tearDown() throws Exception {
        driver.close();
        driver.quit();
    }


}
