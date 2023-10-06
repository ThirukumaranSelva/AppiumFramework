package com.qa.test.development.base;

import com.google.common.collect.ImmutableMap;
import com.qa.test.development.utils.Utils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.InteractsWithApps;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.screenrecording.CanRecordScreen;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.*;
import java.net.URL;
import java.time.Duration;
import java.util.Map;
import java.util.Properties;


public class BaseClass {

    protected static ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();
    protected static ThreadLocal<Properties> properties = new ThreadLocal<>();
    protected static ThreadLocal<Properties> propertiesTestData = new ThreadLocal<>();
    private static AppiumDriverLocalService server;
    ThreadLocal<String> platform = new ThreadLocal<>();
    ThreadLocal<String> dateTime = new ThreadLocal<>();


    public BaseClass() {
        PageFactory.initElements(new AppiumFieldDecorator(getDriver()), this);
    }

    @Parameters({"platformName", "platformVersion", "deviceUDID", "deviceName", "systemPort", "chromeDriverPort",
            "wdaLocalPort", "webkitDebugProxyPort"})
    @BeforeTest
    public void beforeTest(String platformName, @Optional("Both") String platformVersion, String deviceUDID,
                           String deviceName, @Optional("Android") String systemPort, @Optional("Android")
                           String chromeDriverPort,@Optional("iOS") String wdaLocalPort,@Optional("iOS") String webkitDebugProxyPort)
    {

        String logs = "logs" + File.separator + platformName + "_" + deviceName;
        File logFile = new File(logs);
        if (!logFile.exists()) {
            logFile.mkdirs();
        }
        ThreadContext.put("ROUTINGKEY", logs);
        try {
            setDateAndTime(Utils.dateAndTime());
            AppiumDriver driver;
            String file =
                    System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "config.properties";
            InputStream inputStream = new FileInputStream(file);
            Properties properties = new Properties();
            setProperties(properties);
            getProperties().load(inputStream);
            setPlatform(platformName);
            Utils.logger().info("Test Logs:");
/*

            //For using Single server-> declaring the url globally
            URL url = new URL(getProperties().getProperty("appiumURL"));

            switch (deviceUDID) {
                case "ce10171ab374340704" -> {
                    DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
                    desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
                    desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
                    desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
                    desiredCapabilities.setCapability(MobileCapabilityType.UDID, deviceUDID);
                    desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, getProperties().getProperty(
                            "androidAutomationName"));
                    String appURL = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "java" + File.separator + "com" + File.separator + "qa" + File.separator + "test" + File.separator + "development" + File.separator + "application" + File.separator + properties.getProperty("androidSauceLabsOldAppLocation");

                    //               desiredCapabilities.setCapability(MobileCapabilityType.APP, appURL);
                    desiredCapabilities.setCapability("appPackage", getProperties().getProperty("androidSwagLabOldAppPackage"));
                    desiredCapabilities.setCapability("appActivity", getProperties().getProperty("androidSwagLabOldAppActivity"));

                 //   URL url = new URL(getProperties().getProperty("appiumURLThiru"));
                    driver = new AndroidDriver(url, desiredCapabilities);
                    setDriver(driver);
                }
                case "1383272767000GR" -> {
                    DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
                    desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
                    desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
                    desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
                    desiredCapabilities.setCapability(MobileCapabilityType.UDID, deviceUDID);
                    desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, getProperties().getProperty(
                            "androidAutomationName"));
                    String appURL = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "java" + File.separator + "com" + File.separator + "qa" + File.separator + "test" + File.separator + "development" + File.separator + "application" + File.separator + properties.getProperty("androidSauceLabsOldAppLocation");

                    //               desiredCapabilities.setCapability(MobileCapabilityType.APP, appURL);
                    desiredCapabilities.setCapability("appPackage", getProperties().getProperty("androidSwagLabOldAppPackage"));
                    desiredCapabilities.setCapability("appActivity", getProperties().getProperty("androidSwagLabOldAppActivity"));

                   // URL url = new URL(getProperties().getProperty("appiumURLArun"));
                    driver = new AndroidDriver(url, desiredCapabilities);
                    setDriver(driver);
                }
            }

*/

            //For using Multiple server-> declaring the url in each statement.

            switch (deviceUDID) {
                case "ce10171ab374340704" -> {
                    DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
                    desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
                    desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
                    desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
                    desiredCapabilities.setCapability(MobileCapabilityType.UDID, deviceUDID);
                    desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, getProperties().getProperty(
                            "androidAutomationName"));
                    desiredCapabilities.setCapability("systemPort", systemPort);
                    desiredCapabilities.setCapability("chromeDriverPort", chromeDriverPort);

                    String appURL = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "java" + File.separator + "com" + File.separator + "qa" + File.separator + "test" + File.separator + "development" + File.separator + "application" + File.separator + properties.getProperty("androidSauceLabsOldAppLocation");

                    //               desiredCapabilities.setCapability(MobileCapabilityType.APP, appURL);
                    desiredCapabilities.setCapability("appPackage", getProperties().getProperty("androidSwagLabOldAppPackage"));
                    desiredCapabilities.setCapability("appActivity", getProperties().getProperty("androidSwagLabOldAppActivity"));

                    Utils.logger().info("Device Logs:");
                    URL url = new URL(getProperties().getProperty("appiumURLThiru"));
                    driver = new AndroidDriver(url, desiredCapabilities);
                    setDriver(driver);
                }
                case "1383272767000GR" -> {
                    DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
                    desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
                    desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
                    desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
                    desiredCapabilities.setCapability(MobileCapabilityType.UDID, deviceUDID);
                    desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, getProperties().getProperty(
                            "androidAutomationName"));
                    desiredCapabilities.setCapability("systemPort", systemPort);
                    desiredCapabilities.setCapability("chromeDriverPort", chromeDriverPort);

                    Utils.logger().info("Device 2 Logs:");
                    String appURL = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "java" + File.separator + "com" + File.separator + "qa" + File.separator + "test" + File.separator + "development" + File.separator + "application" + File.separator + properties.getProperty("androidSauceLabsOldAppLocation");

                    //desiredCapabilities.setCapability(MobileCapabilityType.APP, appURL);
                    desiredCapabilities.setCapability("appPackage", getProperties().getProperty("androidSwagLabOldAppPackage"));
                    desiredCapabilities.setCapability("appActivity", getProperties().getProperty("androidSwagLabOldAppActivity"));

                    URL url = new URL(getProperties().getProperty("appiumURLArun"));
                    driver = new AndroidDriver(url, desiredCapabilities);
                    setDriver(driver);
                }
            }

   /*
           //Normal Execution:
           switch (platformName) {
                case "Android" -> {
                    DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
                    desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
                    desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
                    desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
                    desiredCapabilities.setCapability(MobileCapabilityType.UDID, deviceUDID);
                    desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, getProperties().getProperty(
                            "androidAutomationName"));
                    String appURL = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "java" + File.separator + "com" + File.separator + "qa" + File.separator + "test" + File.separator + "development" + File.separator + "application" + File.separator + properties.getProperty("androidSauceLabsOldAppLocation");

     //               desiredCapabilities.setCapability(MobileCapabilityType.APP, appURL);
                    desiredCapabilities.setCapability("appPackage", getProperties().getProperty("androidSwagLabOldAppPackage"));
                    desiredCapabilities.setCapability("appActivity", getProperties().getProperty("androidSwagLabOldAppActivity"));

                    URL url = new URL(getProperties().getProperty("appiumURL"));
                    driver = new AndroidDriver(url, desiredCapabilities);
                    setDriver(driver);
                }
                case "iOS" -> {
                    DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
                    desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
                    desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
                    desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
                    desiredCapabilities.setCapability(MobileCapabilityType.UDID, deviceUDID);
                    desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, getProperties().getProperty(
                            "androidAutomationName"));
                    desiredCapabilities.setCapability("wdaLocalPort", wdaLocalPort);
                    desiredCapabilities.setCapability("webkitDebugProxyPort", webkitDebugProxyPort);

                    String appURL = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "java" + File.separator + "com" + File.separator + "qa" + File.separator + "test" + File.separator + "development" + File.separator + "application" + File.separator + properties.getProperty("iOSSauceLabsOldAppLocation");

                    //desiredCapabilities.setCapability(MobileCapabilityType.APP, appURL);
                    desiredCapabilities.setCapability("appPackage", getProperties().getProperty("iOSBundleId"));

                    URL url = new URL(getProperties().getProperty("appiumURL"));
                    driver = new IOSDriver(url, desiredCapabilities);
                    setDriver(driver);
                }
            }
*/
            String testDataFile =
                    System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "testData.properties";
            InputStream inputFile = new FileInputStream(testDataFile);
            Properties propertiesTestData = new Properties();
            setPropertiesTestData(propertiesTestData);
            getPropertiesTestData().load(inputFile);


        } catch (Exception e) {
            e.getStackTrace();
            System.out.println("Exception Caught:" + e);
        }
    }

    @BeforeTest(enabled = false)
    public synchronized void beforeMethod() {
        ((CanRecordScreen) getDriver()).startRecordingScreen();

    }

    @AfterMethod(enabled = false)
    public void afterMethod(ITestResult result) {
        var media = ((CanRecordScreen) getDriver()).stopRecordingScreen();
        Map<String, String> params = result.getTestContext().getCurrentXmlTest().getAllParameters();

        String videoFile =
                System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator +
                        "java" + File.separator + "com" + File.separator + "qa" + File.separator + "test" + File.separator +
                        "development" + File.separator + "videoRecorder" + File.separator + params.get("deviceName") + " " + getDateAndTime() + " " +
                        params.get("platformName") + "v" + params.get("platformVersion") +
                        " " + result.getName();
        File file = new File(videoFile);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file + ".mp4");
            fileOutputStream.write(Base64.decodeBase64(media));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public void waitForElement(WebElement element) {
        try {
            Wait<WebDriver> wait = new WebDriverWait(getDriver(), Duration.ofSeconds(Utils.wait));
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void click(WebElement element) {
        try {
            waitForElement(element);
            element.click();
        } catch (Exception e) {
            System.out.println("Click Method Exception" + e);
        }
    }

    public void sendKeys(WebElement element, String text) {
        try {
            waitForElement(element);
            element.sendKeys(text);
        } catch (Exception e) {
            System.out.println("SendKeys Method Exception" + e);
        }

    }

    public String attribute(WebElement element, String attribute) {
        try {
            waitForElement(element);
        } catch (Exception e) {
            System.out.println("Attribute Method Exception" + e);
        }
        return element.getAttribute(attribute);
    }

    public void clear(WebElement element) {
        try {
            waitForElement(element);
        } catch (Exception e) {
            System.out.println("Clear Method Exception" + e);
        }
        element.clear();
    }

    @BeforeSuite
    public void startServer(){

        String serverLogs = "logs" + File.separator + "ServerLogs";
        File logFile = new File(serverLogs);
        if (!logFile.exists()) {
            logFile.mkdirs();
        }
        ThreadContext.put("ROUTINGKEY", serverLogs);
        server=getServerCustom();
        server.start();

        System.out.println("Server started:");
    }
    @AfterSuite
    public void stopServer(){
        server.stop();
        System.out.println("Server stopped:");
        Utils.logger().info("Server");
    }
    public AppiumDriverLocalService getServerDefault(){
        return AppiumDriverLocalService.buildDefaultService();
    }

    public AppiumDriverLocalService getServerCustom(){
            String nodejs= "C:"+File.separator+"Program Files"+File.separator+"nodejs"+File.separator+"node.exe";
        String appiumJS=
                "C:"+File.separator+"Users"+File.separator+"Thirukumaran"+File.separator+"AppData"+File.separator+
                        "Roaming"+File.separator+"npm"+File.separator+"node_modules"+File.separator+"appium"+File.separator+"build"+File.separator+"lib"+File.separator+"main.js";
            return AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
                    .usingDriverExecutable(new File(nodejs)).withAppiumJS(new File(appiumJS))
                    .usingPort(4723)
                    .withArgument(GeneralServerFlag.SESSION_OVERRIDE)

            );

    }


    public AppiumDriver getDriver() {
        return driver.get();
    }

    public void setDriver(AppiumDriver driver1) {
        driver.set(driver1);
    }

    public String getPlatform() {
        return platform.get();
    }

    public void setPlatform(String platform1) {
        platform.set(platform1);
    }

    public String getDateAndTime() {
        return dateTime.get();
    }

    public void setDateAndTime(String dateTime1) {
        dateTime.set(dateTime1);
    }

    public Properties getProperties() {
        return properties.get();
    }

    public void setProperties(Properties properties1) {
        properties.set(properties1);
    }

    public Properties getPropertiesTestData() {
        return propertiesTestData.get();
    }

    public void setPropertiesTestData(Properties propertiesTestData1) {
        propertiesTestData.set(propertiesTestData1);
    }

    public void scrollableOld(WebElement element, double percentageValue) {
        getDriver().executeScript("mobile: scrollGesture", ImmutableMap.of(
                "elementId", ((RemoteWebElement) element).getId(),
                "direction", "down",
                "percent", percentageValue
        ));
    }

    /*   public void scrollable(WebDriver driver) {
           driver.findElement(AppiumBy.androidUIAutomator("new UIScrollable(new UiSelector()" + ".description
           (\"<parent_locator>")).scrollIntoView("+ " new UISelector().description(\"<child_locator>\"));");
       }
   */
    /*
    public void closeApp() {
        switch (getPlatform()) {
            case "Android": {
                ((InteractsWithApps) getDriver()).terminateApp(getProperties().getProperty("androidSwagLabOldAppPackage"));
                break;
            }
            default:
                System.out.println("Enter valid platform");
        }
    }

    public void openApp() {
        switch (getPlatform()) {
            case "Android": {
                ((InteractsWithApps) getDriver()).activateApp(getProperties().getProperty("androidSwagLabOldAppPackage"));
                break;
            }
            default:
                System.out.println("Enter valid platform");
        }
    }
    */
    @AfterTest
    public void afterTest() {
        getDriver().quit();

    }
}
