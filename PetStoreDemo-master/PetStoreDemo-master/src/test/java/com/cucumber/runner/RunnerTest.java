package com.cucumber.runner;

import io.cucumber.junit.Cucumber;

import org.junit.runner.RunWith;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/main/resources/features"
        ,glue={"com.cucumber.stepdefs"}
)

public class RunnerTest {

}
