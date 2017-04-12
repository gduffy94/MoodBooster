package com.example.garrett.moodbooster;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

/**
 * Created by User on 4/11/2017.
 */

public class SettingsMoodActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton sad_panda;
    private ImageButton sad_music;
    private ImageButton sad_nature;
    private ImageButton sad_recipes;
    private ImageButton sad_exercise;
    private ImageButton sad_family;
    private ImageButton sad_friends;
    private ImageButton sad_books;
    private ImageButton sad_quotes;
    private ImageButton sad_inspire;
    private ImageButton sad_travel;
    private ImageButton sad_funny;

    private ImageButton nextArrow;
    private ImageButton backArrow;

    int page;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page=1;
        displayData();


    }

    public void displayData() {

        if (page == 1) {
            setContentView(R.layout.activity_preferences);
            nextArrow = (ImageButton) findViewById(R.id.pref1_next);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            nextArrow.setOnClickListener(this);

            sad_panda = (ImageButton) findViewById(R.id.pref_panda);
            sad_music = (ImageButton) findViewById(R.id.pref_music);
            sad_nature = (ImageButton) findViewById(R.id.pre_nature);
            sad_recipes = (ImageButton) findViewById(R.id.pref_recipes);

            sad_panda.setOnClickListener(this);
            sad_music.setOnClickListener(this);
            sad_nature.setOnClickListener(this);
            sad_recipes.setOnClickListener(this);

        } else if (page == 2) {
            setContentView(R.layout.activity_preferences2);
            nextArrow = (ImageButton) findViewById(R.id.pref2_next);
            backArrow = (ImageButton) findViewById(R.id.pref2_back);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            nextArrow.setOnClickListener(this);
            backArrow.setOnClickListener(this);

            sad_exercise = (ImageButton) findViewById(R.id.pref_exercise);
            sad_family = (ImageButton) findViewById(R.id.pref_fam);
            sad_quotes = (ImageButton) findViewById(R.id.pref_quotes);
            sad_friends = (ImageButton) findViewById(R.id.pref_friends);

            sad_exercise.setOnClickListener(this);
            sad_family.setOnClickListener(this);
            sad_friends.setOnClickListener(this);
            sad_quotes.setOnClickListener(this);

        } else if (page == 3) {
            setContentView(R.layout.activity_preferences3);
            backArrow = (ImageButton) findViewById(R.id.pref3_back);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            backArrow.setOnClickListener(this);

            sad_books = (ImageButton) findViewById(R.id.pref_books);
            sad_inspire = (ImageButton) findViewById(R.id.pref_kid);
            sad_travel = (ImageButton) findViewById(R.id.pref_travel);
            sad_funny = (ImageButton) findViewById(R.id.pref_shark);

            sad_books.setOnClickListener(this);
            sad_inspire.setOnClickListener(this);
            sad_travel.setOnClickListener(this);
            sad_funny.setOnClickListener(this);
        }
    }

    public void nextPage() {
        System.out.println("next page clicked");
        //page = 2;
        setContentView(R.layout.activity_preferences2);
        //displayData();
    }

    public void previousPage() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int res_id = item.getItemId();

        if (res_id == R.id.settings) {
            Intent mainIntent = new Intent(getApplicationContext(),
                    SettingsActivity.class);
            startActivity(mainIntent);
        } else if (res_id == R.id.home) {
            Intent mainIntent = new Intent(getApplicationContext(),
                    MainActivity.class);
            startActivity(mainIntent);
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        if(view == sad_panda){
            String cDrawableName = "check";
            int checkID = getResources().getIdentifier(cDrawableName , "drawable",  getPackageName());
            sad_panda.setImageResource(checkID);
        } else if (view == sad_books) {
            String cDrawableName = "check";
            int checkID = getResources().getIdentifier(cDrawableName , "drawable",  getPackageName());
            sad_books.setImageResource(checkID);
        } else if (view == nextArrow) {
            nextPage();
        }

    }
}
