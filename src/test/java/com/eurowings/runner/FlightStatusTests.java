package com.eurowings.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;    
import org.testng.annotations.Test;

@CucumberOptions
(tags = "", features = {"src/test/resources/Features/FlightStatus.feature"},
glue = {"com.eurowings.definitions"}, 
plugin = {})

public class FlightStatusTests extends AbstractTestNGCucumberTests {

}
