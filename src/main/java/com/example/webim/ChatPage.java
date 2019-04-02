package com.example.webim;

import com.google.common.collect.Lists;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


import java.util.List;

public class ChatPage {

    private final String URL = "https://demo-pro.webim.ru";

    private WebDriver webDriver;
    private static final String  MESSAGE_AREA_PATH = "/html/body/div[1]/div[2]/div/div[2]/div/div[1]/div[3]/div[1]/div[2]/div[1]/div[5]/div/div[1]";
    private static final String OPEN_CHAT_BUTTON_PATH = "/html/body/div[1]/div[1]/div/div/div/div/a[3]";
    private static final String SEND_MESSAGE_BUTTON_PATH = "/html/body/div[1]/div[2]/div/div[2]/div/div[1]/div[3]/div[1]/div[2]/div[1]/div[5]/button[2]";
    private static final String LAST_MESSAGE_PATH = "/html/body/div[1]/div[2]/div/div[2]/div/div[1]/div[3]/div[1]/div[1]/div[2]/div[1]/div[2]/div/div[2]/ul/li[*]";
    private static final String ADD_FILE_BUTTON_PATH = "/html/body/div[1]/div[2]/div/div[2]/div/div[1]/div[3]/div[1]/div[2]/div[2]/ul/li[2]/label";
    private static final String LOGIN_AREA_PATH= "//*[@id=\"login_or_email\"]";
    private static final String PASSWORD_AREA_PATH = "//*[@id=\"password\"]";
    private static final String LOGIN_BUTTON_PATH = "/html/body/div[1]/div[1]/section/div[1]/form/div[6]/div/button";
    private static final String ALL_MESSAGES_BUTTON_PATH = "/html/body/header/nav/div[2]/ul/li[1]/a/span[3]";
    private static final String MESSAGE_AREA_OPERATOR_PATH = "/html/body/div[1]/div[2]/div/section[6]/div/div[1]/div/div/div[1]/div[5]/div[4]/div/textarea";
    private static final String RATE_BUTTON_PATH = "/html/body/div[1]/div[2]/div/div[2]/div/div[1]/div[3]/div[1]/div[2]/div[2]/ul/li[4]/span";
    private static final String SCORE_BUTTON_PATH = "/html/body/div[1]/div[2]/div/div[2]/div/div[2]/div[2]/div/div[2]/div/ul/li[5]";
    private static final String MENU_BUTTON_PATH = "/html/body/div[1]/div[2]/div/div[2]/div/div[1]/div[3]/div[1]/div[2]/div[1]/div[5]/button[1]";
    private static final String SEND_BUTTON_PATH = "/html/body/div[1]/div[2]/div/div[2]/div/div[2]/div[2]/div/div[2]/div/div[3]/button";

    public ChatPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public void open() {
        webDriver.get(URL);
    }

    public void openChat() {

        webDriver.findElement(By.xpath(OPEN_CHAT_BUTTON_PATH)).click();

    }

    public void closeChat() {
        webDriver.close();
        webDriver.switchTo().window(webDriver.getWindowHandles().stream().findAny().get());
    }

    public void addText(String msg) {
        WebElement element = webDriver.findElement(By.xpath(MESSAGE_AREA_PATH));
        JavascriptExecutor executor = (JavascriptExecutor) webDriver;
        executor.executeScript("arguments[0].textContent= arguments[1];", element, msg);
    }

    public void sendMessage() {
        webDriver.findElement(By.xpath(SEND_MESSAGE_BUTTON_PATH)).click();

    }

    public String getLastMessageText(){
        List<WebElement> messages = webDriver.findElements(By.xpath(LAST_MESSAGE_PATH));
        WebElement message = Lists.reverse(messages).get(0);
        return message.findElement(By.xpath("./div[4]/span")).getText();
    }

    public void addFile(String path) {
        openMenu();
        WebElement fileButton = webDriver.findElement(By.xpath(ADD_FILE_BUTTON_PATH));
        fileButton.click();
        fileButton.findElement(By.xpath("./input")).sendKeys(path);

    }
    public void addOperator(String msg){

        webDriver.get("http://demo.webim.ru/webim/");
        webDriver.findElement(By.xpath(LOGIN_AREA_PATH)).sendKeys("o@webim.ru");
        webDriver.findElement(By.xpath(PASSWORD_AREA_PATH)).sendKeys("password");
        webDriver.findElement(By.xpath(LOGIN_BUTTON_PATH)).click();
        webDriver.findElement(By.xpath(ALL_MESSAGES_BUTTON_PATH)).click();
        webDriver.findElement(By.xpath("/html/body/div[1]/div[1]/section/div[1]/ul/li[//text()[contains(.,'"+msg+"')]]")).click();
        webDriver.findElement(By.xpath(MESSAGE_AREA_OPERATOR_PATH)).sendKeys("123");

    }

    public void rateOperator(){
        openMenu();
        WebElement rateButton = webDriver.findElement(By.xpath(RATE_BUTTON_PATH));
        rateButton.click();
        webDriver.findElement(By.xpath(SCORE_BUTTON_PATH)).click();
        getSendRateButton().click();
    }

    public void openMenu() {
        webDriver.findElement(By.xpath(MENU_BUTTON_PATH)).click();
    }

    public WebElement getSendRateButton() {
        return webDriver.findElement(By.xpath(SEND_BUTTON_PATH));
    }


}
