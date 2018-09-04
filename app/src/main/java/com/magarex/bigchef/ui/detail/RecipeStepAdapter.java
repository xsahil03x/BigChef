package com.magarex.bigchef.ui.detail;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.magarex.bigchef.R;
import com.magarex.bigchef.databinding.StepsItemBinding;
import com.magarex.bigchef.model.Step;

import java.util.List;

public class RecipeStepAdapter extends RecyclerView.Adapter<RecipeStepAdapter.StepViewHolder> {

    private List<Step> mSteps;
    private StepItemClickListener stepItemClickListener;

    RecipeStepAdapter(StepItemClickListener stepItemClickListener) {
        this.stepItemClickListener = stepItemClickListener;
    }

    public void addIngredientsToList(List<Step> stepList) {
        this.mSteps = stepList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        StepsItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.steps_item, parent, false);
        return new StepViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final StepViewHolder holder, int position) {
        holder.mBinding.setStep(mSteps.get(position));
    }

    @Override
    public int getItemCount() {
        if (mSteps == null) {
            return 0;
        } else {
            return mSteps.size();
        }
    }

    class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final StepsItemBinding mBinding;

        StepViewHolder(StepsItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.mBinding = itemBinding;
            mBinding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            stepItemClickListener.onClick(mSteps.get(getAdapterPosition()));
        }
    }
}

