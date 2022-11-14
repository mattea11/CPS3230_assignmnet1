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
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class task1Test 
{
    private static final String URL = "https://www.scanmalta.com/shop/";

    @Test
    public void validURL() throws Exception{
        //set up
        String regex = "((http|https)://)(www.)?"
              + "[a-zA-Z0-9@:%._\\+~#?&//=]"
              + "{2,256}\\.[a-z]"
              + "{2,6}\\b([-a-zA-Z0-9@:%"
              + "._\\+~#?&//=]*)";

        Pattern pat = Pattern.compile(regex);

        Matcher match = pat.matcher(URL);
        
        //exercise
        Boolean correct = match.matches(); // compare the url with the valid URl pattern to assert if its is valid or not

        //verification
        assertTrue(correct);
        
    }
    
    @Test 
    public void createRightAmouontOfJsonObj() throws JSONException, UnsupportedEncodingException{
        //setup
        webScrap ws = Mockito.mock(webScrap.class);
        WebDriver driver = Mockito.mock(WebDriver.class);
        postApi pa = new postApi();
        List<itemInfo> items = new ArrayList<>();
        List<String> alert = new ArrayList<>();

        items.add(new itemInfo("heading1", "decsription1", "url1", "image1", "123"));
        items.add(new itemInfo("heading2", "decsription2", "url2", "image2", "123"));

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
    
    // @Rule
    // public ExpectedException thrown = ;

    @Test (expected = IllegalArgumentException.class)
    public void catchExceptionOnJsonObjectCreateTitle() throws JSONException, UnsupportedEncodingException{
        //setup
        webScrap ws = Mockito.mock(webScrap.class);
        WebDriver driver = Mockito.mock(WebDriver.class);
        postApi pa = new postApi();
        List<itemInfo> items = new ArrayList<>();

        items.add(new itemInfo("", "decsription1", "url1", "image1", "123"));
        
        Mockito.when(ws.QueryItemInfo(driver, 1)).thenReturn(items);

        //exercise
        pa.createJsonObject(items,6);

        //teardown
        pa = null;
        items = null;
    }

    @Test (expected = IllegalArgumentException.class)
    public void catchExceptionOnJsonObjectCreateDescription() throws JSONException, UnsupportedEncodingException{
        //setup
        webScrap ws = Mockito.mock(webScrap.class);
        WebDriver driver = Mockito.mock(WebDriver.class);
        postApi pa = new postApi();
        List<itemInfo> items = new ArrayList<>();

        items.add(new itemInfo("heading1", "", "url1", "image1", "123"));
        
        Mockito.when(ws.QueryItemInfo(driver, 1)).thenReturn(items);

        //exercise
        pa.createJsonObject(items,6);
        
        //teardown
        pa = null;
        items = null;
    }

    @Test (expected = IllegalArgumentException.class)
    public void catchExceptionOnJsonObjectCreateURL() throws JSONException, UnsupportedEncodingException{
        //setup
        webScrap ws = Mockito.mock(webScrap.class);
        WebDriver driver = Mockito.mock(WebDriver.class);
        postApi pa = new postApi();
        List<itemInfo> items = new ArrayList<>();

        items.add(new itemInfo("heading1", "decsription1", "", "image1", "123"));
        
        Mockito.when(ws.QueryItemInfo(driver, 1)).thenReturn(items);

        //exercise
        pa.createJsonObject(items,6);
        
        //teardown
        pa = null;
        items = null;
    }

    @Test (expected = IllegalArgumentException.class)
    public void catchExceptionOnJsonObjectCreateImageUrl() throws JSONException, UnsupportedEncodingException{
        //setup
        webScrap ws = Mockito.mock(webScrap.class);
        WebDriver driver = Mockito.mock(WebDriver.class);
        postApi pa = new postApi();
        List<itemInfo> items = new ArrayList<>();

        items.add(new itemInfo("heading1", "decsription1", "url1", "", "123"));
        
        Mockito.when(ws.QueryItemInfo(driver, 1)).thenReturn(items);

        //exercise
        pa.createJsonObject(items,6);
        
        //teardown
        pa = null;
        items = null;
    }

    @Test (expected = IllegalArgumentException.class)
    public void catchExceptionOnJsonObjectCreatePrice() throws JSONException, UnsupportedEncodingException{
        //setup
        webScrap ws = Mockito.mock(webScrap.class);
        WebDriver driver = Mockito.mock(WebDriver.class);
        postApi pa = new postApi();
        List<itemInfo> items = new ArrayList<>();

        items.add(new itemInfo("heading1", "decsription1", "url1", "image1", ""));
        
        Mockito.when(ws.QueryItemInfo(driver, 1)).thenReturn(items);

        //exercise
        pa.createJsonObject(items,6);
        
        //teardown
        pa = null;
        items = null;
    }

    @Test (expected = IllegalArgumentException.class)
    public void catchExceptionOnJsonObjectCreateAlertType() throws JSONException, UnsupportedEncodingException{
        //setup
        webScrap ws = Mockito.mock(webScrap.class);
        WebDriver driver = Mockito.mock(WebDriver.class);
        postApi pa = new postApi();
        List<itemInfo> items = new ArrayList<>();

        items.add(new itemInfo("heading1", "decsription1", "url1", "image1", "123"));
        
        Mockito.when(ws.QueryItemInfo(driver, 1)).thenReturn(items);

        
        //exercise
        pa.createJsonObject(items,9);
        
        //teardown
        pa = null;
        items = null;
    }
    
    @Test
    public void createRightAmouontOfJsonObjException() throws JSONException, UnsupportedEncodingException{
        //setup
        webScrap ws = Mockito.mock(webScrap.class);
        WebDriver driver = Mockito.mock(WebDriver.class);
        postApi pa = new postApi();
        List<itemInfo> items = new ArrayList<>();
        List<String> alert = new ArrayList<>();

        items.add(new itemInfo("heading1", "decsription1", "url1", "image1", "123"));
        items.add(new itemInfo("heading2", "decsription2", "url2", "image2", "123"));

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

    // @Test
    // public void postRightAmountOfAlerts() throws IOException{
    //     //setup
    //     postApi pa = Mockito.mock(postApi.class);
    //     List<itemInfo> items = Mockito.mock(List.class);
    //     postApi pa1 = new postApi();
    //     List<String> alerts = new ArrayList<>();
    //     Boolean checkPost = false;

    //     alerts.add("{"+
    //         "\"alertType\":9,"+
    //         "\"heading\":\"\","+
    //         "\"description\":\"\","+
    //         "\"url\":\"\","+
    //         "\"imageUrl\":\"\","+
    //         "\"postedBy\":\"\","+
    //         "\"priceInCents\":\"\""+
    //         "}"
    //     );
        
    //     Mockito.when(pa.createJsonObject(items,9)).thenReturn(alerts);

    //     //exercise and verification
    //     for (int i = 0; i < alerts.size(); i++) {
    //         checkPost = pa1.postAlert(alerts.get(i)); // will retrun true if code returned from attempted post is 201 (successful)
    //         assertTrue(checkPost);
    //     }

    // }

    @Test
    public void deleteAllAlerts() throws IOException{
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
        driver.close();
        ws = null;
        pa = null;
        t2 = null;
        driver = null;
        items = null;
        alerts = null;
    }
}
