package com.test.mastglobal.ui.userinfo;

import dagger.Module;
import dagger.Provides;

@Module
public class UserInfoModule {

    @Provides
    UserInfoViewModel.UserInfoViewModelFactory provideUserInfoViewModelFactory(UserInfoViewModel viewModel){
        return new UserInfoViewModel.UserInfoViewModelFactory(viewModel);
    }

}
