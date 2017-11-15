package com.example.rechee.namegame;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.example.rechee.namegame.network.PersonNetworkRepository;
import com.example.rechee.namegame.network.model.Headshot;
import com.example.rechee.namegame.network.model.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Rechee on 11/11/2017.
 */

public class MainGridViewModel extends ViewModel {

    private final PersonRepository personRepository;
    private LiveData<Person[]> allPeople;
    private Random random;
    private LiveData<Person[]> currentRandomPeople;
    private LiveData<Person> currentRandomPerson;

    public MainGridViewModel(PersonRepository personRepository){
        this.personRepository = personRepository;
        init();
    }

    public MainGridViewModel(){
        //this constructor is called by the android framework's ViewModelProvider
        this.personRepository = new PersonNetworkRepository();
        init();
    }

    private void init() {
        random = new Random();
        this.allPeople = getAllPeople();
    }

    private LiveData<Person[]> getAllPeople() {
        if(this.allPeople == null){
            this.allPeople = personRepository.getAllPeople();
        }

        return allPeople;
    }

    public LiveData<Person[]> getRandomPeople(final int n){

        if(currentRandomPeople != null && currentRandomPeople.getValue() != null && currentRandomPeople.getValue().length == n){
            return currentRandomPeople;
        }

        currentRandomPeople = Transformations.map(allPeople, new Function<Person[], Person[]>() {
            @Override
            public Person[] apply(Person[] input) {
                if(n < 0 || n > input.length){
                    return new Person[0];
                }

                List<Person> randomPeople = new ArrayList<>(n);

                int i = 0;

                while(i < n){
                    int randomIndex = random.nextInt(input.length);
                    if(randomPeople.contains(input[randomIndex])){
                        continue;
                    }

                    final Headshot headshot = input[randomIndex].getHeadshot();
                    if(headshot == null || headshot.getUrl() == null || headshot.getUrl().isEmpty()){
                        continue;
                    }

                    randomPeople.add(input[randomIndex]);
                    i++;
                }

                return randomPeople.toArray(new Person[randomPeople.size()]);
            }
        });

        return currentRandomPeople;
    }

    public LiveData<Person> getRandomPersonToChoose(){

        if(this.currentRandomPerson != null){
            return currentRandomPerson;
        }

        this.currentRandomPerson =  Transformations.map(currentRandomPeople, new Function<Person[], Person>() {
            @Override
            public Person apply(Person[] input) {
                int randomIndex = random.nextInt(input.length);
                return input[randomIndex];
            }
        });

        return currentRandomPerson;
    }
}
