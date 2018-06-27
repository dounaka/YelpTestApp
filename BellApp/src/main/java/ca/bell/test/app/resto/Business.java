package ca.bell.test.app.resto;

import java.io.Serializable;
import java.util.ArrayList;
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
public class Business extends Entity {
    @Override
    public String getId() {
        return id;
    }

    private String id;
    private String name;
    private String url;
    private String imageUrl;
    private float distance;
    private String price;
    private float rating;
    private int reviewCount;

    private String phone;
    private String displayPhone;
    private boolean isClosed;
    private Location location;
    private ArrayList<Categ> categories = new ArrayList<>();
    private Coordinate coordinates;

    private String[] photos;




    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ArrayList<Categ> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Categ> categories) {
        this.categories = categories;
    }

    public Coordinate getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinate coordinates) {
        this.coordinates = coordinates;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDisplayPhone() {
        return displayPhone;
    }

    public void setDisplayPhone(String displayPhone) {
        this.displayPhone = displayPhone;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String[] getPhotos() {
        return photos;
    }

    public void setPhotos(String[] photos) {
        this.photos = photos;
    }

    public static class Categ implements Serializable {
        private String alias;
        private String title;

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class Coordinate implements Serializable {
        private float latitude;
        private float longitude;

        public float getLatitude() {
            return latitude;
        }

        public void setLatitude(float latitude) {
            this.latitude = latitude;
        }

        public float getLongitude() {
            return longitude;
        }

        public void setLongitude(float longitude) {
            this.longitude = longitude;
        }
    }

    public static class Location implements Serializable {
        private String address1;
        private String address2;
        private String address3;
        private String city;
        private String zipCode;
        private String country;
        private String state;

        private String [] displayAddress;

        public String getAddress1() {
            return address1;
        }

        public void setAddress1(String address1) {
            this.address1 = address1;
        }

        public String getAddress2() {
            return address2;
        }

        public void setAddress2(String address2) {
            this.address2 = address2;
        }

        public String getAddress3() {
            return address3;
        }

        public void setAddress3(String address3) {
            this.address3 = address3;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getZipCode() {
            return zipCode;
        }

        public void setZipCode(String zipCode) {
            this.zipCode = zipCode;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String[] getDisplayAddress() {
            return displayAddress;
        }

        public void setDisplayAddress(String[] displayAddress) {
            this.displayAddress = displayAddress;
        }
    }

    public static class DayService implements Serializable {
        private int day;
        private String start;
        private String end;
        private boolean isOvernight;

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        public String getStart() {
            return start;
        }

        public void setStart(String start) {
            this.start = start;
        }

        public String getEnd() {
            return end;
        }

        public void setEnd(String end) {
            this.end = end;
        }

        public boolean isOvernight() {
            return isOvernight;
        }

        public void setOvernight(boolean overnight) {
            isOvernight = overnight;
        }
    }

    public WeekService[] getHours() {
        return hours;
    }

    public void setHours(WeekService[] hours) {
        this.hours = hours;
    }

    private WeekService[] hours;
    public static class WeekService {
        private boolean isOpenNow;
        private String hoursType;
        private DayService[] open;

        public String getHoursType() {
            return hoursType;
        }

        public void setHoursType(String hoursType) {
            this.hoursType = hoursType;
        }

        public boolean isOpenNow() {
            return isOpenNow;
        }

        public void setOpenNow(boolean openNow) {
            isOpenNow = openNow;
        }

        public DayService[] getOpen() {
            return open;
        }

        public void setOpen(DayService[] open) {
            this.open = open;
        }
    }



}
