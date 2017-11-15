package com.example.rechee.namegame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.LayoutDirection;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.rechee.namegame.network.model.Person;
import com.example.rechee.namegame.network.model.SocialLink;

import org.w3c.dom.Text;

public class WonGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_won_game);

        TextView personNameTextView = findViewById(R.id.person_name);
        TextView titleTextView = findViewById(R.id.value_title);
        TextView attemptsTextView = findViewById(R.id.value_attempts);
        RelativeLayout container = findViewById(R.id.container);

        Button newGameButton = findViewById(R.id.button_new_game);
        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WonGameActivity.this, MainActivity.class));
            }
        });

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            Person person = extras.getParcelable("person");

            int attempts = extras.getInt("attempts", 1);
            attemptsTextView.setText(Integer.toString(attempts));

            if(person != null){
                personNameTextView.setText(person.getFirstName() + " " + person.getLastName());
                titleTextView.setText(person.getJobTitle());

                addSocialLinksToView(container, person.getSocialLinks());
            }
        }
    }

    private void addSocialLinksToView(RelativeLayout container, SocialLink[] socialLinks) {

        TextView lastTextView = new TextView(this);
        for (int i = 0; i < socialLinks.length; i++) {
            SocialLink socialLink = socialLinks[i];

            TextView socialLinkTextView = new TextView(this);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            if(i == 0){
                //if this is the first social link text view, align it with the label, else align it with the first textview
                params.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.label_social_links);
                params.addRule(RelativeLayout.ALIGN_BASELINE, R.id.label_social_links);
                params.addRule(RelativeLayout.END_OF, R.id.label_social_links);
            }
            else{
                params.addRule(RelativeLayout.ALIGN_START, lastTextView.getId());
                params.addRule(RelativeLayout.BELOW, lastTextView.getId());
            }

            lastTextView = socialLinkTextView;

            socialLinkTextView.setId(View.generateViewId());
            socialLinkTextView.setLayoutParams(params);
            socialLinkTextView.setClickable(true);
            socialLinkTextView.setMovementMethod(LinkMovementMethod.getInstance());

            String rawUrl = socialLink.getUrl();

            String url = "";
            if(rawUrl != null && !rawUrl.isEmpty()){
                //make the social link clickable
                url = String.format("<a href='%s'>%s</a>", socialLink.getUrl(), socialLink.getType());
            }

            socialLinkTextView.setText(Html.fromHtml(url));

            container.addView(socialLinkTextView);
        }
    }
}
