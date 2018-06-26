package ca.bell.test.app.resto;

import java.util.Comparator;
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
public abstract class PlaceComparator implements Comparator<Business> {

    abstract float getValue(Business b);

    public boolean ascendant = true;

    @Override
    public int compare(Business b1, Business b2) {
        int factor = 1;
        if (!ascendant) factor = -1;
        float v1 = getValue(b1), v2 = getValue(b2);
        if (v1 == v2) return 0;
        else if (v1 > v2) return 1 * factor;
        else return -1 * factor;
    }

}
