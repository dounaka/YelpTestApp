package ca.bell.test.app;

import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import ca.bell.test.app.api.yelp.YelpApi;

/**
 * Splash screen activity - handles the init operations
 * check internet connection
 * get the Token for the resto API
 * add here other common operations, like sync common data
 *
 */
public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    @Override
    protected void onResume() {
        super.onResume();


        YelpApi yelpApi1 = YelpApi.getInstance(getApplicationContext());

        yelpApi1.request();

        // mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

    }
    private FusedLocationProviderClient    mFusedLocationClient;


}
