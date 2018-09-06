package com.magarex.bigchef.ui.exoplayer;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.magarex.bigchef.R;
import com.magarex.bigchef.databinding.ActivityExoPlayerBinding;
import com.magarex.bigchef.model.Step;
import com.magarex.bigchef.ui.base.BaseActivity;

import java.util.List;
import java.util.Objects;


public class ExoPlayerActivity extends BaseActivity<ActivityExoPlayerBinding> {

    private List<Step> stepList;
    private int position;
    private ExoFragmentPlayerAdapter mAdapter;
    private FragmentManager fragmentManager;
    private ViewPager fragmentViewPager;

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stepList = Objects.requireNonNull(getIntent().getExtras()).getParcelableArrayList("step");
        position = getIntent().getExtras().getInt("position");
        if (stepList != null) {
            getBinding().toolbarRecipe.setTitle(getIntent().getExtras().getString("recipeName"));
            prepareView();
        }
    }

    private void prepareView() {
        fragmentManager = getSupportFragmentManager();
        mAdapter = new ExoFragmentPlayerAdapter(fragmentManager, stepList);
        fragmentViewPager = getBinding().fragmentViewPager;
        fragmentViewPager.setAdapter(mAdapter);
        fragmentViewPager.setOffscreenPageLimit(12);
        fragmentViewPager.setCurrentItem(position);
        fragmentViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                getBinding().currentStep.setText("Step " + (position + 1));
                if (position == 0) {
                    getBinding().btnBack.setEnabled(false);
                } else if (position == stepList.size() - 1) {
                    getBinding().btnNext.setEnabled(false);
                } else {
                    getBinding().btnBack.setEnabled(true);
                    getBinding().btnNext.setEnabled(true);
                }
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected int provideLayout() {
        return R.layout.activity_exo_player;
    }

}
