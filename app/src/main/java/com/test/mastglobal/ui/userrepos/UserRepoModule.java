package com.test.mastglobal.ui.userrepos;

import dagger.Module;
import dagger.Provides;

@Module
public class UserRepoModule {

    @Provides
    UserRepoViewModel.UserRepoViewModelFactory provideUserRepoViewModelFactory(UserRepoViewModel viewModel){
        return new UserRepoViewModel.UserRepoViewModelFactory(viewModel);
    }

}
