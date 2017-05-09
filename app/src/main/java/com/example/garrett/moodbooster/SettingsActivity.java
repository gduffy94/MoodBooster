package com.example.garrett.moodbooster;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by User on 4/11/2017.
 */

public class SettingsActivity extends AppCompatActivity {

    //firebase auth object
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;

    private String uID;
    private String email;

    private ImageView sad;
    private ImageView tired;
    private ImageView afraid;
    private ImageView angry;
    private ImageView lonely;
    private ImageView bored;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("MoodBooster");

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


        sad = (ImageView) findViewById(R.id.settings_sad);
        String sDrawableName = "sad";
        int sadID = getResources().getIdentifier(sDrawableName , "drawable",  getPackageName());
        sad.setImageResource(sadID);
        sad.setOnClickListener(new View.OnClickListener() {
            // Start new list activity
            public void onClick(View v) {
                Intent mainIntent = new Intent(getApplicationContext(),
                        SettingsMoodActivity.class);
                mainIntent.putExtra("mood", "sad");
                startActivity(mainIntent);
            }
        });

        afraid = (ImageView) findViewById(R.id.settings_afraid);
        String aDrawableName = "terrified";
        int afraidID = getResources().getIdentifier(aDrawableName , "drawable",  getPackageName());
        afraid.setImageResource(afraidID);
        afraid.setOnClickListener(new View.OnClickListener() {
            // Start new list activity
            public void onClick(View v) {
                Intent mainIntent = new Intent(getApplicationContext(),
                        SettingsMoodActivity.class);
                mainIntent.putExtra("mood", "afraid");
                startActivity(mainIntent);
            }
        });

        angry = (ImageView) findViewById(R.id.settings_angry);
        String anDrawableName = "angry";
        int angryID = getResources().getIdentifier(anDrawableName , "drawable",  getPackageName());
        angry.setImageResource(angryID);
        angry.setOnClickListener(new View.OnClickListener() {
            // Start new list activity
            public void onClick(View v) {
                Intent mainIntent = new Intent(getApplicationContext(),
                        SettingsMoodActivity.class);
                mainIntent.putExtra("mood", "angry");
                startActivity(mainIntent);
            }
        });

        tired = (ImageView) findViewById(R.id.settings_tired);
        String tDrawableName = "exhausted";
        int tID = getResources().getIdentifier(tDrawableName , "drawable",  getPackageName());
        tired.setImageResource(tID);
        tired.setOnClickListener(new View.OnClickListener() {
            // Start new list activity
            public void onClick(View v) {
                Intent mainIntent = new Intent(getApplicationContext(),
                        SettingsMoodActivity.class);
                mainIntent.putExtra("mood", "tired");
                startActivity(mainIntent);
            }
        });

        bored = (ImageView) findViewById(R.id.settings_bored);
        String bDrawableName = "bored";
        int bID = getResources().getIdentifier(bDrawableName , "drawable",  getPackageName());
        bored.setImageResource(bID);
        bored.setOnClickListener(new View.OnClickListener() {
            // Start new list activity
            public void onClick(View v) {
                Intent mainIntent = new Intent(getApplicationContext(),
                        SettingsMoodActivity.class);
                mainIntent.putExtra("mood", "bored");
                startActivity(mainIntent);
            }
        });

        lonely = (ImageView) findViewById(R.id.settings_lonely);
        String lDrawableName = "lonely";
        int lID = getResources().getIdentifier(lDrawableName , "drawable",  getPackageName());
        lonely.setImageResource(lID);
        lonely.setOnClickListener(new View.OnClickListener() {
            // Start new list activity
            public void onClick(View v) {
                Intent mainIntent = new Intent(getApplicationContext(),
                        SettingsMoodActivity.class);
                mainIntent.putExtra("mood", "lonely");
                startActivity(mainIntent);
            }
        });



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
            View view = (View) findViewById(R.id.activity_settings);
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
