package com.example.garrett.moodbooster;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("MoodBooster");

        ImageView sad = (ImageView) findViewById(R.id.sad_nature);
        String sDrawableName = "sad";
        int sadID = getResources().getIdentifier(sDrawableName , "drawable",  getPackageName());
        sad.setImageResource(sadID);

        sad.setOnClickListener(new View.OnClickListener() {
            // Start new list activity
            public void onClick(View v) {
                Intent mainIntent = new Intent(getApplicationContext(),
                        MediaYoutube.class);
                startActivity(mainIntent);
            }
        });

        ImageView lonely = (ImageView) findViewById(R.id.sad_family);
        String lDrawableName = "lonely";
        int lonelyID = getResources().getIdentifier(lDrawableName , "drawable",  getPackageName());
        lonely.setImageResource(lonelyID);

        lonely.setOnClickListener(new View.OnClickListener() {
            // Start new list activity
            public void onClick(View v) {
                Intent mainIntent = new Intent(getApplicationContext(),
                        MediaYoutube.class);
                startActivity(mainIntent);
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
        }
        return true;
    }

}
