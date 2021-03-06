package com.magarex.bigchef.ui.main;


import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.magarex.bigchef.R;
import com.magarex.bigchef.databinding.RecipeItemBinding;
import com.magarex.bigchef.model.Recipe;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private List<Recipe> mRecipes;
    private RecipeItemClickListener recipeItemClickListener;

    RecipeAdapter(RecipeItemClickListener recipeItemClickListener) {
        this.recipeItemClickListener = recipeItemClickListener;
    }

    public void addRecipeToList(List<Recipe> recipeList) {
        this.mRecipes = recipeList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecipeItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.recipe_item, parent, false);
        return new RecipeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecipeViewHolder holder, int position) {
        holder.mBinding.setRecipe(mRecipes.get(position));
    }

    @Override
    public int getItemCount() {
        if (mRecipes == null) {
            return 0;
        } else {
            return mRecipes.size();
        }
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final RecipeItemBinding mBinding;

        RecipeViewHolder(RecipeItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.mBinding = itemBinding;
            mBinding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            recipeItemClickListener.onClick(mRecipes.get(getAdapterPosition()));
        }
    }
}
