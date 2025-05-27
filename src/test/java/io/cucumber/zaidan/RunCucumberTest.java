package io.cucumber.zaidan;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.*;

@Suite
@IncludeEngines("cucumber")
@SelectPackages("io.cucumber.zaidan")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty, html:target/cucumber-reports/report.html, json:target/cucumber-reports/cucumber.json")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "io.cucumber.zaidan")
@ConfigurationParameter(key = FEATURES_PROPERTY_NAME, value = "src/test/resources/io/cucumber/zaidan")
@ConfigurationParameter(key = FILTER_TAGS_PROPERTY_NAME, value = "not @ignore")
@ConfigurationParameter(key = PARALLEL_EXECUTION_ENABLED_PROPERTY_NAME, value = "false")
public class RunCucumberTest {
}
