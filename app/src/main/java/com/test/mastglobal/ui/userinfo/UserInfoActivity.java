package com.test.mastglobal.ui.userinfo;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.test.mastglobal.R;
import com.test.mastglobal.ui.userrepos.UserReposActivity;
import com.test.mastglobal.vo.UserInfoResponse;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class UserInfoActivity extends AppCompatActivity implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentInjector;
    @Inject
    UserInfoViewModel.UserInfoViewModelFactory factory;
    @BindView(R.id.tv_name)
    TextView mTxtName;
    @BindView(R.id.tv_location)
    TextView mTxtLocation;
    @BindView(R.id.tv_email)
    TextView mTxtEmail;
    @BindView(R.id.btn_next)
    Button mBtnNext;
    private UserInfoViewModel mViewModel;
    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(this, factory).get(UserInfoViewModel.class);
        setContentView(R.layout.activity_userinfo);
        mUnbinder = ButterKnife.bind(this);
        addObservers();
        getUserInfo();
        mBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(UserReposActivity.getIntent(UserInfoActivity.this));
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentInjector;
    }


    /**
     * Method which add activity observers to the viewmodel's LiveData
     */
    private void addObservers(){
        mViewModel.getUserInfoLiveData().observe(this, new Observer<UserInfoResponse>() {
            @Override
            public void onChanged(@Nullable UserInfoResponse userInfoResponse) {
                if(userInfoResponse != null){
                    updateUserInfo(userInfoResponse);
                }
            }
        });
    }


    /**
     * Method to initiate the network call to get the user information.
     */
    private void getUserInfo(){
        mViewModel.getUserInfo();
    }

    /**
     * Method to update the user information.
     * @param userInfoResponse
     */
    private void updateUserInfo(UserInfoResponse userInfoResponse){
        mTxtName.setText(getString(R.string.txt_name, userInfoResponse.getName()));
        mTxtLocation.setText(getString(R.string.txt_location, userInfoResponse.getLocation()));
        mTxtEmail.setText(getString(R.string.txt_email, userInfoResponse.getEmail()));
        mBtnNext.setVisibility(View.VISIBLE);
    }

}
