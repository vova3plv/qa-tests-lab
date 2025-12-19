package com.example.e2e;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = "cucumber.glue", value = "com.example.e2e.steps")
@ConfigurationParameter(
        key = "cucumber.plugin",
        value = "pretty, html:target/cucumber-report.html, json:target/cucumber-report.json"
)
public class CucumberTest {}
