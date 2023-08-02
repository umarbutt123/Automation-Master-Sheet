package com.eurowings.definitions;

import static org.testng.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

public class FlightStatusDefinitions {

    private static WebDriver driver;       
    public final static int TIMEOUT = 10;
    private static WebDriverWait wait ;
    private  Actions actions; 
    private Calendar calendar;
    private SimpleDateFormat sdf ;
    private String TodaysDate;
    private String TomorrowDate;
    private String YesterdayDate;
    private static String FlightNumberFetch;

     
    @Before
    public void setUp() {
 
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TIMEOUT));
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
        actions = new Actions(driver); 
        
        Date TodayDate = new Date();
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        TodaysDate = sdf.format(TodayDate);


        calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date futureDateTime = calendar.getTime();
        TomorrowDate = sdf.format(futureDateTime);
        
        calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        Date PastDateTime = calendar.getTime();
        YesterdayDate = sdf.format(PastDateTime);
    }
 
    @Given("User is checking flight statuses on {string}")
    public void LandingPage(String url) {
         
        driver.get(url);
        
        By elementLocator = By.xpath("//h2[contains(text(),'Privacy settings')]");
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(elementLocator));
      //      System.out.println("Cookie Header exists");
            driver.findElement(By.xpath("//span[contains(text(),'I understand')]")).click();
        } catch (TimeoutException e) {
            System.out.println("Cookie Header does not exist");
        }
        
    }
  
    @When("User enters departure as {string} and destination as {string} and date as {string}")
    public void FlightDetailsviaRoute(String departure, String destination, String date) throws InterruptedException {
  
    	actions.moveToElement(driver.findElement(By.xpath("//span[contains(text(),'Show flight status')]")));
    	actions.perform();
        driver.findElement(By.xpath("//span[contains(text(),'Departure airport')]")).click();
        driver.findElement(By.xpath("//input[@placeholder='Departure airport']")).sendKeys(departure);
        driver.findElement(By.xpath("//input[@placeholder='Departure airport']")).sendKeys(Keys.ENTER);
        
        
        Thread.sleep(1000);
        driver.findElement(By.xpath("//span[contains(text(),'Destination airport')]")).click();  
        driver.findElement(By.xpath("//input[@placeholder='Destination airport']")).sendKeys(destination);
        driver.findElement(By.xpath("//input[@placeholder='Destination airport']")).sendKeys(Keys.ENTER);
        
        driver.findElement(By.xpath("//*[contains(@name, 'datepicker_input')]")).click();
        if(date.equalsIgnoreCase("Today")) 
        	{
        	driver.findElement(By.xpath("//input[@value='"+TodaysDate+"']")).click();
                    	}
        else if (date.equalsIgnoreCase("Yesterday")) 
        	{
        	try {
        	driver.findElement(By.xpath("//input[@value='"+YesterdayDate+"']")).click();
        	}
        	catch(Exception e)
        	{
        		driver.findElement(By.xpath("//button[contains(@class,'calendar__previous-month')]//span[@class='a-cta__icon']")).click();
        		driver.findElement(By.xpath("//input[@value='"+YesterdayDate+"']")).click();
        	}
        	}
        else if (date.equalsIgnoreCase("Tomorrow")) 
        	{
        	try {
        		driver.findElement(By.xpath("//input[@value='"+TomorrowDate+"']")).click();
            	}
            	catch(Exception e)
            	{
            		driver.findElement(By.xpath("//button[contains(@class,'calendar__next-month')]//span[@class='a-cta__icon']")).click();
            		driver.findElement(By.xpath("//input[@value='"+TomorrowDate+"']")).click();
            	}
        	
        	}
        
        driver.findElement(By.xpath("//span[contains(text(),'Show flight status')]")).click();
         
    }
     
    @Then("User should be able to see {string} flights")
    public void VerifyFlightDates(String date) {
    	calendar = Calendar.getInstance();
        Date futureDateTime = calendar.getTime();
        sdf = new SimpleDateFormat("dd/MM/");
        String tdy = sdf.format(futureDateTime);
        
          calendar = Calendar.getInstance();
          calendar.add(Calendar.DAY_OF_YEAR, 1);
          Date futureDateTime1 = calendar.getTime();
          sdf = new SimpleDateFormat("dd/MM/");
          String tmrw = sdf.format(futureDateTime1);
          
          calendar = Calendar.getInstance();
          calendar.add(Calendar.DAY_OF_YEAR, -1);
          Date futureDateTime2 = calendar.getTime();
          sdf = new SimpleDateFormat("dd/MM/");
          String Ystrd = sdf.format(futureDateTime2);
          
          
    	actions.moveToElement(driver.findElement(By.xpath("//div[contains(@class,'o-search-flight-status__flight-list-cards')]")));
    	actions.perform();
         String UIDate = driver.findElement(By.xpath("//button[contains(@class,'navigation__date--active')]//div")).getText();
         if(date.equalsIgnoreCase("Today")) 
     	{
     	Assert.assertTrue(UIDate.contains(tdy));
                 	}
     else if (date.equalsIgnoreCase("Yesterday")) 
     	{
    	 Assert.assertTrue(UIDate.contains(Ystrd));
     	}
     else if (date.equalsIgnoreCase("Tomorrow")) 
     	{
    	 Assert.assertTrue(UIDate.contains(tmrw));
     	}  
    }
     
    @Then("Route must be mentioned from {string} to {string}")
    public void verifyFlightRoute(String departure, String destination) {
  
        String UIDeparture = driver.findElement(By.xpath("//div[@class='o-search-flight-status__card'][1]//div[@class='o-search-flight-status__card-airports']//p[contains(@class,'a-paragraph a-paragraph--left')][1]")).getText();
        String UIDestination = driver.findElement(By.xpath("//div[@class='o-search-flight-status__card'][1]//div[@class='o-search-flight-status__card-airports']//p[contains(@class,'a-paragraph a-paragraph--left')][2]")).getText();
         
        Assert.assertEquals(UIDeparture,departure);
        Assert.assertEquals(UIDestination,destination);
  
    }
    
    @Then("status should be mentioned as {string} or {string}")
    public void verifyFlightStatus(String status1, String status2) {
  
         String FlightStatus = driver.findElement(By.xpath("//div[@class='o-search-flight-status__card'][1]//div[contains(@class,'o-search-flight-status__card-flight-status')]/p")).getText();
          
         assertTrue(FlightStatus.equals(status1) || FlightStatus.equals(status2));

  
    }
    
    @When("User enters flight number \\(fetched from routes) and date")
    public void FlightNumber() throws InterruptedException {
  
    	FlightDetailsviaRoute("CGN","BER","Today");
    	Thread.sleep(1000);
        FlightNumberFetch = driver.findElement(By.xpath("//div[@class='o-search-flight-status__card'][1]//div[@class='o-search-flight-status__card-flight-number']/p")).getText();
        driver.findElement(By.xpath("//span[contains(text(),'Flight number')]")).click();
        driver.findElement(By.xpath("//input[@name='flightNumber']")).sendKeys(FlightNumberFetch);
        driver.findElement(By.xpath("//*[contains(@name, 'datepicker_input')]")).click();
        driver.findElement(By.xpath("//input[@value='"+TodaysDate+"']")).click();
        driver.findElement(By.xpath("//span[contains(text(),'Show flight status')]")).click();
       

  
    }
    
    @Then("User should be able to see the exact flight")
    public void verifyFlightDetails() {
  
    	 String FlightNumberResult = driver.findElement(By.xpath("//div[@class='o-search-flight-status__card'][1]//div[@class='o-search-flight-status__card-flight-number']/p")).getText();
         Assert.assertEquals(FlightNumberResult,FlightNumberFetch);
  
    }
     
    @After
    public void teardown() {
  
        driver.quit();
    }

}

