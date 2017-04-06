package com.example.garrett.moodbooster;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * Created by User on 4/5/2017.
 */

public class LoadingScreen extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        ImageView sad = (ImageView) findViewById(R.id.loading_sad);
        String sDrawableName = "sad";
        int sadID = getResources().getIdentifier(sDrawableName, "drawable", getPackageName());
        sad.setImageResource(sadID);

        ImageView happy = (ImageView) findViewById(R.id.loading_happy);
        String hDrawableName = "smile";
        int happyID = getResources().getIdentifier(hDrawableName, "drawable", getPackageName());
        happy.setImageResource(happyID);

        ImageView mad = (ImageView) findViewById(R.id.loading_mad);
        String mDrawableName = "mad";
        int madID = getResources().getIdentifier(mDrawableName, "drawable", getPackageName());
        mad.setImageResource(madID);

        ImageView goofy = (ImageView) findViewById(R.id.loading_goofy);
        String gDrawableName = "quirky";
        int goofyID = getResources().getIdentifier(gDrawableName, "drawable", getPackageName());
        goofy.setImageResource(goofyID);

        Thread myThread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(3000);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();
    }
}
