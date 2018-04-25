package com.test.mastglobal.di;




import com.test.mastglobal.ui.userinfo.UserInfoActivity;
import com.test.mastglobal.ui.userinfo.UserInfoModule;
import com.test.mastglobal.ui.userrepos.UserRepoModule;
import com.test.mastglobal.ui.userrepos.UserReposActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Prashanth on 05-12-2017.
 */
@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(modules = {UserInfoModule.class})
    abstract UserInfoActivity contributeUserInfoActivity();

    @ContributesAndroidInjector(modules = {UserRepoModule.class})
    abstract UserReposActivity contributeUserRepoActivity();
}
