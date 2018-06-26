package ca.bell.test.app.resto;
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
 * App Entity
 * Allow to manage persistance, cache, view display at a higher level and factorize code
 */
public abstract class Entity {

    protected static final String SEP = "_";

    abstract String getId();

    public String getUid() {
        return getUid(this.getClass(), getId());
    }

    public static String getUid(Class clazz, String id) {
        return clazz.getSimpleName() + SEP + id;
    }


}
