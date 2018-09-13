package com.magarex.bigchef.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.magarex.bigchef.R;
import com.magarex.bigchef.model.Recipe;
import com.magarex.bigchef.model.Step;
import com.magarex.bigchef.ui.exoplayer.ExoPlayerActivity;
import com.magarex.bigchef.ui.exoplayer.ExoPlayerFragment;
import com.magarex.bigchef.ui.main.RecipeActivity;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasFocus;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.magarex.bigchef.utils.TestDataGenerator.getMockRecipe;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class ExoPlayerActivityTest {

    private static final String RECIPE_NAME = "Cheesecake";
    private static final int STEP_POSITION = 2;
    private Recipe recipe;

    @Rule
    public ActivityTestRule<ExoPlayerActivity> mActivityTestRule = new ActivityTestRule<ExoPlayerActivity>(ExoPlayerActivity.class) {
        @Override
        protected Intent getActivityIntent() {
            Context targetContext = InstrumentationRegistry
                    .getInstrumentation()
                    .getTargetContext();
            recipe = getMockRecipe();
            Intent intent = new Intent(targetContext, ExoPlayerActivity.class);
            Bundle recipeData = new Bundle();
            recipeData.putParcelableArrayList("step", (ArrayList<Step>) recipe.getSteps());
            recipeData.putInt("stepPosition", STEP_POSITION);
            recipeData.putString("recipeName", recipe.getName());
            intent.putExtras(recipeData);
            return intent;
        }
    };

    @Test
    public void checkCurrentStepDisplayed() {
        onView(withId(R.id.currentStep))
                .check(matches(isDisplayed()))
                .check(matches(withText("Step 3")));
    }

    @Test
    public void checkRecipeNameDisplayed() {
        onView(withId(R.id.toolbarRecipe)).check(matches(isDisplayed()));
        onView(withText(RECIPE_NAME)).
                check(matches(withParent(withId(R.id.toolbarRecipe))));
    }

    @Test
    public void checkViewPagerDisplayed() {
        onView(withId(R.id.fragmentViewPager))
                .check(matches(isDisplayed()));
    }

    @Test
    public void phone_checkStepShortDescriptionDisplayed() {
        // Step description should be displayed
        onView(allOf(withId(R.id.tvShortDescription), isDisplayed()))
                .check(matches(isDisplayed()))
                .check(matches(withText(recipe.getSteps().get(STEP_POSITION).getShortDescription())));
    }

    @Test
    public void phone_checkStepDescriptionDisplayed() {
        // Step short description should be displayed
        onView(allOf(withId(R.id.tvDescription), isDisplayed()))
                .check(matches(isDisplayed()))
                .check(matches(withText(recipe.getSteps().get(STEP_POSITION).getDescription().substring(3))));
    }

    @After
    public void tearDownResources() {
        recipe = null;
    }

}
