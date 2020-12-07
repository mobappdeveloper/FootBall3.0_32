package com.footballio.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClientV2 {

    private static String baseUrl = "https://adminpanel.football3.io/v5/api/";
    private static Retrofit retrofit;
    private static FootballService service;

    public static FootballService setRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(FootballService.class);
        return service;
    }

}




