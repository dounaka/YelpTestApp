package ca.bell.test.app;

import android.Manifest;
import android.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import ca.bell.test.app.fragment.BusinessFragment;
import ca.bell.test.app.fragment.FavoriteFragment;
import ca.bell.test.app.fragment.HistoryFragment;
import ca.bell.test.app.fragment.SearchFragment;
import ca.bell.test.app.fragment.SearchViewModel;
import ca.bell.test.app.permission.LocationPermission;
import ca.bell.test.app.resto.Business;
import ca.bell.test.app.resto.Search;

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
    SearchViewModel mSearchModel;
    ViewGroup detailView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSearchModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        final Observer<Business> searchObserver = new Observer<Business>() {
            @Override
            public void onChanged(@Nullable Business business) {
                if (business != null)
                    show(business);
                else if (detailView != null) detailView.setVisibility(View.GONE);
            }
        };
        mSearchModel.getSelectedBusiness().observe(this, searchObserver);

        setContentView(R.layout.activity_start);

        detailView = findViewById(R.id.containerFragmentDetail);
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

    private void show(Business business) {
        if (detailView == null) {
            Intent bizzDetailIntent = new Intent(this, BusinessDetailActivity.class);
            bizzDetailIntent.putExtra(BusinessFragment.KEY_BUSINESS, business);
            startActivity(bizzDetailIntent);
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.containerFragmentDetail, BusinessFragment.create(business)).commit();
            detailView.setVisibility(View.VISIBLE);
        }
    }

    private void showSearchFragment() {
        SearchFragment fragment = new SearchFragment();
        getFragmentManager().beginTransaction().replace(R.id.containerFragmentList, fragment).commit();
    }


    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            view.clearFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    private void showFavoriteFragment() {
        hideKeyboard();
        if (detailView != null) detailView.setVisibility(View.GONE);
        FavoriteFragment fragment = new FavoriteFragment();
        getFragmentManager().beginTransaction().replace(R.id.containerFragmentList, fragment).commit();
    }

    private void showHistoryFragment() {
        hideKeyboard();
        if (detailView != null) detailView.setVisibility(View.GONE);
        HistoryFragment fragment = new HistoryFragment();
        getFragmentManager().beginTransaction().replace(R.id.containerFragmentList, fragment).commit();
    }

    private FusedLocationProviderClient mFusedLocationClient;


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
        OnSuccessListener successListener = new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    Search search = mSearchModel.getCurrentSearch().getValue();
                    if (search == null || search.getLocation() != null) return;
                    search.setLat(location.getLatitude());
                    search.setLng(location.getLongitude());
                    Fragment fragment = getFragmentManager().findFragmentById(R.id.containerFragmentList);
                    if (fragment instanceof SearchFragment) {
                        ((SearchFragment) fragment).onNewSearch(search);
                    }
                }
            }
        };
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M &&
                (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        || checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
            mFusedLocationClient.getLastLocation().addOnSuccessListener(this, successListener);
        } else mFusedLocationClient.getLastLocation().addOnSuccessListener(this, successListener);
    }


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


}
