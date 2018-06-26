package ca.bell.test.app.permission;

import android.Manifest;

import ca.bell.test.app.R;

/**
 * Location permission
 * as we display result by distance we need to use FINE_LOCATION
 */

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

public class LocationPermission extends PermissionRequester {

    public LocationPermission() {
    }

    public static final int REQUEST_LOCATION = 19;

    @Override
    public int getRequestCode() {
        return REQUEST_LOCATION;
    }

    @Override
    String[] getPermissionList() {
        return new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
        };
    }

    @Override
    int getRationaleMessageRessource() {
        return R.string.permission_location_rationale;
    }


    @Override
    int getMessageResource() {
        return R.string.permission_location_available;
    }

    private static final String COUNT_LOCATION = "location";

    @Override
    String getCountSuffix() {
        return COUNT_LOCATION;
    }
}
