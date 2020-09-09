package com.customized.lib.web.webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.concurrent.TimeUnit;


@SuppressWarnings("all")
public class AutoLogin2CSDN {

    public static void main(String[] args) {

        System.setProperty("webdriver.chrome.driver", "/Users/yan/Downloads/chromedriver");// chromedriver服务地址
        WebDriver driver = new ChromeDriver(); // 新建一个WebDriver 的对象，但是new 的是谷歌的驱动
        String url = "https://passport.csdn.net/login?code=public";
        driver.get(url); // 打开指定的网站

        //driver.navigate().to(url); // 打开指定的网站        

        /*
         *
         * driver.findElement(By.id("kw")).sendKeys(new String[] { "hello" });//
         * 找到kw元素的id，然后输入hello driver.findElement(By.id("su")).click(); // 点击按扭
         */
        try {
            /**
             * WebDriver自带了一个智能等待的方法。 dr.manage().timeouts().implicitlyWait(arg0, arg1）；
             * Arg0：等待的时间长度，int 类型 ； Arg1：等待时间的单位 TimeUnit.SECONDS 一般用秒作为单位。
             */
            driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            //获取当前浏览器的信息
            System.out.println("Title:" + driver.getTitle());
            System.out.println("currentUrl:" + driver.getCurrentUrl());

            // 基于xpath的定位，很好理解，按照实际的doc文档结构即可
            WebElement login = driver.findElement(By.xpath("//*[@class='main-select']/ul/li[2]/a"));

            /**
             * 就是将bai鼠标移动到element元素上，注意光标不会du移动，但是element元素上会出现鼠标移上去的效果。
             * .build().perform()可以简单理解为执行的action的意思。
             */
            // Mouse Move
            Actions move = new Actions(driver);
            move.moveToElement(login).perform();

            Thread.sleep(1000);

            // Click
            Actions click = new Actions(driver);
            click.click(login).perform();


            // 用户名+密码+提交表单
            WebElement username = driver.findElement(By.id("all"));
            username.click();
            username.sendKeys("yanlun0323");

            Thread.sleep(100);

            WebElement password = driver.findElement(By.id("password-number"));
            password.click();
            password.sendKeys("//");

            Thread.sleep(100);

            WebElement formSubmit = driver.findElement(By.className("form-submit"));

            Actions move2Submit = new Actions(driver);
            move2Submit.moveToElement(formSubmit).perform();
            Thread.sleep(100);

            // formSubmit.findElement(By.tagName("button")).click();

            Thread.sleep(Integer.MAX_VALUE);
        } catch (Exception ex) {
            ex.printStackTrace();
            driver.quit();
        }

        /**
         * dr.quit()和dr.close()都可以退出浏览器,简单的说一下两者的区别：第一个close，
         * 如果打开了多个页面是关不干净的，它只关闭当前的一个页面。第二个quit，
         * 是退出了所有Webdriver所有的窗口，退的非常干净，所以推荐使用quit最为一个case退出的方法。
         */
    }

}