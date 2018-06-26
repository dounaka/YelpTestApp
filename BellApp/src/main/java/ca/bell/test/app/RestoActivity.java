package ca.bell.test.app;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import ca.bell.test.app.fragment.FavoriteFragment;
import ca.bell.test.app.fragment.HistoryFragment;
import ca.bell.test.app.fragment.SearchFragment;
import ca.bell.test.app.permission.LocationPermission;
import ca.bell.test.app.resto.Business;
/*
 *  Android library
    Copyright (C) 2018 Icati inc. - Canada

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    http://www.gnu.org/licenses/gpl.html
 */
public class RestoActivity extends AppCompatActivity {


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_search:
                    showSearchFragment();
                    return true;
                case R.id.navigation_favorite:
                    showFavoriteFragment();
                    return true;
                case R.id.navigation_history:
                    showHistoryFragment();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        (new LocationPermission()).requestPermissions(this,
                findViewById(R.id.navigation),
                new Runnable() {
                    @Override
                    public void run() {
                        showCurrentLocation();
                    }
                });

        if (savedInstanceState == null) {
            showSearchFragment();
        }

    }

    private void showSearchFragment() {
        SearchFragment fragment = new SearchFragment();
        getFragmentManager().beginTransaction().replace(R.id.containerFragment, fragment).commit();
    }

    private void showFavoriteFragment() {
        FavoriteFragment fragment = new FavoriteFragment();
        getFragmentManager().beginTransaction().replace(R.id.containerFragment, fragment).commit();
    }

    private void showHistoryFragment() {
        HistoryFragment fragment = new HistoryFragment();
        getFragmentManager().beginTransaction().replace(R.id.containerFragment, fragment).commit();
    }

    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onResume() {
        super.onResume();


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        (new LocationPermission()).onRequestPermissionsResult(this,
                findViewById(R.id.navigation),
                requestCode,
                grantResults,
                new Runnable() {
                    @Override
                    public void run() {
                        showCurrentLocation();
                    }
                }
        );
    }

    private void showCurrentLocation() {
        Toast.makeText(this, "Request location ok ", Toast.LENGTH_SHORT).show();
        OnSuccessListener successListener = new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    Log.d("location", location.toString());
                }
            }
        };
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M &&
                (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        || checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
            mFusedLocationClient.getLastLocation().addOnSuccessListener(this, successListener);
        } else mFusedLocationClient.getLastLocation().addOnSuccessListener(this, successListener);
    }


}
