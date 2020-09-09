package com.customized.lib.web.webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.concurrent.TimeUnit;


@SuppressWarnings("all")
public class DoubleClickBaiduNews {

    public static void main(String[] args) {

        System.setProperty("webdriver.chrome.driver", "/Users/yan/Downloads/chromedriver");// chromedriver服务地址
        WebDriver driver = new ChromeDriver(); // 新建一个WebDriver 的对象，但是new 的是谷歌的驱动
        String url = "https://www.baidu.com";
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

            //定位百度首页右上角  新闻
            WebElement Xw = driver.findElement(By.xpath("//*[@id='s-top-left']/a[1]"));
            //new Actions对象
            Actions RightClick = new Actions(driver);
            //在 新闻 上点击鼠标右键
            RightClick.contextClick(Xw).perform();
            Thread.sleep(3000);
            //双击 新闻
            RightClick.doubleClick(Xw).perform();
            Thread.sleep(3000);

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