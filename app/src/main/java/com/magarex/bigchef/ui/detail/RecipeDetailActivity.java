package com.magarex.bigchef.ui.detail;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.magarex.bigchef.R;
import com.magarex.bigchef.databinding.ActivityRecipeDetailBinding;
import com.magarex.bigchef.model.Recipe;
import com.magarex.bigchef.model.Step;
import com.magarex.bigchef.ui.base.BaseActivity;
import com.magarex.bigchef.ui.exoplayer.ExoPlayerActivity;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RecipeDetailActivity extends BaseActivity<ActivityRecipeDetailBinding> implements StepItemClickListener {

    private RecipeIngredientsAdapter ingredientsAdapter;
    private RecipeStepAdapter stepAdapter;
    private Dialog ingredientDialog;
    private Recipe recipe;


    @Override
    protected int provideLayout() {
        return R.layout.activity_recipe_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recipe = Parcels.unwrap(getIntent().getParcelableExtra("recipe"));
        if (recipe != null) {
            ingredientDialog = new Dialog(this);
            prepareStepsRecyclerView();
            prepareIngredientRecyclerView();
            getBinding().setRecipe(recipe);
            ingredientsAdapter.addIngredientsToList(recipe.getIngredients());
            stepAdapter.addIngredientsToList(recipe.getSteps());
        }

        getBinding().fabIngredient.setOnClickListener(v -> {
            ingredientDialog.show();
        });
    }


    private void prepareIngredientRecyclerView() {
        ingredientDialog.setContentView(R.layout.dialog_recipe_ingredients);
        ingredientDialog.setCancelable(true);
        ingredientDialog.setCanceledOnTouchOutside(true);
        ingredientDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ingredientsAdapter = new RecipeIngredientsAdapter();
        RecyclerView rvIngredients = ingredientDialog.findViewById(R.id.rvIngredients);
        rvIngredients.setLayoutManager(new LinearLayoutManager(this));
        rvIngredients.setItemAnimator(new DefaultItemAnimator());
        rvIngredients.setAdapter(ingredientsAdapter);
        rvIngredients.setHasFixedSize(true);
        Objects.requireNonNull(ingredientDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private void prepareStepsRecyclerView() {
        stepAdapter = new RecipeStepAdapter(this);
        RecyclerView rvSteps = getBinding().rvSteps;
        rvSteps.setLayoutManager(new LinearLayoutManager(this));
        rvSteps.setItemAnimator(new DefaultItemAnimator());
        rvSteps.setAdapter(stepAdapter);
        rvSteps.setHasFixedSize(true);
    }

    @Override
    public void onClick(ArrayList<Step> step, int position) {
        Intent intent = new Intent(this, ExoPlayerActivity.class);
        Bundle recipeData = new Bundle();
        recipeData.putParcelableArrayList("step", step);
        recipeData.putInt("position", position);
        recipeData.putString("recipeName", recipe.getName());
        intent.putExtras(recipeData);
        startActivity(intent);
    }
}
