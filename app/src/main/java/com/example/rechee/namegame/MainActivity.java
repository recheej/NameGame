package com.example.rechee.namegame;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rechee.namegame.network.PersonNetworkRepository;
import com.example.rechee.namegame.network.model.Headshot;
import com.example.rechee.namegame.network.model.Person;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private MainGridViewModel viewModel;
    private LinearLayout rootLayout;
    int attempts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        this.rootLayout = findViewById(R.id.headshots_view);

        final TextView personToChooseTextView = findViewById(R.id.person_to_choose);

        this.viewModel = ViewModelProviders.of(this).get(MainGridViewModel.class);
        this.viewModel.getRandomPeople(6).observe(this, new Observer<Person[]>() {
            @Override
            public void onChanged(@Nullable final Person[] people) {
                viewModel.getRandomPersonToChoose().observe(MainActivity.this, new Observer<Person>() {
                    @Override
                    public void onChanged(@Nullable final Person randomPersonToChoose) {
                        personToChooseTextView.setText(randomPersonToChoose.getFirstName() + " " + randomPersonToChoose.getLastName());

                        LinearLayout.LayoutParams imageLayoutParams = new LinearLayout.LayoutParams(
                                0, 300
                        );
                        imageLayoutParams.weight = 1;

                        LinearLayout imageRow = getImageRow(16, 0);
                        MainActivity.this.rootLayout.addView(imageRow);

                        for (int i = 0; i < people.length; i++) {
                            final Person person = people[i];
                            ImageView headshotView = new ImageView(MainActivity.this);
                            headshotView.setId(View.generateViewId());
                            headshotView.setLayoutParams(imageLayoutParams);

                            headshotView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    attempts++;

                                    if(Objects.equals(person.getId(), randomPersonToChoose.getId())){

                                        //go to won game activity, but also save current random person

                                        Intent goToWonGameIntent = new Intent(MainActivity.this, WonGameActivity.class);

                                        Bundle args = new Bundle();
                                        args.putParcelable("person", person);
                                        args.putInt("attempts", attempts);

                                        goToWonGameIntent.putExtras(args);

                                        startActivity(goToWonGameIntent);
                                    }
                                    else{
                                        Toast.makeText(MainActivity.this, R.string.incorrect, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                            final Headshot headshot = person.getHeadshot();

                            Picasso.with(MainActivity.this)
                                    .load(headshot.getUrl())
                                    .fit()
                                    .placeholder(R.drawable.placeholder)
                                    .error(R.drawable.image_not_found)
                                    .into(headshotView);

                            imageRow.addView(headshotView);

                            if((i + 1) % 3 == 0){
                                //add second row of 3 items
                                imageRow = getImageRow(50, 0);
                                MainActivity.this.rootLayout.addView(imageRow);
                            }
                        }
                    }
                });
            }
        });
    }

    @NonNull
    private LinearLayout getImageRow(int topMargin, int bottomMargin) {
        LinearLayout imageRow = new LinearLayout(MainActivity.this);
        final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, topMargin, 0, bottomMargin);

        imageRow.setLayoutParams(params);
        imageRow.setOrientation(LinearLayout.HORIZONTAL);
        imageRow.setId(View.generateViewId());

        return imageRow;
    }
}
