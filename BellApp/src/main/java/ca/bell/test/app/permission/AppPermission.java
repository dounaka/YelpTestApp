package ca.bell.test.app.permission;

import android.content.pm.PackageManager;
import android.util.Log;
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
public class AppPermission {
    public static boolean verifyPermissions(int[] grantResults) {
        // At least one result must be checked.
        if (grantResults.length < 1) {
            return false;
        }
        // Verify that each required permission has been granted, otherwise return false.
        for (int result : grantResults) {
            Log.d("permission", "denied?" + (result == PackageManager.PERMISSION_DENIED));
            Log.d("permission", "granted?" + (result == PackageManager.PERMISSION_GRANTED));
            Log.d("permission", "value " + (result));
            if (result == PackageManager.PERMISSION_GRANTED) {
                return true;
            }
        }
        return false;
    }
}
