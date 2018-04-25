package com.test.mastglobal.ui.userinfo;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.test.mastglobal.data.api.Datamanager;
import com.test.mastglobal.vo.UserInfoResponse;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

public class UserInfoViewModel extends AndroidViewModel{

    private Datamanager mDataManager;
    private MutableLiveData<UserInfoResponse> userInfoResponseMutableLiveData = new MutableLiveData<>();

    @Inject
    public UserInfoViewModel(@NonNull Application application ,  Datamanager dataManager) {
        super(application);
        mDataManager = dataManager;
    }


    public LiveData<UserInfoResponse>getUserInfoLiveData(){
        return userInfoResponseMutableLiveData;
    }

    /**
     * Method to get the user info from NW.
     */
    public void getUserInfo(){
        mDataManager.getUserInfoObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSubscriber<UserInfoResponse>() {
                    @Override
                    public void onNext(UserInfoResponse userInfoResponse) {
                        userInfoResponseMutableLiveData.setValue(userInfoResponse);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    public static class UserInfoViewModelFactory implements ViewModelProvider.Factory {
        UserInfoViewModel mViewModel;

        @Inject
        public UserInfoViewModelFactory(UserInfoViewModel viewModel) {
            this.mViewModel = viewModel;
        }

        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(UserInfoViewModel.class)) {
                return (T) mViewModel;
            }
            throw new IllegalArgumentException("Unknown class name");
        }
    }

}
