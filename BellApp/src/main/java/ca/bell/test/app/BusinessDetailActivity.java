package ca.bell.test.app;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ca.bell.test.app.fragment.BusinessFragment;
import ca.bell.test.app.resto.Business;

public class BusinessDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_detail);
        Business business = (Business) getIntent().getSerializableExtra(BusinessFragment.KEY_BUSINESS);
        if (savedInstanceState == null)
            getSupportFragmentManager().beginTransaction().replace(R.id.containerFragmentDetail, BusinessFragment.create(business)).commit();
    }

}
