package com.magarex.bigchef.ui.detail;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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

import org.parceler.Parcels;

import java.util.Objects;

public class RecipeDetailActivity extends BaseActivity<ActivityRecipeDetailBinding> implements StepItemClickListener {

    private RecipeIngredientsAdapter ingredientsAdapter;
    private RecipeStepAdapter stepAdapter;
    private Dialog ingredientDialog;


    @Override
    protected int provideLayout() {
        return R.layout.activity_recipe_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Recipe recipe = Parcels.unwrap(getIntent().getParcelableExtra("data"));
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
    public void onClick(Step step) {

    }
}
