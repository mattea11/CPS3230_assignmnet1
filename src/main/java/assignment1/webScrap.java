package assignment1;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class webScrap {

    public WebDriver setUpDriver(WebDriver driver){
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver_win32\\chromedriver.exe"); 
        driver = new ChromeDriver();
        
        String scraperUrl = "https://www.atoz.com.mt/";

        driver.get(scraperUrl); //open website
        driver.manage().window().maximize(); //open the page to fullscreen mode
        return driver;
    }  

    public WebDriver searchItem(WebDriver driver, String itemSearch){
        driver.findElement(By.id("search")).sendKeys(itemSearch); //enter item to be looked up
        driver.findElement(By.className("search__submit")).click(); //list all items
        return driver;
    }

    public List<itemInfo> QueryItemInfo(WebDriver driver, int alertNo){                                                 
        
        WebElement row;
        String title; String description; String url; String image; String price;
        List<itemInfo> items = new ArrayList<>();//itemInfo[alertNo+1];

        for (int i = 1; i <= alertNo+1; i++) { //get the first x amount of items and any required information about them
            row = driver.findElement(By.xpath("//*[@id=\"MainForm\"]/table[2]/tbody/tr/td/table/tbody/tr/td/div/div["+ i +"]/div"));

            url = row.findElement(By.tagName("a")).getAttribute("href");
            image = row.findElement(By.tagName("img")).getAttribute("src");
            title = row.findElement(By.tagName("a")).getAttribute("title");
            description = row.findElement(By.xpath("//*[@id=\"MainForm\"]/table[2]/tbody/tr/td/table/tbody/tr/td/div/div[1]/div/div/a[1]")).getText();
            price = row.findElement(By.xpath("//*[@id=\"MainForm\"]/table[2]/tbody/tr/td/table/tbody/tr/td/div/div[1]/div/div/div[1]/div/font[1]/div/b")).getText();
            price = price.replaceAll("[^0-9]", "");
            
            items.add(new itemInfo(title, description, url, image, price));
        }

        return items; //return a list of all the items scarpped fro the website
    } 
}
