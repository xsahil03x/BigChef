package com.magarex.bigchef.ui.exoplayer;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

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
    private int orientation;

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stepList = Objects.requireNonNull(getIntent().getExtras()).getParcelableArrayList("step");
        position = getIntent().getExtras().getInt("position");
        orientation = getResources().getConfiguration().orientation;
        if (stepList != null) {
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                getBinding().groupNavigation.setVisibility(View.GONE);
                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) getBinding().fragmentViewPager.getLayoutParams();
                params.height = ConstraintLayout.LayoutParams.MATCH_PARENT;
            }
            if (getBinding().toolbarRecipe != null) {
                getBinding().toolbarRecipe.setTitle(getIntent().getExtras().getString("recipeName"));
            }
            prepareView();
        }
    }

    @Override
    public void onBackPressed() {
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else super.onBackPressed();
    }

    private void prepareView() {
        fragmentManager = getSupportFragmentManager();
        mAdapter = new ExoFragmentPlayerAdapter(fragmentManager, stepList);
        fragmentViewPager = getBinding().fragmentViewPager;
        fragmentViewPager.setAdapter(mAdapter);
        fragmentViewPager.setOffscreenPageLimit(0);
        fragmentViewPager.setCurrentItem(position);
        fragmentViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (getBinding().currentStep != null) {
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

    public void onBack(View view) {
        if (fragmentViewPager.getCurrentItem() != 0) {
            fragmentViewPager.setCurrentItem(fragmentViewPager.getCurrentItem() - 1);
        }
    }

    public void onNext(View view) {
        if (fragmentViewPager.getCurrentItem() != stepList.size() - 1) {
            fragmentViewPager.setCurrentItem(fragmentViewPager.getCurrentItem() + 1);
        }
    }
}
