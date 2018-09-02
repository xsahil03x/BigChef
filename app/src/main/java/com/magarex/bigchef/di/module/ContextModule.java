package com.magarex.bigchef.di.module;

import android.content.Context;

import com.magarex.bigchef.di.scope.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {
    private Context context;

    public ContextModule(Context context) {
        this.context = context;
    }

    @ApplicationContext
    @Provides
    @Singleton
    public Context context() {
        return context.getApplicationContext();
    }
}
