package com.example.garrett.moodbooster;

import android.annotation.TargetApi;
import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
import android.webkit.WebSettings;
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
    private int choice;
    private int page = 2;
    private ImageButton nextArrow;
    private TextView media_question;

    public String mood;
    public String antiMood;
    public String keyWord;

    //firebase auth object
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;

    private String uID;
    private String email;

    private ArrayList<String> prefs;
    private ArrayList<String> htmls = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Random rand = new Random();
        page = rand.nextInt(2) + 1;

        //store htmls of youtube videos to pull up
        htmls.add("https://www.youtube.com/watch?v=rzhv3Q2HkbU");
        htmls.add("https://www.youtube.com/watch?v=wNYcqj8A1Tg");
        htmls.add("https://www.youtube.com/watch?v=r3fE6FQT82s");
        htmls.add("https://www.youtube.com/watch?v=JD-6z3pSRs0");
        htmls.add("https://www.youtube.com/watch?v=dEzbdLn2bJc");
        htmls.add("https://www.youtube.com/watch?v=EFnv8az8cVE");
        htmls.add("https://www.youtube.com/watch?v=1k_bPhglhZk");
        htmls.add("https://www.youtube.com/watch?v=4lqZDJnOsFc");
        htmls.add("https://www.youtube.com/watch?v=q1mAGQAw3Oc");
        htmls.add("https://www.youtube.com/watch?v=nfWlot6h_JM&list=PLsVEeDqau8ajMwMIIDn92SdkNsSIZEU5L");
        htmls.add("https://www.youtube.com/watch?v=QGJuMBdaqIw&index=6&list=PLsVEeDqau8ajMwMIIDn92SdkNsSIZEU5L");
        htmls.add("https://www.youtube.com/watch?v=1k8craCGpgs&list=PLsVEeDqau8ajMwMIIDn92SdkNsSIZEU5L&index=22");
        htmls.add("https://www.youtube.com/watch?v=gGdGFtwCNBE&index=35&list=PLsVEeDqau8ajMwMIIDn92SdkNsSIZEU5L");
        htmls.add("https://www.youtube.com/watch?v=K0ibBPhiaG0");
        htmls.add("https://www.youtube.com/watch?v=lp-EO5I60KA");
        htmls.add("https://www.youtube.com/watch?v=AclhwQKlgfw");
        htmls.add("https://www.youtube.com/watch?v=78nyMHLONqo");
        htmls.add("https://www.youtube.com/watch?v=mX50lA_n0l0");
        htmls.add("https://www.youtube.com/watch?v=i944XxUXkzs");
        htmls.add("https://www.youtube.com/watch?v=-D8MOeH9oYk");
        htmls.add("https://www.youtube.com/watch?v=dfR_LdA3fPI");
        htmls.add("https://www.youtube.com/watch?v=6vQgHBugHxA");
        htmls.add("https://www.youtube.com/watch?v=mNHq7T9YHHs");
        htmls.add("https://www.youtube.com/watch?v=qWy_aOlB45Y");
        htmls.add("https://www.youtube.com/watch?v=1919eTCoESo");
        htmls.add("https://www.youtube.com/watch?v=NTEDDmCjd1k");
        htmls.add("https://www.youtube.com/watch?v=bqxxtxXWMsM");
        htmls.add("https://www.youtube.com/watch?v=mgmVOuLgFB0");
        htmls.add("https://www.youtube.com/watch?v=g-jwWYX7Jlo");
        htmls.add("https://www.youtube.com/watch?v=m5yCOSHeYn4");
        htmls.add("https://www.youtube.com/watch?v=l-gQLqv9f4o");
        htmls.add("https://www.youtube.com/watch?v=1Za8BtLgKv8");
        htmls.add("https://www.youtube.com/watch?v=OPdbdjctx2I");
        htmls.add("https://www.youtube.com/watch?v=2ihOXaU0I8o");
        htmls.add("https://www.youtube.com/watch?v=Nck6BZga7TQ");

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

                    displayData(mood);

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

    public void displayData(String mood) {

        if (page == 1) {
            setContentView(R.layout.activity_media_youtube);
            nextArrow = (ImageButton) findViewById(R.id.nextArrowYT);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            nextArrow.setOnClickListener(this);

            Random rand = new Random();
            int html_index = rand.nextInt(htmls.size()) + 1;
            String html = htmls.get(html_index);
            String frameVideo = "<html><body>Video from YouTube<br><iframe width=\"340\" height=\"300\" src=\""+ html + "\" frameborder=\"0\" allowfullscreen></body></html>";
            WebView videoView =(WebView)findViewById(R.id.video);
            videoView.setWebViewClient(new WebViewClient(){
                @SuppressWarnings("deprecation")
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url){
                    return false;
                }
            });
            WebSettings webSettings = videoView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            videoView.loadData(frameVideo, "text/html", "utf-8");

            media_question = (TextView) findViewById(R.id.media_question);
            media_question.setText("Still feeling " + mood + "?");

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

            media_question = (TextView) findViewById(R.id.media_question);
            media_question.setText("Still feeling " + mood + "?");
        }
    }

    public void nextPage() {
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
