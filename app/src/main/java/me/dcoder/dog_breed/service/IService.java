package me.dcoder.dog_breed.service;

import me.dcoder.dog_breed.model.BreedList;
import retrofit2.Call;
import retrofit2.http.GET;

public interface IService {
    @GET("breeds/list/all")
    Call<BreedList> getBreadList();
}
