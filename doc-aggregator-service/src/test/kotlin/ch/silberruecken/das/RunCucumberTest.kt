package ch.silberruecken.das

import io.cucumber.junit.platform.engine.Constants
import org.junit.platform.suite.api.*


@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameters(
    ConfigurationParameter(
        key = Constants.PLUGIN_PROPERTY_NAME,
        value = "pretty,html:build/cucumber-reports/cucumber.html,json:build/cucumber-reports/cucumber.json"
    ),
    ConfigurationParameter(
        key = Constants.PLUGIN_PUBLISH_TOKEN_PROPERTY_NAME,
        value = "85afa876-9a12-4183-a9b8-5300fac5853c"
    )
)
class RunCucumberTest
