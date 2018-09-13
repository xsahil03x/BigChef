package com.magarex.bigchef.ui.detail;

import android.os.Bundle;
import android.view.WindowManager;


import com.magarex.bigchef.R;
import com.magarex.bigchef.databinding.ActivityRecipeDetailBinding;
import com.magarex.bigchef.model.Recipe;
import com.magarex.bigchef.model.Step;
import com.magarex.bigchef.ui.base.BaseActivity;
import com.magarex.bigchef.ui.exoplayer.ExoPlayerFragment;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Objects;

public class RecipeDetailActivity extends BaseActivity<ActivityRecipeDetailBinding> {

    private Boolean isTablet;
    private RecipeDetailFragment recipeDetailFragment;
    private ExoPlayerFragment exoPlayerFragment;
    private ArrayList<Step> steps;

    @Override
    protected int provideLayout() {
        return R.layout.activity_recipe_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Recipe recipe = Parcels.unwrap(getIntent().getParcelableExtra("recipe"));
        if (getBinding().toolbarRecipeDetail != null) {
            getBinding().toolbarRecipeDetail.setTitle(recipe.getName());
        }
        steps = (ArrayList<Step>) recipe.getSteps();
        recipeDetailFragment = new RecipeDetailFragment();
        recipeDetailFragment.setRecipe(recipe);
        isTablet = getBinding().frameExoPlayer != null;
        if (!isTablet) {
            replaceRecipeDetailFrame();
        } else {
            exoPlayerFragment = new ExoPlayerFragment();
            replaceRecipeDetailFrame();
            replaceExoPlayerFrame();
        }
    }

    private void replaceExoPlayerFrame() {
        exoPlayerFragment.setStep(steps.get(0));
        exoPlayerFragment.setTablet(isTablet);
        this.getSupportFragmentManager().beginTransaction()
                .replace(getBinding().frameExoPlayer.getId(), exoPlayerFragment)
                .commit();
    }

    private void replaceRecipeDetailFrame() {
        recipeDetailFragment.setTablet(isTablet);
        this.getSupportFragmentManager().beginTransaction()
                .replace(getBinding().frameRecipeDetail.getId(), recipeDetailFragment)
                .commit();
    }
}
