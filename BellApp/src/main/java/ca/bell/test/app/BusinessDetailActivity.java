package ca.bell.test.app;

import android.os.Bundle;
import android.app.Activity;

import ca.bell.test.app.fragment.BusinessFragment;

public class BusinessDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_detail);
        if (savedInstanceState == null)
            getFragmentManager().beginTransaction().replace(R.id.containerFragmentDetail, new BusinessFragment()).commit();
    }

}
