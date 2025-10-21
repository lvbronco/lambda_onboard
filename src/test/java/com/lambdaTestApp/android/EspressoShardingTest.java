package com.lambdaTestApp.android;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import okhttp3.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class EspressoShardingTest {

    String userName = System.getenv("LT_USERNAME") == null ? "Your LT Username" : System.getenv("LT_USERNAME");
    String accessKey = System.getenv("LT_ACCESS_KEY") == null ? "Your LT AccessKey" : System.getenv("LT_ACCESS_KEY");

    String credential = Credentials.basic(userName, accessKey);
    public String gridURL = "@mobile-hub.lambdatest.com/wd/hub";

    AppiumDriver driver;

    @BeforeSuite
    public void beforeSuite() throws IOException, InterruptedException {
        if (isAppPresent()){
            System.out.println("App already present, skipping app upload...");
        }else {
            System.out.println("App not present, uploading app...");
            uploadApp();
            System.out.println("App upload successful");
            Thread.sleep(60000);
        }
    }

    @Test
    @org.testng.annotations.Parameters(value = {"device", "version", "platform", "testFramework", "shardIndex", "totalShards", "junitReport"})
    public void espressoShardingTest(String device, String version, String platform, String testFramework, String shardIndex, String totalShards, String junitReport) {
        try {
            System.out.println("Running Espresso Sharding Test - Shard " + shardIndex + " of " + totalShards);
            
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("build","Espresso Test with Sharding and JUnit Report");
            capabilities.setCapability("name",platform+" "+device+" "+version+" Espresso Shard "+shardIndex);
            capabilities.setCapability("deviceName", device);
            capabilities.setCapability("platformVersion",version);
            capabilities.setCapability("platformName", platform);
            capabilities.setCapability("isRealMobile", true);
            capabilities.setCapability("app", "proverbial-android");
            capabilities.setCapability("deviceOrientation", "PORTRAIT");
            capabilities.setCapability("console", true);
            capabilities.setCapability("network", true);
            capabilities.setCapability("visual", true);
            capabilities.setCapability("devicelog", true);
            capabilities.setCapability("testFramework", testFramework);
            
            // Sharding specific capabilities
            capabilities.setCapability("shardIndex", Integer.parseInt(shardIndex));
            capabilities.setCapability("totalShards", Integer.parseInt(totalShards));
            capabilities.setCapability("junitReport", Boolean.parseBoolean(junitReport));

            String hub = "https://" + userName + ":" + accessKey + gridURL;
            driver = new AppiumDriver(new URL(hub), capabilities);

            // Espresso Test specific interactions for sharding
            System.out.println("Executing shard " + shardIndex + " specific tests...");
            
            // Shard-specific test logic based on shard index
            int shard = Integer.parseInt(shardIndex);
            switch (shard) {
                case 0:
                    // Shard 0: Color and Text tests
                    MobileElement color = (MobileElement) driver.findElementById("com.lambdatest.proverbial:id/color");
                    color.click();
                    Thread.sleep(1000);
                    color.click();
                    MobileElement text = (MobileElement) driver.findElementById("com.lambdatest.proverbial:id/Text");
                    text.click();
                    break;
                case 1:
                    // Shard 1: Toast and Notification tests
                    MobileElement toast = (MobileElement) driver.findElementById("com.lambdatest.proverbial:id/toast");
                    toast.click();
                    MobileElement notification = (MobileElement) driver.findElementById("com.lambdatest.proverbial:id/notification");
                    notification.click();
                    Thread.sleep(2000);
                    break;
                case 2:
                    // Shard 2: Geolocation test
                    MobileElement geo = (MobileElement) driver.findElementById("com.lambdatest.proverbial:id/geoLocation");
                    geo.click();
                    Thread.sleep(5000);
                    MobileElement home = (MobileElement) driver.findElementByAccessibilityId("Home");
                    home.click();
                    break;
                case 3:
                    // Shard 3: Speed test
                    MobileElement speedtest = (MobileElement) driver.findElementById("com.lambdatest.proverbial:id/speedTest");
                    speedtest.click();
                    Thread.sleep(5000);
                    MobileElement Home = (MobileElement) driver.findElementByAccessibilityId("Home");
                    Home.click();
                    break;
                case 4:
                    // Shard 4: Browser test
                    MobileElement browser = (MobileElement) driver.findElementByAccessibilityId("Browser");
                    browser.click();
                    MobileElement url = (MobileElement) driver.findElementById("com.lambdatest.proverbial:id/url");
                    url.sendKeys("https://www.lambdatest.com");
                    MobileElement find = (MobileElement) driver.findElementById("com.lambdatest.proverbial:id/find");
                    find.click();
                    break;
                default:
                    // Default: Run all tests
                    MobileElement colorDefault = (MobileElement) driver.findElementById("com.lambdatest.proverbial:id/color");
                    colorDefault.click();
                    Thread.sleep(1000);
                    colorDefault.click();
                    MobileElement textDefault = (MobileElement) driver.findElementById("com.lambdatest.proverbial:id/Text");
                    textDefault.click();
                    MobileElement toastDefault = (MobileElement) driver.findElementById("com.lambdatest.proverbial:id/toast");
                    toastDefault.click();
                    MobileElement notificationDefault = (MobileElement) driver.findElementById("com.lambdatest.proverbial:id/notification");
                    notificationDefault.click();
                    Thread.sleep(2000);
                    MobileElement geoDefault = (MobileElement) driver.findElementById("com.lambdatest.proverbial:id/geoLocation");
                    geoDefault.click();
                    Thread.sleep(5000);
                    MobileElement homeDefault = (MobileElement) driver.findElementByAccessibilityId("Home");
                    homeDefault.click();
                    MobileElement speedtestDefault = (MobileElement) driver.findElementById("com.lambdatest.proverbial:id/speedTest");
                    speedtestDefault.click();
                    Thread.sleep(5000);
                    MobileElement HomeDefault = (MobileElement) driver.findElementByAccessibilityId("Home");
                    HomeDefault.click();
                    MobileElement browserDefault = (MobileElement) driver.findElementByAccessibilityId("Browser");
                    browserDefault.click();
                    MobileElement urlDefault = (MobileElement) driver.findElementById("com.lambdatest.proverbial:id/url");
                    urlDefault.sendKeys("https://www.lambdatest.com");
                    MobileElement findDefault = (MobileElement) driver.findElementById("com.lambdatest.proverbial:id/find");
                    findDefault.click();
                    break;
            }

            System.out.println("Shard " + shardIndex + " completed successfully");
            driver.quit();

        } catch (Exception e) {
            System.err.println("Shard " + shardIndex + " failed: " + e.getMessage());
            e.printStackTrace();
            try{
                driver.quit();
            }catch(Exception e1){
                e.printStackTrace();
            }
        }
    }

    private void uploadApp() throws IOException, InterruptedException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("appFile","apps/proverbial_android.apk",
                        RequestBody.create(MediaType.parse("application/octet-stream"),
                                new File("apps/proverbial_android.apk")))
                .addFormDataPart("custom_id","proverbial-android")
                .addFormDataPart("name","proverbial-android")
                .build();
        Request request = new Request.Builder()
                .url("https://manual-api.lambdatest.com/app/upload/realDevice")
                .method("POST", body)
                .addHeader("Authorization", credential)
                .build();
        Response response = client.newCall(request).execute();
    }

    private boolean isAppPresent() throws IOException {
        String jsonString = getResponseAsJson("android");
        List<String> listOfApps = getAppIdsFromJson(jsonString);
        return listOfApps.contains("proverbial-android");
    }

    private String getResponseAsJson(String platform) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://manual-api.lambdatest.com/app/data?type="+platform+"&level=user")
                .method("GET",null)
                .addHeader("Authorization", credential)
                .build();

        Response response = client.newCall(request).execute();
        assert response.body() != null;
        return response.body().string();
    }

    private List<String> getAppIdsFromJson(String jsonData) {
        List<String> namesList = new ArrayList<String>();
        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(jsonData);
            JSONArray dataArray = (JSONArray) jsonObject.get("data");
            for (Object data : dataArray) {
                JSONObject dataObject = (JSONObject) data;
                String name = (String) dataObject.get("name");
                namesList.add(name);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return namesList;
    }
}
