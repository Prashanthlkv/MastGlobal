package com.test.mastglobal;

import android.app.Activity;
import android.app.Application;

import com.test.mastglobal.di.AppComponent;
import com.test.mastglobal.di.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class MastGlobalApplication extends Application implements HasActivityInjector{

    private AppComponent mAppComponent;

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        getApplicationComponent().inject(this);
    }


    public AppComponent getApplicationComponent(){
        if(mAppComponent == null){
            mAppComponent = DaggerAppComponent.builder().application(this)
                    .build();
        }
        return mAppComponent;
    }


    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}
