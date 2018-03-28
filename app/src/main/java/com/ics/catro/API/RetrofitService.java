package com.ics.catro.API;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ichsan.Fatiha on 3/9/2018.
 */

public class RetrofitService {
    private static final String url = "http://10.0.2.2/catro/";
    private static Retrofit retrofit = null;

    public static Retrofit service(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        if  (retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

}
