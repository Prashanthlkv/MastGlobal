package com.test.mastglobal.data.api;

import com.test.mastglobal.AppConstants;
import com.test.mastglobal.vo.UserInfoResponse;
import com.test.mastglobal.vo.UserRepo;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import io.reactivex.Flowable;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface ApiHelper {

   String BASE_URL = "https://api.github.com/users/";

    @Singleton
    class Creator {
        public static ApiHelper newRetrofit() {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder client = new OkHttpClient.Builder();
            client.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    Request request = original.newBuilder()
                            .header("Content-Type",
                                    "application/json")
                            .method(original.method(), original.body())
                            .build();
                    return chain.proceed(request);
                }
            });

            // add logging as last interceptor
            client.addInterceptor(logging);
            client.connectTimeout(65, TimeUnit.SECONDS);
            client.readTimeout(65, TimeUnit.SECONDS);
            client.writeTimeout(65, TimeUnit.SECONDS);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client.build())
                    .build();
            return retrofit.create(ApiHelper.class);
        }

    }


    @GET(AppConstants.ApiConstants.METHOD_USERINFO)
    Flowable<UserInfoResponse> getUserInfoObservable();

    @GET(AppConstants.ApiConstants.METHOD_USER_REPOS)
    Flowable<List<UserRepo>> getUserRepoObservable();

}
