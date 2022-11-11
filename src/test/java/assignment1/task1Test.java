package assignment1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONException;
import org.junit.Test;
import org.mockito.Mockito;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class task1Test 
{
    private static final String URL = "https://www.scanmalta.com/shop/";

    @Test
    public void validURL() throws Exception{

        String regex = "((http|https)://)(www.)?"
              + "[a-zA-Z0-9@:%._\\+~#?&//=]"
              + "{2,256}\\.[a-z]"
              + "{2,6}\\b([-a-zA-Z0-9@:%"
              + "._\\+~#?&//=]*)";

        Pattern pat = Pattern.compile(regex);

        Matcher match = pat.matcher(URL);
        Boolean correct = match.matches(); // compare the url with the valid URl pattern to assert if its is valid or not

        assertTrue(correct);
        
    }

    @Test
    public void getItemInfo(){
        //setup
        webScrap ws = new webScrap();//Mockito.mock(webScrap.class);
        WebDriver driver = null;
        List<itemInfo> items = new ArrayList<>();

        //exercise
        driver = ws.setUpDriver(driver);
        driver = ws.searchItem(driver, "headphones");
        
        
        //verfication
        for (int i = 0; i < 3; i++) {
         
        items = ws.QueryItemInfo(driver, i);
        assertEquals(i, items.size()-1); //assert that the webscarper returns the correct amount of items
        }

        // teardown
        driver.quit();
    }
    
    @Test
    public void createJsonObj() throws JSONException, UnsupportedEncodingException{
        //setup
        webScrap ws = Mockito.mock(webScrap.class);
        WebDriver driver = Mockito.mock(WebDriver.class);
        postApi pa = new postApi();
        List<itemInfo> items = new ArrayList<>();
        List<String> alert = new ArrayList<>();

        items.add(new itemInfo("title1", "decsription1", "url1", "image1", "123"));
        items.add(new itemInfo("title2", "decsription2", "url2", "image2", "123"));

        Mockito.when(ws.QueryItemInfo(driver, 2)).thenReturn(items);

        
        //exercise
        alert = pa.createJsonObject(items,6);
        
        //verfication
        assertEquals(2, alert.size());

        //teardown
        pa = null;
        items = null;
        alert = null;
    }

    @Test
    public void postAlerts() throws IOException{
        //setup
        postApi pa = Mockito.mock(postApi.class);
        List<itemInfo> items = Mockito.mock(List.class);
        postApi pa1 = new postApi();
        List<String> alerts = new ArrayList<>();
        Boolean checkPost = false;

        alerts.add("{"+
            "\"alertType\":6,"+
            "\"heading\":\"Wraps Classic In Ear\","+
            "\"description\":\"Wraps Classic In-Ear Headphone Pink WRAPSCPIN-V5 Simple Product Headphones\","+
            "\"url\":\"https://www.atoz.com.mt/Wraps-Classic-In-Ear-p/5060382793292.htm\","+
            "\"imageUrl\":\"https://cdn3.volusion.com/xzwdw.xzhmz/v/vspfiles/photos/5060382793292-1.jpg?v-cache=1644288741\","+
            "\"postedBy\":\"7f84a00a-eeac-47fa-b15c-ee7e7ff9378d\","+
            "\"priceInCents\":2495"+
            "}"

        );
        alerts.add("{"+
            "\"alertType\":6,"+
            "\"heading\":\"Wraps Classic In Ear\","+
            "\"description\":\"Wraps Classic In-Ear Headphone Blue WRAPSCPIN-V5 Simple Product Headphones\","+
            "\"url\":\"https://www.atoz.com.mt/Wraps-Classic-In-Ear-p/5060382793285.htm\","+
            "\"imageUrl\":\"https://cdn3.volusion.com/xzwdw.xzhmz/v/vspfiles/photos/5060382793285-1.jpg?v-cache=1644288009\","+
            "\"postedBy\":\"7f84a00a-eeac-47fa-b15c-ee7e7ff9378d\","+
            "\"priceInCents\":2495"+
            "}"

        );
        
        Mockito.when(pa.createJsonObject(items,6)).thenReturn(alerts);

        //exercise and verification
        for (int i = 0; i < alerts.size(); i++) {
            checkPost = pa1.postAlert(alerts.get(i)); // will retrun true if code returned from attempted post is 201 (successful)
            assertTrue(checkPost);
        }
        
    }

    @Test
    public void deleteAlerts() throws IOException{
        //setup
        webScrap ws = new webScrap();
        postApi pa = new postApi();
        task2 t2 = new task2();
        WebDriver driver = null;
        List<itemInfo> items = new ArrayList<>();
        List<String> alerts = new ArrayList<>();

        driver = ws.setUpDriver(driver);
        driver = ws.searchItem(driver, "headphones");
        items = ws.QueryItemInfo(driver, 6);

        alerts = pa.createJsonObject(items, 6);

        for (int i = 0; i < alerts.size(); i++) {
            pa.postAlert(alerts.get(i));
        }

        //exercise
        pa.deleteAlerts();
        driver = t2.accessMarketUm(driver);
        driver = t2.goToLogIn(driver, "7f84a00a-eeac-47fa-b15c-ee7e7ff9378d");
        driver = t2.goToAlerts(driver);
        
        //verfication
        assertEquals(0, driver.findElements(By.tagName("table")).size());

        //teardown
        ws = null;
        pa = null;
        t2 = null;
        driver = null;
        items = null;
        alerts = null;
    }
}
