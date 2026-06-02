package com.alexzh.moodtracker.screenshottests.composepreviewscanner.roborazzi

import com.github.takahirom.roborazzi.AndroidComposePreviewTester
import com.github.takahirom.roborazzi.ComposePreviewTester
import com.github.takahirom.roborazzi.ExperimentalRoborazziApi
import com.github.takahirom.roborazzi.captureRoboImage
import com.github.takahirom.roborazzi.inspectionMode

@Suppress("UNUSED")
@OptIn(ExperimentalRoborazziApi::class)
class InspectionModeComposePreviewTester :
    ComposePreviewTester<ComposePreviewTester.TestParameter<*>> {

    private val delegate = AndroidComposePreviewTester(
        capturer = { parameter ->
            parameter.preview.captureRoboImage(
                filePath = parameter.filePath,
                roborazziOptions = parameter.roborazziOptions,
                roborazziComposeOptions = parameter.roborazziComposeOptions.builder()
                    .inspectionMode(true)
                    .build()
            )
        }
    )

    override fun options() = delegate.options()

    override fun testParameters(): List<ComposePreviewTester.TestParameter<*>> =
        delegate.testParameters()

    override fun test(testParameter: ComposePreviewTester.TestParameter<*>) {
        delegate.test(testParameter as ComposePreviewTester.TestParameter.JUnit4TestParameter.AndroidPreviewJUnit4TestParameter)
    }
}
