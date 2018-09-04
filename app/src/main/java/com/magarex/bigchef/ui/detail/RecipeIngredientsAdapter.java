package com.magarex.bigchef.ui.detail;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.magarex.bigchef.R;
import com.magarex.bigchef.databinding.IngredientItemBinding;
import com.magarex.bigchef.databinding.StepsItemBinding;
import com.magarex.bigchef.model.Ingredient;
import com.magarex.bigchef.model.Step;

import java.util.List;

public class RecipeIngredientsAdapter extends RecyclerView.Adapter<RecipeIngredientsAdapter.IngredientsViewHolder> {

    private List<Ingredient> mIngredients;

    public void addIngredientsToList(List<Ingredient> ingredientList) {
        this.mIngredients = ingredientList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        IngredientItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.ingredient_item, parent, false);
        return new IngredientsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final IngredientsViewHolder holder, int position) {
        holder.mBinding.setIngredient(mIngredients.get(position));
    }

    @Override
    public int getItemCount() {
        if (mIngredients == null) {
            return 0;
        } else {
            return mIngredients.size();
        }
    }

    class IngredientsViewHolder extends RecyclerView.ViewHolder {

        final IngredientItemBinding mBinding;

        IngredientsViewHolder(IngredientItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.mBinding = itemBinding;
        }
    }
}

