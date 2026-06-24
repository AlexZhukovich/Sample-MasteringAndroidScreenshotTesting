package com.alexzh.moodtracker.screenshottests.composepreviewscanner.roborazzi

import androidx.test.core.app.ApplicationProvider
import com.alexzh.moodtracker.screenshottests.common.FakeImageLoaderRule
import com.github.takahirom.roborazzi.AndroidComposePreviewTester
import com.github.takahirom.roborazzi.ComposePreviewTester
import com.github.takahirom.roborazzi.ExperimentalRoborazziApi
import org.junit.rules.RuleChain

@Suppress("UNUSED")
@OptIn(ExperimentalRoborazziApi::class)
class FakeImageLoaderComposePreviewTester :
    ComposePreviewTester<ComposePreviewTester.TestParameter<*>> {

    private val delegate = AndroidComposePreviewTester()

    override fun options(): ComposePreviewTester.Options {
        val defaultLifecycleOptions = delegate.options().testLifecycleOptions
                as ComposePreviewTester.Options.JUnit4TestLifecycleOptions

        return delegate.options().copy(
            testLifecycleOptions = defaultLifecycleOptions.copy(
                testRuleFactory = { composeTestRule ->
                    RuleChain
                        .outerRule(FakeImageLoaderRule({ ApplicationProvider.getApplicationContext() }))
                        .around(defaultLifecycleOptions.testRuleFactory(composeTestRule))
                }
            )
        )
    }

    override fun testParameters(): List<ComposePreviewTester.TestParameter<*>> =
        delegate.testParameters()

    override fun test(testParameter: ComposePreviewTester.TestParameter<*>) {
        delegate.test(testParameter as ComposePreviewTester.TestParameter.JUnit4TestParameter.AndroidPreviewJUnit4TestParameter)
    }
}