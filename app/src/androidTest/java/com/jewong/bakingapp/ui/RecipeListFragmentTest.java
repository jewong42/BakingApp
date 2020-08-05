package com.jewong.bakingapp.ui;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.SystemClock;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.jewong.bakingapp.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class RecipeListFragmentTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void TestRecipeListFragment() {
        ConnectivityManager connectivityManager = (ConnectivityManager) activityRule
                .getActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        boolean isConnected = networkInfo != null && networkInfo.isConnected();
        SystemClock.sleep(2000);

        if (isConnected) {
            onView(withId(R.id.recipe_recycler_view))
                    .check(matches(isDisplayed()));
        } else {
            onView(withId(R.id.recipe_recycler_view))
                    .check(matches((not(isDisplayed()))));
            onView(withId(R.id.error_layout))
                    .check(matches(isDisplayed()));
        }
    }


}
