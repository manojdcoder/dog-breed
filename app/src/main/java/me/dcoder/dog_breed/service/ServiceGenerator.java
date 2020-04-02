package me.dcoder.dog_breed.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    private  static IService service;

    private static void initialize(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dog.ceo/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(IService.class);
    }

    public  static IService getService(){
        if(service == null){
            initialize();
        }
        return service;
    }
}
