package com.test.mastglobal.ui.userrepos;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import android.util.Log;

import com.test.mastglobal.data.api.Datamanager;
import com.test.mastglobal.vo.UserRepo;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

public class UserRepoViewModel extends AndroidViewModel{

    private Datamanager mDataManager;
    private MutableLiveData<List<UserRepo>> userInfoResponseMutableLiveData = new MutableLiveData<>();


    @Inject
    public UserRepoViewModel(@NonNull Application application , Datamanager dataManager) {
        super(application);
        mDataManager = dataManager;
    }


    public LiveData<List<UserRepo>>getUserInfoLiveData(){
        return userInfoResponseMutableLiveData;
    }

    /**
     * Method to get the user info from NW.
     */
    public void getUserRepos(){
        mDataManager.getUserRepoObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSubscriber<List<UserRepo>>() {
                    @Override
                    public void onNext(List<UserRepo> o) {

                        if(o != null){
                            Log.i("RESPONSE" , "NOT NULL");
                            userInfoResponseMutableLiveData.setValue(o);
                        }else {
                            Log.i("RESPONSE" , "NULL");
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.i("RESPONSE" , "ERROR=="+t.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public static class UserRepoViewModelFactory implements ViewModelProvider.Factory {
        UserRepoViewModel mViewModel;

        @Inject
        public UserRepoViewModelFactory(UserRepoViewModel viewModel) {
            this.mViewModel = viewModel;
        }

        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(UserRepoViewModel.class)) {
                return (T) mViewModel;
            }
            throw new IllegalArgumentException("Unknown class name");
        }
    }

}
