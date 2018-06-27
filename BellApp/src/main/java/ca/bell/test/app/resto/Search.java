package ca.bell.test.app.resto;

import java.util.ArrayList;
import java.util.Collections;
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
public class Search extends Entity {

    public Search() {
        super();
    }

    private String query;
    private String location;
    private double lat = -1;
    private double lng = -1;
    private ArrayList<Business> businesses = new ArrayList<>();

    public static final int LIMIT = 20;

    private int offset;


    @Override
    public String getId() {
        return getQuery() + SEP + getLocation() + SEP + getLat() + SEP + getLng();
    }


    public String getQuery() {
        return query;
    }


    public boolean hasLocation() {
        return lat != -1 && lng != -1;
    }

    public void setQuery(String query) {
        this.query = query;
        this.offset = 0;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
        this.offset = 0;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public ArrayList<Business> getBusinesses() {
        return businesses;
    }

    public void setBusinesses(ArrayList<Business> businesses) {
        this.businesses = businesses;
    }


    private short sortBy = SORT_BY_DISTANCE;
    private static final int SORT_BY_DISTANCE = 1;
    private static final int SORT_BY_RATING = 2;

    public boolean isSortByDistance() {
        return sortBy == SORT_BY_DISTANCE;
    }

    public boolean isSortByRating() {
        return sortBy == SORT_BY_RATING;
    }

    public void setSortByDistance() {
        sortBy = SORT_BY_DISTANCE;
    }

    public void setSortByRating() {
        sortBy = SORT_BY_RATING;
    }



    private PlaceComparator distanceSort = new PlaceComparator() {
        @Override
        float getValue(Business b) {
            return b.getDistance();
        }
    };

    PlaceComparator ratingSort = new PlaceComparator() {
        @Override
        float getValue(Business b) {
            return b.getRating();
        }
    };


    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }


    public short getSortBy() {
        return sortBy;
    }

    public void setSortBy(short sortBy) {
        this.sortBy = sortBy;
    }

    public void syncPagingSearch(Search anotherSearch) {
        this.offset = anotherSearch.getOffset() + Search.LIMIT;
        this.sortBy = anotherSearch.sortBy;
        this.query = anotherSearch.query;
        this.location = anotherSearch.location;
        this.lat = anotherSearch.lat;
        this.lng = anotherSearch.lng;
        this.businesses.addAll(0, anotherSearch.getBusinesses());
    }
}
