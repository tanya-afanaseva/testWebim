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

    public ChatPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public void open() {
        webDriver.get(URL);
    }

    public void openChat() {
        webDriver.findElement(By.xpath("/html/body/div[1]/div[1]/div/div/div/div/a[3]")).click();

    }

    public void closeChat() {
        webDriver.close();
        webDriver.switchTo().window(webDriver.getWindowHandles().stream().findAny().get());
    }

    public void addText(String msg) {
        WebElement element = webDriver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/div/div[1]/div[3]/div[1]/div[2]/div[1]/div[5]/div/div[1]"));
        JavascriptExecutor executor = (JavascriptExecutor) webDriver;
        executor.executeScript("arguments[0].textContent= arguments[1];", element, msg);
    }

    public void sendMessage() {
        webDriver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/div/div[1]/div[3]/div[1]/div[2]/div[1]/div[5]/button[2]")).click();

    }

    public String getLastMessageText(){
        List<WebElement> messages = webDriver.findElements(By.xpath("/html/body/div[1]/div[2]/div/div[2]/div/div[1]/div[3]/div[1]/div[1]/div[2]/div[1]/div[2]/div/div[2]/ul/li[*]"));
        WebElement message = Lists.reverse(messages).get(0);
        return message.findElement(By.xpath("./div[4]/span")).getText();
    }

    public void addFile(String path) {
       webDriver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/div/div[1]/div[3]/div[1]/div[2]/div[1]/div[5]/button[1]")).click();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement fileButton = webDriver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/div/div[1]/div[3]/div[1]/div[2]/div[2]/ul/li[2]/label"));
        fileButton.click();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        fileButton.findElement(By.xpath("./input")).sendKeys(path);

    }
}
