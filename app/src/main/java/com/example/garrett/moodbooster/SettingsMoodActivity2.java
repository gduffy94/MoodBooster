package com.example.garrett.moodbooster;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by User on 5/5/2017.
 */

public class SettingsMoodActivity2 extends AppCompatActivity implements View.OnClickListener {

    //firebase auth object
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;

    private String uID;
    private String email;
    private ImageButton sad_exercise;
    private ImageButton sad_family;
    private ImageButton sad_friends;
    private ImageButton sad_quotes;

    private ImageButton nextArrow;
    private ImageButton backArrow;

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

        //page=1;
        //displayData();

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

    @Override
    public void onClick(View view) {
        if(view == sad_exercise){
            String cDrawableName = "check";
            int checkID = getResources().getIdentifier(cDrawableName , "drawable",  getPackageName());
            sad_exercise.setImageResource(checkID);
        } else if (view == sad_family) {
            String cDrawableName = "check";
            int checkID = getResources().getIdentifier(cDrawableName , "drawable",  getPackageName());
            sad_family.setImageResource(checkID);
        } else if (view == sad_friends){
            String cDrawableName = "check";
            int checkID = getResources().getIdentifier(cDrawableName , "drawable",  getPackageName());
            sad_friends.setImageResource(checkID);
        } else if (view == sad_quotes){
            String cDrawableName = "check";
            int checkID = getResources().getIdentifier(cDrawableName , "drawable",  getPackageName());
            sad_quotes.setImageResource(checkID);
        } else if (view == nextArrow) {
            //nextPage();
        }

    }

}
