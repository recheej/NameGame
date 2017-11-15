package com.example.rechee.namegame;

import android.arch.lifecycle.LiveData;

import com.example.rechee.namegame.network.model.Person;

/**
 * Created by Rechee on 11/11/2017.
 */

public interface PersonRepository {
    LiveData<Person[]> getAllPeople();
}
