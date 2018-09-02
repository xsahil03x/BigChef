package com.magarex.bigchef.ui.main;

import android.content.Context;
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

class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    final RecipeItemBinding mBinding;
    private RecipeItemClickListener itemClickListener;

    RecipeViewHolder(RecipeItemBinding itemBinding) {
        super(itemBinding.getRoot());
        this.mBinding = itemBinding;
        mBinding.getRoot().setOnClickListener(this);
    }

    public void setItemClickListener(RecipeItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition());
    }
}

public class RecipeAdapter extends RecyclerView.Adapter<RecipeViewHolder> {

    private final Context mContext;
    private List<Recipe> mRecipes;

    RecipeAdapter(Context context) {
        this.mContext = context;
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
        holder.setItemClickListener((view, position1) -> {
            // TODO : Navigate to next screen
        });
    }

    @Override
    public int getItemCount() {
        if (mRecipes == null) {
            return 0;
        } else {
            return mRecipes.size();
        }
    }
}
