package com.example.rechee.namegame;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.provider.Contacts;

import com.example.rechee.namegame.network.model.Headshot;
import com.example.rechee.namegame.network.model.Person;
import com.example.rechee.namegame.network.model.SocialLink;

import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;

/**
 * Created by Rechee on 11/11/2017.
 */

public class TestPersonRepository implements PersonRepository {

    @Override
    public LiveData<Person[]> getAllPeople() {
        MutableLiveData<Person[]> data = new MutableLiveData<>();

        Person[] people = new Person[2];
        people[0] = new Person("1",
                "people",
                "rechee-jozil",
                "Software Engineer",
                "Rechee",
                "Jozil",
                new Headshot("image", "image/jpeg", "1",
                        "//images.contentful.com/3cttzl4i3k1h/4Mv2CONANym46UwuuCIgK/cbeb43c93a843a43c07b1de9954795e2/headshot_joel_garrett.jpg",
                        "headshot rechee jozil",
                        340, 340),
                new SocialLink[0]
                );

        people[1] = new Person("2",
                "people",
                "jon-snow",
                "King of the North",
                "jon",
                "snow",
                new Headshot("image", "image/jpeg", "2",
                        "http://i.dailymail.co.uk/i/pix/2016/06/15/02/26FC2E3000000578-3641721-Snow_s_doppelganger_goes_by_the_name_of_facialfollicles_on_Insta-a-16_1465952970225.jpg",
                        "headshot jon snow",
                        634, 629),
                new SocialLink[0]
        );

        people[1] = new Person("3",
                "people",
                "rob-snow",
                "(Former) King of the North",
                "rob",
                "snow",
                new Headshot("image", "image/jpeg", "3",
                        "https://static.comicvine.com/uploads/original/12/125032/2945115-robb_stark.jpg",
                        "headshot rob snow",
                        225, 200),
                new SocialLink[0]
        );

        data.setValue(people);
        return data;
    }
}
