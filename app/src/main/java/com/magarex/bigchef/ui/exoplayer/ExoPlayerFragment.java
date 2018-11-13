package com.magarex.bigchef.ui.exoplayer;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.magarex.bigchef.R;
import com.magarex.bigchef.databinding.FragmentExoPlayerBinding;
import com.magarex.bigchef.model.Step;
import com.magarex.bigchef.ui.base.BaseFragment;

import java.util.Objects;

public class ExoPlayerFragment extends BaseFragment<FragmentExoPlayerBinding> {

    private static final String TABLET_KEY = "isTablet";
    private Step mStep;
    public static final String STEP_KEY = "currentStep";
    private Boolean isTablet = false;
    private VideoPlayerComponent videoPlayerComponent;

    public static ExoPlayerFragment newInstance(Step step, Boolean isTablet) {
        ExoPlayerFragment exoPlayerFragment = new ExoPlayerFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(TABLET_KEY, isTablet);
        bundle.putParcelable(STEP_KEY, step);
        exoPlayerFragment.setArguments(bundle);
        return exoPlayerFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mStep = getArguments().getParcelable(STEP_KEY);
            isTablet = getArguments().getBoolean(TABLET_KEY);
        }
        if (savedInstanceState != null) {
            mStep = savedInstanceState.getParcelable(STEP_KEY);
            isTablet = savedInstanceState.getBoolean(TABLET_KEY);
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mStep != null) {
            if (!isTablet) {
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    Objects.requireNonNull(getActivity()).getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                            WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    getDataBinding().exoGroup.setVisibility(View.GONE);
                    ConstraintLayout.LayoutParams exoParams = (ConstraintLayout.LayoutParams) getDataBinding().recipePlayerView.getLayoutParams();
                    exoParams.height = ConstraintLayout.LayoutParams.MATCH_CONSTRAINT;
                    ConstraintLayout.LayoutParams errorParams = (ConstraintLayout.LayoutParams) getDataBinding().ivError.getLayoutParams();
                    errorParams.height = ConstraintLayout.LayoutParams.MATCH_CONSTRAINT;
                }
            }
            prepareViews();
        } else Toast.makeText(getActivity(), "No Steps Available", Toast.LENGTH_SHORT).show();
    }

    private void prepareViews() {
        videoPlayerComponent = new VideoPlayerComponent(getActivity(), getDataBinding().recipePlayerView, mStep.getVideoURL());
        getDataBinding().setStep(mStep);
        getLifecycle().addObserver(videoPlayerComponent);
    }

    @Override
    protected int provideLayout() {
        return R.layout.fragment_exo_player;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(STEP_KEY, mStep);
        outState.putBoolean(TABLET_KEY, isTablet);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.isVisible()) {
            if (!isVisibleToUser) {
                videoPlayerComponent.getPlayer().setPlayWhenReady(false);
            }
            if (isVisibleToUser) {
                if (videoPlayerComponent.getPlayer().getPlayWhenReady())
                    videoPlayerComponent.getPlayer().setPlayWhenReady(true);
                else
                    videoPlayerComponent.getPlayer().setPlayWhenReady(false);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getLifecycle().removeObserver(videoPlayerComponent);
    }
}