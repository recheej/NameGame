package com.example.rechee.namegame;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.rechee.namegame.network.model.Person;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(RobolectricTestRunner.class)
public class MainGridViewModelTest {

    @Rule public InstantTaskExecutorRule  rule = new InstantTaskExecutorRule();

    private MainGridViewModel mainGridViewModel;

    @Test
    public void basicRandomPeopleTest() throws Exception {

        mainGridViewModel = new MainGridViewModel(new TestPersonRepository());
        LiveData<Person[]> randomPeople = mainGridViewModel.getRandomPeople(1);

        randomPeople.observeForever(new Observer<Person[]>() {
            @Override
            public void onChanged(@Nullable Person[] people) {
                assertNotNull(people);
                assertEquals(1, people.length);
            }
        });
    }

    @Test
    public void randomPeopleReturnsBlankArrayForOutOfIndexParameter() throws Exception {

        mainGridViewModel = new MainGridViewModel(new TestPersonRepository());
        LiveData<Person[]> randomPeople = mainGridViewModel.getRandomPeople(-1);

        randomPeople.observeForever(new Observer<Person[]>() {
            @Override
            public void onChanged(@Nullable Person[] people) {
                assertNotNull(people);
                assertEquals(0, people.length);
            }
        });
    }
}