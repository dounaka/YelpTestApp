package ca.bell.test.app.api.yelp;

import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import ca.bell.test.app.BuildConfig;
import ca.bell.test.app.api.RestoApi;
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
public class YelpApi implements RestoApi {
    private static YelpApi mInstance;
    private RequestQueue mRequestQueue;
    private Context mCtx;
    private Cache mCache;

    private YelpApi(Context context) {
        mCtx = context.getApplicationContext();
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


    private void request(final Search search, final SearchResponse searchResponse) {
        StringBuilder urlSearch = new StringBuilder("https://api.yelp.com/v3/businesses/search?categories=restaurants&");
        if (search.isSortByDistance())
            urlSearch.append("&sort_by=distance");
        else if (search.isSortByRating())
            urlSearch.append("&sort_by=rating");

        urlSearch.append("&offset=" + search.getOffset());
        urlSearch.append("&limit=" + Search.LIMIT);

        if (search.getQuery() != null && search.getQuery().length() > 0) {
            urlSearch.append("&term=" + search.getQuery());
        }
        if (search.getLocation() != null && search.getLocation().length() > 0) {
            urlSearch.append( "&location=" + search.getLocation());
        } else if (search.hasLocation()) {
            urlSearch.append( "&latitude=" + search.getLat());
            urlSearch.append("&longitude=" + search.getLng());
        } else return;

        final Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + BuildConfig.API_KEY);
        final Response.Listener<Search> responseSearch = new Response.Listener<Search>() {
            @Override
            public void onResponse(Search response) {
                response.syncPagingSearch(search);
                searchResponse.onSuccess(response);
            }
        };
        Response.ErrorListener responseError = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                searchResponse.onError(error);
            }
        };
        GsonRequest<Search> searchGsonRequest = new GsonRequest<>(urlSearch.toString(), Search.class, headers,
                responseSearch, responseError);
        mRequestQueue.add(searchGsonRequest);
    }

    private void requestDetail(final String businessId, final SearchResponse<Business> businessResponse) {
        StringBuilder urlSearch = new StringBuilder("https://api.yelp.com/v3/businesses/" + businessId);
        final Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + BuildConfig.API_KEY);
        final Response.Listener<Business> responseBusiness = new Response.Listener<Business>() {
            @Override
            public void onResponse(Business response) {
                businessResponse.onSuccess(response);
            }
        };
        Response.ErrorListener responseError = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                businessResponse.onError(error);
            }
        };
        GsonRequest<Business> searchGsonRequest = new GsonRequest<>(urlSearch.toString(), Business.class, headers,
                responseBusiness, responseError);
        mRequestQueue.add(searchGsonRequest);
    }


    @Override
    public void search(Search search, SearchResponse<Search> response) {
        // check if has string location else if has position
        request(search, response);
    }

    @Override
    public void getDetail(String restoid, SearchResponse<Business> response) {
        requestDetail(restoid, response);
    }
}




