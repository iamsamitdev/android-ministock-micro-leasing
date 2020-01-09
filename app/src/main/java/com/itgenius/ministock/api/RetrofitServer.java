package com.itgenius.ministock.api;

import com.itgenius.ministock.utils.BaseUrlApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitServer {
    private static Retrofit retrofit;

    public static Retrofit getClient(){

        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(getBaseUrlApi(BaseUrlApi.baseURLAPI))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }

    private static String getBaseUrlApi(String base_url){
        return base_url;
    }
}

