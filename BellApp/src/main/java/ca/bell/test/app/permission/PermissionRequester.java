package ca.bell.test.app.permission;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.view.View;

import ca.bell.test.app.R;

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
/**
 * Created by dounaka on 2017-01-18.
 * Manage permissions and the number of tries
 * Subclasses responsabilities is to define the requested permissions and the messages displayed
 */

public abstract class PermissionRequester {

    private final static int MAX_ALERT_COUNT = 3;

    private final static String PREF_NAME = "pref.permissions";

    private final static String PREF_COUNT = "pref.count_";

    public abstract int getRequestCode();

    abstract String[] getPermissionList();

    abstract int getRationaleMessageRessource();

    abstract int getMessageResource();

    abstract String getCountSuffix();


    private static void setCount(Context ctx, final String suffixName, final int newcount) {
        SharedPreferences settings = ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(PREF_COUNT + suffixName, newcount);
        editor.apply();
    }

    private static int getCount(Context ctx, final String suffixName) {
        SharedPreferences settings = ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        final int count = settings.getInt(PREF_COUNT + suffixName, 0);
        return count;
    }


    public void requestPermissions(final Activity activity, View snackview, Runnable nextAction) {
        final String[] allpermissions = getPermissionList();
        final String firstPermission = allpermissions[0];
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, firstPermission)) {
            Snackbar.make(snackview,
                    getRationaleMessageRessource(),
                    Snackbar.LENGTH_INDEFINITE).setAction(R.string.ok, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ActivityCompat.requestPermissions(activity, allpermissions, getRequestCode());
                }
            }).show();
        } else if (ActivityCompat.checkSelfPermission(activity, firstPermission) != PackageManager.PERMISSION_GRANTED) {
            if (getCount(activity, getCountSuffix()) < MAX_ALERT_COUNT)
                ActivityCompat.requestPermissions(activity, allpermissions, getRequestCode());
        } else {
            nextAction.run();
        }
    }

    public boolean isDenied() {

        return false;
    }


    public boolean isPermissionAccepted(Activity activity) {
        final String firstPermission = getPermissionList()[0];
        return (!ActivityCompat.shouldShowRequestPermissionRationale(activity, firstPermission));
    }

    public void onRequestPermissionsResult(Context context, View snackView, int returnedCode, int[] grantResults, Runnable nextAction) {
        if (returnedCode == getRequestCode())
            if (AppPermission.verifyPermissions(grantResults)) {
                nextAction.run();
                Snackbar.make(snackView, getMessageResource(), Snackbar.LENGTH_SHORT).show();
            } else {
                int countAlert = getCount(context, getCountSuffix());
                if (countAlert <= MAX_ALERT_COUNT) {
                    Snackbar.make(snackView, R.string.permission_not_granted, Snackbar.LENGTH_LONG).show();
                    setCount(context, getCountSuffix(), countAlert + 1);
                }
            }
    }


}
