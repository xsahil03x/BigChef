package com.magarex.bigchef.ui.detail;

import android.os.Bundle;

import com.magarex.bigchef.R;
import com.magarex.bigchef.databinding.ActivityRecipeDetailBinding;
import com.magarex.bigchef.ui.base.BaseActivity;

public class RecipeDetailActivity extends BaseActivity<ActivityRecipeDetailBinding> {

    @Override
    protected int provideLayout() {
        return R.layout.activity_recipe_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
