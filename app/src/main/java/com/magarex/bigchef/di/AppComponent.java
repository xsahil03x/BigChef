package com.magarex.bigchef.di;

import android.app.Application;

import com.magarex.bigchef.BigChefApp;
import com.magarex.bigchef.di.module.AppModule;
import com.magarex.bigchef.di.module.ActivityBindingModule;
import com.magarex.bigchef.di.module.ContextModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;


@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        ActivityBindingModule.class,
        AppModule.class})
interface AppComponent extends AndroidInjector<BigChefApp> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        AppComponent.Builder application(Application application);

        AppComponent build();
    }
}