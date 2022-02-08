package com.example.maxtap_sdk;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.brightcove.player.controller.VideoPlaybackController;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class BrightcoveTest {
    int []ad_times ={3,15,26};
    @Rule
    public ActivityScenarioRule<BrightcovePlayerIntegration> activityRule = new ActivityScenarioRule<>(BrightcovePlayerIntegration.class);

    @Test
    public void AdsTest() {
        onView(withId(R.id.video_player)).perform(click());
        activityRule.getScenario().onActivity(activity -> {
            for (int ad_time: ad_times) {
                activity.getBrightcoveVideoView().seekTo(ad_time*1000);
            }
            onView(withId(R.id.maxtap_container_id)).perform(click());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });
    }
}
