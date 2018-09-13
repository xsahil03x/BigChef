package com.magarex.bigchef.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.magarex.bigchef.R;
import com.magarex.bigchef.model.Recipe;
import com.magarex.bigchef.ui.detail.RecipeDetailActivity;
import com.magarex.bigchef.ui.detail.RecipeDetailFragment;
import com.magarex.bigchef.utils.EspressoTestUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.parceler.Parcels;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.magarex.bigchef.utils.TestDataGenerator.getMockRecipe;

@RunWith(AndroidJUnit4.class)
public class RecipeDetailActivityTest {

    private static final String RECIPE_NAME = "Cheesecake";
    private RecipeDetailFragment recipeDetailFragment;
    private Recipe recipe;

    @Rule
    public ActivityTestRule<RecipeDetailActivity> mActivityTestRule = new ActivityTestRule<RecipeDetailActivity>(RecipeDetailActivity.class) {
        @Override
        protected Intent getActivityIntent() {
            Context targetContext = InstrumentationRegistry
                    .getInstrumentation()
                    .getTargetContext();
            recipe = getMockRecipe();
            Intent intent = new Intent(targetContext, RecipeDetailActivity.class);
            Bundle recipeData = new Bundle();
            recipeData.putParcelable("recipe", Parcels.wrap(getMockRecipe()));
            intent.putExtras(recipeData);
            return intent;
        }
    };

    @Before
    public void setupFragment() {
        EspressoTestUtil.disableAnimations(mActivityTestRule);
        recipeDetailFragment = new RecipeDetailFragment();
        recipeDetailFragment.setRecipe(recipe);
        mActivityTestRule.getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameRecipeDetail, recipeDetailFragment)
                .commit();
    }

    @Test
    public void checkIngredientListDisplayed() {
        onView(withId(R.id.fabIngredient)).perform(click());
        onView(withId(R.id.rvIngredients))
                .check(matches(isDisplayed()))
                .check(matches(hasMinimumChildCount(1)));
    }

    @Test
    public void checkIngredientsLabelDisplayed() {
        onView(withId(R.id.textView4))
                .check(matches(isDisplayed()))
                .check(matches(withText("Preparation Steps")));
    }

    @Test
    public void checkRecipeNameDisplayed() {
        onView(withId(R.id.tvRecipeName))
                .check(matches(isDisplayed()))
                .check(matches(withText(RECIPE_NAME)));
    }

    @Test
    public void checkStepListDisplayed() {
        // At least 1 ingredient displayed
        onView(withId(R.id.rvSteps))
                .check(matches(isDisplayed()))
                .check(matches(hasMinimumChildCount(1)));
    }

    @Test
    public void clickStepItem_OpensExoPlayerActivity() {
        onView(ViewMatchers.withId(R.id.rvSteps))
                .perform(actionOnItemAtPosition(1,
                        click()));
        onView(withId(R.id.toolbarRecipe)).check(matches(withText(RECIPE_NAME)));
    }

    @Test
    public void phone_checkExoPlayerNotDisplayed() {
        // Step description should not be displayed
        onView(withId(R.id.tvDescription))
                .check(doesNotExist());
    }

    @After
    public void tearDownResources() {
        recipe = null;
        mActivityTestRule.getActivity().getSupportFragmentManager().beginTransaction().remove(recipeDetailFragment).commit();
    }
}
