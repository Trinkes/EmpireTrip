package com.redphoenix.empire.trip


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@LargeTest
@RunWith(AndroidJUnit4::class)
class OpenTripDetailsFlowTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun openTripDetailsFlowTest() {
        val testScheduler =
            (mActivityTestRule.activity.application as TestApplication).testScheduler
        testScheduler.triggerActions()
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        onView(withId(R.id.loading_layout)).check(matches(isDisplayed()))
        testScheduler.advanceTimeBy(2, TimeUnit.SECONDS)
        testScheduler.triggerActions()

        onView(
            allOf(
                withId(R.id.pilot_name),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.list),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        ).check(matches(withText(pilotName)))

        onView(
            allOf(
                withText("Last Trips"), childAtPosition(
                    childAtPosition(
                        IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java),
                        0
                    ),
                    0
                )
            )
        ).check(matches(isDisplayed()))

        val constraintLayout = onView(
            allOf(
                childAtPosition(
                    allOf(
                        withId(R.id.list),
                        childAtPosition(
                            withClassName(`is`("android.widget.LinearLayout")),
                            4
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        constraintLayout.perform(click())

        testScheduler.triggerActions()
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)

        onView(withId(R.id.loading_layout)).check(matches(isDisplayed()))

        testScheduler.triggerActions()
        testScheduler.advanceTimeBy(2, TimeUnit.SECONDS)

        val textView3 = onView(
            allOf(
                withId(R.id.fragment_details_pilot_name), isDisplayed()
            )
        )
        textView3.check(matches(withText("pilot_name")))

        val textView4 =
            onView(allOf(withId(R.id.fragment_details_pick_up_label), isDisplayed()))
        textView4.check(matches(withText(R.string.fragment_details_pick_up_label)))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
