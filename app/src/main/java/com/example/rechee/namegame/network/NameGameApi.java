package com.example.rechee.namegame.network;

import com.example.rechee.namegame.network.model.Person;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NameGameApi {
    @GET("/api/v1.0/profiles")
    Call<List<Person>> getPeople();
}
