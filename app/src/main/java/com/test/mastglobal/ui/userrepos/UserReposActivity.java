package com.test.mastglobal.ui.userrepos;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.test.mastglobal.R;
import com.test.mastglobal.vo.UserRepo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class UserReposActivity extends AppCompatActivity implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentInjector;
    private Unbinder mUnbinder;
    @Inject
    UserRepoViewModel.UserRepoViewModelFactory factory;
    private UserRepoViewModel mViewModel;
    private UserRepoAdapter mAdapter;
    @BindView(R.id.rv_repoinfo)
    RecyclerView mRecyclerView;
    private List<UserRepo>mList = new ArrayList<>();


    /**
     * Method to which returns the current activity intent object.
     * @param context
     * @return
     */
    public static Intent getIntent(Context context){
        return new Intent(context , UserReposActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(this, factory).get(UserRepoViewModel.class);
        setContentView(R.layout.activity_userrepo);
        addObservers();
        mUnbinder = ButterKnife.bind(this);
        mAdapter = new UserRepoAdapter(mList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
        getUserRepos();

    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentInjector;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }


    /**
     * Method which add activity observers to the viewmodel's LiveData
     */
    private void addObservers(){
        mViewModel.getUserInfoLiveData().observe(this, new Observer<List<UserRepo>>() {
            @Override
            public void onChanged(@Nullable List<UserRepo> userInfoResponse) {
                // Update UI
                mList.addAll(userInfoResponse);
                mAdapter.notifyDataSetChanged();
            }
        });
    }


    /**
     * Method to initiate the network call to get the user information.
     */
    private void getUserRepos(){
        mViewModel.getUserRepos();
    }
}
