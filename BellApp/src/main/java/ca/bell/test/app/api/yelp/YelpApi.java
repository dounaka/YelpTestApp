package ca.bell.test.app.api.yelp;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import ca.bell.test.app.BuildConfig;

public class YelpApi {
    private static YelpApi mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;
    private static Cache
            mCache;


    private static final String TAG = "YELP_API";


    private YelpApi(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized YelpApi getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new YelpApi(context);
        }
        return mInstance;
    }

    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mCache = new DiskBasedCache(mCtx.getCacheDir(), 1024 * 1024); // 1MB cap
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
            Network network = new BasicNetwork(new HurlStack());
            mRequestQueue = new RequestQueue(mCache, network);
            mRequestQueue.start();
        }
        return mRequestQueue;
    }


    public void request() {
        String url2 = "https://api.yelp.com/v3/businesses/search?location=montreal&term=sushi";
        String url = "https://api.yelp.com/v3/autocomplete?text=del&latitude=37.786882&longitude=-122.399972";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "RESPONSE " + response);
                        // Do something with the response
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {error.printStackTrace();
                        Log.e(TAG, "RESPONSE " + error.getMessage());// Handle error
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers2 =  new HashMap<>();
                headers2.putAll(super.getHeaders());
                headers2.put("Authorization", "Bearer " + BuildConfig.API_KEY);
                return headers2;
            }
        };
        stringRequest.setTag(TAG);
        mRequestQueue.add(stringRequest);
    }


    public void cancelAll() {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(TAG);
        }
    }

    public void request2() {


        String url = "http://www.example.com";

// Formulate the request and handle the response.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Do something with the response
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                    }
                });

// Add the request to the RequestQueue.
        mRequestQueue.add(stringRequest);
    }
}




