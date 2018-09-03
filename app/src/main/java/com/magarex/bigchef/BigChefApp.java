package com.magarex.bigchef;

import com.magarex.bigchef.di.DaggerAppComponent;
import com.squareup.leakcanary.LeakCanary;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import timber.log.Timber;

public class BigChefApp extends DaggerApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        setupLeakCanary();
        // Plant Timber
        Timber.plant(new Timber.DebugTree());
    }

    private void setupLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(BigChefApp.this).build();
    }
}
