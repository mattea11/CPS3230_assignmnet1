package assignment1;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.WebDriver;

public class App {
    public static void main(String[] args) throws Exception {

        webScrap ws = new webScrap();
        postApi pa = new postApi();
        task2 t2 = new task2();

        WebDriver driver = null;

        int alertNo = 0; //is one less than the actual amount of alerts we will get
        List<itemInfo> items = new ArrayList<>();//itemInfo[] items = new itemInfo[alertNo];
        List<String> alerts = new ArrayList<>();

        driver = ws.setUpDriver(driver);
        driver = ws.searchItem(driver, "headphones");
        items = ws.QueryItemInfo(driver, alertNo);

        alerts = pa.createJsonObject(items, 6);

        for (int i = 0; i < alerts.size(); i++) {
            pa.postAlert(alerts.get(i));
        }
        // pa.deleteAlerts();

        driver = t2.accessMarketUm(driver);
        driver = t2.goToLogIn(driver, "7f84a00a-eeac-47fa-b15c-ee7e7ff9378d");
        driver = t2.goToAlerts(driver);
        // driver = t2.goToHome(driver);
        // driver = t2.goToLogOut(driver);
        // t2.closeMarketUm(driver);

    }
}