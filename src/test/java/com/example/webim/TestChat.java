package com.example.webim;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.concurrent.TimeUnit;

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

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        //test opening chat
        chatPage.open();
        String windowMainPageHandle = driver.getWindowHandle();
        chatPage.openChat();
        driver.switchTo().window(
                driver.getWindowHandles().stream()
                        .filter(o -> !o.equals(windowMainPageHandle))
                .findAny().orElseThrow(() -> new IllegalStateException("Chat window was not opened!"))
        );
        String windowChatPageHandle = driver.getWindowHandle();
        assertEquals( "Онлайн-консультант", driver.getTitle());

        //test sending text messages
        String msg = RandomStringUtils.randomAlphanumeric(RandomUtils.nextInt(3, 11));


        chatPage.addText(msg);
        chatPage.sendMessage();
        assertEquals(msg, chatPage.getLastMessageText());

        //test blank messages
        chatPage.addText("");
        chatPage.sendMessage();
        assertNotEquals("", chatPage.getLastMessageText());

        //test sending img
        chatPage.addFile("resources\\pic.jpg");
        assertEquals("Отправил(а) файл pic.jpg", chatPage.getLastMessageText());

        //test rating operator
        switchToPage(windowMainPageHandle, "Main window was not found!");
        chatPage.addOperator(msg);
        switchToPage(windowChatPageHandle, "Chat window was not found!");
        chatPage.rateOperator();
        assertFalse(chatPage.getSendRateButton().isDisplayed());

        //test closing chat
        chatPage.closeChat();

    }

    private void switchToPage(String windowMainPageHandle, String s) {
        driver.switchTo().window(
                driver.getWindowHandles().stream()
                        .filter(windowMainPageHandle::equals)
                        .findAny().orElseThrow(() -> new IllegalStateException(s))
        );
    }

    @After
    public void tearDown() throws Exception {
        driver.close();
        driver.quit();
    }


}
