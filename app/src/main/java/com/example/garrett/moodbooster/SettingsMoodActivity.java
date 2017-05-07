package com.example.garrett.moodbooster;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by User on 4/11/2017.
 */

public class SettingsMoodActivity extends AppCompatActivity {

    //firebase auth object
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;

    private String uID;
    private String email;

    private CheckBox animals_cb;
    private CheckBox music_cb;
    private CheckBox nature_cb;
    private CheckBox recipes_cb;
    private CheckBox exercise_cb;
    private CheckBox family_cb;
    private CheckBox friends_cb;
    private CheckBox quotes_cb;
    private CheckBox inspire_cb;
    private CheckBox travel_cb;
    private CheckBox funny_cb;
    private CheckBox books_cb;

    private Boolean animals =false;
    private Boolean music = false;
    private Boolean nature = false;
    private Boolean recipes = false;
    private Boolean exercise = false;
    private Boolean family = false;
    private Boolean friends = false;
    private Boolean quotes = false;
    private Boolean inspire = false;
    private Boolean travel = false;
    private Boolean funny = false;
    private Boolean books = false;

    private TextView title;
    private String mood;

    private Button saveButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //if the user is not logged in
        //that means current user will return null
        if(firebaseAuth.getCurrentUser() == null){
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, LoginActivity.class));
        }

        //getting current user ID and email
        FirebaseUser user = firebaseAuth.getCurrentUser();
        uID = user.getUid();
        email = user.getEmail();

        //gather user data from Database
        final DatabaseReference myRef = mDatabase.child("users");
        Query query = myRef.orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                    String name = (String) messageSnapshot.child("first_name").getValue();
                    System.out.println("First name: "+ name);
                    //textViewUserName.setText("Welcome " + name);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        setContentView(R.layout.activity_mood_preferences);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //get mood
        mood = getIntent().getStringExtra("mood");
        System.out.println("mood is " + mood);
        title = (TextView) findViewById(R.id.titleTb);
        title.setText("What makes you less " + mood + "?");

        animals_cb = (CheckBox) findViewById(R.id.animals_checkBox);
        music_cb = (CheckBox) findViewById(R.id.music_checkBox);
        nature_cb = (CheckBox) findViewById(R.id.nature_checkBox);
        recipes_cb = (CheckBox) findViewById(R.id.recipes_checkBox);
        exercise_cb = (CheckBox) findViewById(R.id.exercise_checkBox);
        family_cb = (CheckBox) findViewById(R.id.family_checkBox);
        friends_cb = (CheckBox) findViewById(R.id.friends_checkBox);
        books_cb = (CheckBox) findViewById(R.id.books_checkBox);
        quotes_cb = (CheckBox) findViewById(R.id.quotes_checkBox);
        travel_cb = (CheckBox) findViewById(R.id.travel_checkBox);
        inspire_cb = (CheckBox) findViewById(R.id.inspire_checkBox);
        funny_cb = (CheckBox) findViewById(R.id.funny_checkBox);

        //initializing views
        saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUpdate(mood);
                finish(); //closing activity
            }
        });

    }

    public void sendUpdate(String mood) {

        if ((animals_cb.isChecked())) {
            animals = true;
        } if ((music_cb.isChecked())) {
            music = true;
        } if ((nature_cb.isChecked())) {
            nature = true;
        } if ((recipes_cb.isChecked())) {
            recipes = true;
        } if ((exercise_cb.isChecked())) {
            exercise = true;
        } if ((family_cb.isChecked())) {
            family = true;
        } if ((friends_cb.isChecked())) {
            friends = true;
        } if ((books_cb.isChecked())) {
            books = true;
        } if ((quotes_cb.isChecked())) {
            quotes = true;
        } if ((inspire_cb.isChecked())) {
            inspire = true;
        } if ((travel_cb.isChecked())) {
            travel = true;
        } if ((funny_cb.isChecked())) {
            funny = true;
        }

        MoodSettings moodSettings = new MoodSettings(animals, music, nature, recipes, exercise, family, friends, books, quotes, inspire, travel, funny);
        DatabaseReference newRef = null;
        if (mood.equals("sad")) {

            newRef = mDatabase.child("users").child(uID).child("sadSettings").push();

        } else if (mood.equals("afraid")) {

            newRef = mDatabase.child("users").child(uID).child("afraidSettings").push();

        } else if (mood.equals("angry")) {

            newRef = mDatabase.child("users").child(uID).child("angrySettings").push();

        } else if (mood.equals("tired")) {

            newRef = mDatabase.child("users").child(uID).child("tiredSettings").push();

        } else if (mood.equals("bored")) {

            newRef = mDatabase.child("users").child(uID).child("boredSettings").push();

        } else if (mood.equals("lonely")) {

            newRef = mDatabase.child("users").child(uID).child("lonelySettings").push();
            
        }

        newRef.setValue(moodSettings);

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
        } else if (res_id == R.id.logout) {
            // Floating Contextual Menu with options
            View view = (View) findViewById(R.id.activity_main);
            registerForContextMenu(view);
            openContextMenu(view);
            unregisterForContextMenu(view);
        }

        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        //code to inflate floating_contextual_menu
        getMenuInflater().inflate(R.menu.floating_contextual_menu, menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int item_id = item.getItemId();
        if (item_id == R.id.exit) {
            //to be implemented later
            firebaseAuth.signOut(); //logging out the user
            finish(); //closing activity
            startActivity(new Intent(this, LoginActivity.class)); //return to login
        }
        return false;
    }
}
