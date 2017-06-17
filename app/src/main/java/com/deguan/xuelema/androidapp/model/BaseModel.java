package com.deguan.xuelema.androidapp.model;


import modle.MyHttp.Teacher_http;
import modle.MyUrl;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dell on 2016/4/5.
 */
public class BaseModel {
    public Teacher_http service;
    public BaseModel(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MyUrl.URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(Teacher_http.class);
    }
}
