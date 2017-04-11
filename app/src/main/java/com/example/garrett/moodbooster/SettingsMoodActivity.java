package com.example.garrett.moodbooster;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

/**
 * Created by User on 4/11/2017.
 */

public class SettingsMoodActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton sad_puppy;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("MoodBooster");

        sad_puppy = (ImageButton) findViewById(R.id.sad_panda);
        sad_music = (ImageButton) findViewById(R.id.sad_music);
        sad_nature = (ImageButton) findViewById(R.id.sad_nature);
        sad_recipes = (ImageButton) findViewById(R.id.sad_recipes);
        sad_exercise = (ImageButton) findViewById(R.id.sad_exercise);
        sad_family = (ImageButton) findViewById(R.id.sad_family);
        sad_friends = (ImageButton) findViewById(R.id.sad_friends);
        sad_books = (ImageButton) findViewById(R.id.sad_books);
        sad_quotes = (ImageButton) findViewById(R.id.sad_quotes);
        sad_inspire = (ImageButton) findViewById(R.id.sad_inpire);
        sad_travel = (ImageButton) findViewById(R.id.sad_travel);
        sad_funny = (ImageButton) findViewById(R.id.sad_funny);

        sad_puppy.setOnClickListener(this);
        sad_music.setOnClickListener(this);
        sad_nature.setOnClickListener(this);
        sad_recipes.setOnClickListener(this);
        sad_exercise.setOnClickListener(this);
        sad_family.setOnClickListener(this);
        sad_friends.setOnClickListener(this);
        sad_books.setOnClickListener(this);
        sad_quotes.setOnClickListener(this);
        sad_inspire.setOnClickListener(this);
        sad_travel.setOnClickListener(this);
        sad_funny.setOnClickListener(this);
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
        if(view == sad_puppy){
            String cDrawableName = "check";
            int checkID = getResources().getIdentifier(cDrawableName , "drawable",  getPackageName());
            sad_puppy.setImageResource(checkID);
        } else if (view == sad_books) {
            String cDrawableName = "check";
            int checkID = getResources().getIdentifier(cDrawableName , "drawable",  getPackageName());
            sad_books.setImageResource(checkID);
        } else if (view == sad_exercise) {
            String cDrawableName = "check";
            int checkID = getResources().getIdentifier(cDrawableName , "drawable",  getPackageName());
            sad_exercise.setImageResource(checkID);
        } else if (view == sad_family) {
            String cDrawableName = "check";
            int checkID = getResources().getIdentifier(cDrawableName , "drawable",  getPackageName());
            sad_family.setImageResource(checkID);
        } else if (view == sad_friends) {
            String cDrawableName = "check";
            int checkID = getResources().getIdentifier(cDrawableName , "drawable",  getPackageName());
            sad_friends.setImageResource(checkID);
        } else if (view == sad_funny) {
            String cDrawableName = "check";
            int checkID = getResources().getIdentifier(cDrawableName , "drawable",  getPackageName());
            sad_funny.setImageResource(checkID);
        } else if (view == sad_inspire) {
            String cDrawableName = "check";
            int checkID = getResources().getIdentifier(cDrawableName , "drawable",  getPackageName());
            sad_inspire.setImageResource(checkID);
        } else if (view == sad_music) {
            String cDrawableName = "check";
            int checkID = getResources().getIdentifier(cDrawableName , "drawable",  getPackageName());
            sad_music.setImageResource(checkID);
        } else if (view == sad_nature) {
            String cDrawableName = "check";
            int checkID = getResources().getIdentifier(cDrawableName , "drawable",  getPackageName());
            sad_nature.setImageResource(checkID);
        } else if (view == sad_quotes) {
            String cDrawableName = "check";
            int checkID = getResources().getIdentifier(cDrawableName , "drawable",  getPackageName());
            sad_quotes.setImageResource(checkID);
        } else if (view == sad_recipes) {
            String cDrawableName = "check";
            int checkID = getResources().getIdentifier(cDrawableName , "drawable",  getPackageName());
            sad_recipes.setImageResource(checkID);
        } else if (view == sad_travel) {
            String cDrawableName = "check";
            int checkID = getResources().getIdentifier(cDrawableName , "drawable",  getPackageName());
            sad_travel.setImageResource(checkID);
        }

    }
}
