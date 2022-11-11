package assignment1;

// import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class task2 {

    public WebDriver accessMarketUm(WebDriver driver){
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver_win32\\chromedriver.exe");
        driver = new ChromeDriver();
        String url = "https://www.marketalertum.com/";
        driver.get(url); //open website
        return driver;
    }

    public WebDriver goToLogIn(WebDriver driver, String username){
        driver.findElement(By.xpath("//a[@href='/Alerts/Login']")).click();
        driver.findElement(By.id("UserId")).sendKeys(username);
        driver.findElement(By.xpath("//input[@type='submit']")).click();
        return driver;
    }

    public WebDriver goToLogOut(WebDriver driver){
        driver.findElement(By.xpath("//a[@href='/Home/Logout']")).click(); // "/html/body/header/nav/div/div/ul/li[3]/a")).click();// 
        return driver;
    }
    
    public WebDriver goToAlerts(WebDriver driver){
        driver.findElement(By.xpath("//a[@href='/Alerts/List']")).click();        
        return driver;
    }

    public WebDriver goToHome(WebDriver driver){
        driver.findElement(By.xpath("//a[@href='/']")).click();
        return driver;
    }

    public void closeMarketUm(WebDriver driver){
        driver.quit();
        driver.close();
    }
}
