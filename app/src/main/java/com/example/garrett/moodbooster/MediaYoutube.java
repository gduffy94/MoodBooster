package com.example.garrett.moodbooster;

import android.annotation.TargetApi;
import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;

import java.util.ArrayList;
import java.util.Random;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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

import static android.content.ContentValues.TAG;

/**
 * Created by User on 4/6/2017.
 */

public class MediaYoutube extends AppCompatActivity implements View.OnClickListener {
    private int choice = 2;
    private int page = 2;
    private ImageButton nextArrow;

    public String mood;
    public String antiMood;
    public String keyWord="quotes";

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
        Log.d("Mood", mood);
        System.out.println("mood= " + mood);
        prefs = new ArrayList<String>();

        generatePreferences();
        //going to have to update to set content view based on choice
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

                    for (DataSnapshot updateSnapshot : messageSnapshot.child(mood + "Settings").getChildren()) {
                        Boolean pref = (Boolean) updateSnapshot.getValue();
                        //System.out.println("Pref: " + pref);
                        if (pref) {
                            prefs.add(updateSnapshot.getKey());
                        }
                    }

                    for(String pref: prefs){
                        Log.d("PREFSS", pref);
                    }
                    //choose random key word

                    int len = prefs.size();
                    Random rand = new Random();
                    choice = rand.nextInt(len);
                    String keyword = prefs.get(choice);
                    setKeyWord(keyword);

                    if(mood.equals("angry")) {
                        String anti = "calm";
                        setAntiMood(anti);
                    } else if (mood.equals("afraid")) {
                        String anti = "comforting";
                        setAntiMood(anti);

                    }else if (mood.equals("sad")){
                        String anti = "happy";
                        setAntiMood(anti);

                    }else if (mood.equals("tired")){
                        String anti = "energizing";
                        setAntiMood(anti);

                    }else if (mood.equals("bored")){
                        String anti = "exciting";
                        setAntiMood(anti);

                    }else if (mood.equals("lonely")){
                        String anti = "loved";
                        setAntiMood(anti);
                    }

                    displayData();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void setAntiMood(String anti){
        antiMood = anti;
    }

    public void setKeyWord(String key){
        keyWord = key;
    }

    public void displayData() {

        System.out.println("display data");

        if (page == 1) {
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

        } else if (page == 2) {
            setContentView(R.layout.activity_media);
            nextArrow = (ImageButton) findViewById(R.id.nextArrow);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            nextArrow.setOnClickListener(this);

            WebView webview = (WebView) findViewById(R.id.webview);
            String url = "https://www.google.com/search?q="+antiMood+"+"+keyWord;
            webview.setWebViewClient(new MyWebViewClient());
            webview.getSettings().setJavaScriptEnabled(true);
            webview.loadUrl(url);

        }
    }

    public void nextPage() {
        System.out.println("next page clicked");
        Random rand = new Random();
        page = rand.nextInt(2) + 1;
        generatePreferences();
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

    public class MyWebViewClient extends WebViewClient{

        @SuppressWarnings("deprecation")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url){
            final Uri uri = Uri.parse(url);
            return handleUri(uri);
        }

        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request){
            final Uri uri = request.getUrl();
            return handleUri(uri);
        }


        public boolean handleUri(final Uri uri){
            Log.i(TAG, "Uri =" + uri);
            final String host = uri.getHost();
            final String scheme = uri.getScheme();

            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            //startActivity(new Intent(Intent.ACTION_VIEW, uri));

            startActivity(intent);
            return false;
        }

    }

}
