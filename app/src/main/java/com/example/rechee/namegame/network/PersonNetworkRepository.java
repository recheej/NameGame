package com.example.rechee.namegame.network;

import android.arch.lifecycle.MutableLiveData;

import com.example.rechee.namegame.PersonRepository;
import com.example.rechee.namegame.network.model.Person;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Rechee on 11/11/2017.
 */

public class PersonNetworkRepository implements PersonRepository {
    private final Retrofit retrofit;
    private final NameGameApi nameGameApi;
    private String baseUrl = "https://willowtreeapps.com/";

    public PersonNetworkRepository() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient.Builder().build())
                .build();

        this.nameGameApi = this.retrofit.create(NameGameApi.class);
    }

    @Override
    public MutableLiveData<Person[]> getAllPeople() {
        final MutableLiveData<Person[]> data = new MutableLiveData<>();
        nameGameApi.getPeople().enqueue(new Callback<List<Person>>() {
            @Override
            public void onResponse(Call<List<Person>> call, Response<List<Person>> response) {
                List<Person> people = response.body();

                if(people == null){
                    data.setValue(new Person[0]);
                }
                else{
                    data.setValue(people.toArray(new Person[people.size()]));
                }
            }

            @Override
            public void onFailure(Call<List<Person>> call, Throwable t) {
            }
        });

        return data;
    }
}
