package com.example.maxtap_sdk;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.util.Log;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ExpoPlayerTest {
    int []ad_times ={3,15,26};
    

    @Rule
    public ActivityScenarioRule<HotstarDemo> activityRule = new ActivityScenarioRule<>(HotstarDemo.class);
    @Test
    public void AdsTest(){

        activityRule.getScenario().onActivity(activity -> {
            Log.i("test_log","start");
            for (int ad_time: ad_times) {
                activity.exoPlayer.seekTo(ad_time*1000L);
                onView(withId(R.id.maxtap_container_id)).check(matches(isDisplayed())).perform(click());
            }

            Log.i("test_log","end");

        });

    }

}
