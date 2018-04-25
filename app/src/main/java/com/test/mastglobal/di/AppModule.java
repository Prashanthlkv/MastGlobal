package com.test.mastglobal.di;


import android.app.Application;
import android.content.Context;

import com.test.mastglobal.data.api.ApiHelper;
import com.test.mastglobal.data.api.AppApiHelper;
import com.test.mastglobal.data.api.AppDataManager;
import com.test.mastglobal.data.api.Datamanager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;



/**
 * Created by Prashanth on 05-12-2017.
 */
@Module
public class AppModule {

    @Provides
    @Singleton
    Context provideApplicationContext(Application application) {
        return application;
    }

    @Singleton @Provides
    ApiHelper provideApiHelper(AppApiHelper appApiHelper){
        return appApiHelper;
    }


    @Singleton @Provides
    Datamanager getDataManager(AppDataManager appDataManager){
        return appDataManager;
    }

}
