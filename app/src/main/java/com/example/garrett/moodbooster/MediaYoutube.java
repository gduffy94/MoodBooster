package com.example.garrett.moodbooster;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;

import java.util.ArrayList;
import java.util.Random;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by User on 4/6/2017.
 */

public class MediaYoutube extends AppCompatActivity implements View.OnClickListener {
    private int choice = 2;
    private ImageButton nextArrow;

    private String mood;

    //firebase auth object
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;

    private String uID;
    private String email;

    private ArrayList<String> prefs;

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

        mood = getIntent().getStringExtra("mood");
        System.out.println("mood= " + mood);
        prefs = new ArrayList<String>();

        generatePreferences();
        //going to have to update to set content view based on choice
        displayData();
    }

    public void generatePreferences() {
        //gather user data from Database
        final DatabaseReference myRef = mDatabase.child("users");
        Query query = myRef.orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                    String name = (String) messageSnapshot.child("first_name").getValue();
                    System.out.println("First name: "+ name);

                    for (DataSnapshot updateSnapshot : messageSnapshot.child(mood + "Settings").getChildren()) {
                        Boolean pref = (Boolean) updateSnapshot.getValue();
                        //System.out.println("Pref: " + pref);
                        if (pref) {
                            prefs.add(updateSnapshot.getKey());
                        }
                    }
                    System.out.println("Preference: "+ prefs);
                    //choose random key word

                    int len = prefs.size();
                    Random rand = new Random();
                    choice = rand.nextInt(len);
                    String keyword = prefs.get(choice);
                    System.out.println("Keyword= "+ keyword);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    public void gatherData() {

    }

    public void displayData() {

        if (choice == 1) {
            setContentView(R.layout.activity_media_youtube);
            nextArrow = (ImageButton) findViewById(R.id.nextArrowYT);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            nextArrow.setOnClickListener(this);

            VideoView videoView =(VideoView)findViewById(R.id.video);
            MediaController mediaController= new MediaController(this);
            mediaController.setAnchorView(videoView);
            Uri uri=Uri.parse("rtsp://r7—sn-4g57kue6.googlevideo.com/Ck0LENy73wIaRAmk3cJBg-iaXhMYDSANFC0k8JRWMOCoAUIJbXYtZ29vZ2xlSARSBXdhdGNoYKK205Ti2LaNVooBC2lZYlRtN201YTlRDA==/9EFB79E36D6A4191F3BF60D01A0AE429B5C9AA32.B774C6D2C0948C3A49A088F9F5CCCFBE34864B29/yt6/1/video.3gp");
            videoView.setMediaController(mediaController);
            videoView.setVideoURI(uri);
            videoView.requestFocus();
            videoView.start();

        } else if (choice == 2) {
            setContentView(R.layout.activity_media);
            nextArrow = (ImageButton) findViewById(R.id.nextArrow);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            nextArrow.setOnClickListener(this);
        } else if (choice == 3) {
            //quote
        }
    }

    public void nextPage() {
        System.out.println("next page clicked");
        Random rand = new Random();
        choice = rand.nextInt(3) + 1;
        //setContentView(R.layout.activity_media_youtube);
        displayData();
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
        if(view == nextArrow){
            nextPage();
        }
    }

}
