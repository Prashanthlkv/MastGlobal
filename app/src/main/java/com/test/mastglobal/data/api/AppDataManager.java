package com.test.mastglobal.data.api;

import com.test.mastglobal.vo.UserInfoResponse;
import com.test.mastglobal.vo.UserRepo;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;

@Singleton
public class AppDataManager implements Datamanager{

    private ApiHelper mApiHelper;

    @Inject
    public AppDataManager(ApiHelper apiHelper){
        mApiHelper = apiHelper;
    }

    @Override
    public Flowable<UserInfoResponse> getUserInfoObservable() {
        return mApiHelper.getUserInfoObservable();
    }

    @Override
    public Flowable<List<UserRepo>> getUserRepoObservable() {
        return mApiHelper.getUserRepoObservable();
    }
}
