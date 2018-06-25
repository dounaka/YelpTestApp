package ca.bell.test.app.api.yelp;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class YelpApiTest {
    @Test
    public void initAPi() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        YelpApi yelpApi1 = YelpApi.getInstance(appContext);
        YelpApi yelpApi2 = YelpApi.getInstance(appContext);
        // validate the singleton
        assertEquals(yelpApi1, yelpApi2);
    }


    @Test
    public void runQuery() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        YelpApi yelpApi1 = YelpApi.getInstance(appContext);

        yelpApi1.request();


    }
}
