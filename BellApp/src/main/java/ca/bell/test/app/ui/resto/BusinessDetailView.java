package ca.bell.test.app.ui.resto;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import ca.bell.test.app.R;
import ca.bell.test.app.resto.Business;
import ca.bell.test.app.ui.EntityView;
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

public class BusinessDetailView extends EntityView<Business> {
    private ImageView mImgBusiness;
    private RatingBar mRatingBar;
    private TextView mtxtName, mTxtPrice, mTxtDistance;

    public BusinessDetailView(Context ctx) {
        super(ctx);
    }

    @Override
    public int getViewResourceId() {
        return R.layout.view_business_list_item;
    }

    @Override
    public void bindControls(Context ctx) {
        mImgBusiness = findViewById(R.id.imgBusiness);
        mRatingBar = findViewById(R.id.ratingBarBusiness);
        mtxtName = findViewById(R.id.txtName);
        mTxtPrice = findViewById(R.id.txtPrice);
        mTxtDistance = findViewById(R.id.txtDistance);
    }

    @Override
    protected void showEntity(Business business) {
        mtxtName.setText(business.getName());
        mTxtPrice.setText(business.getPrice());
//        StringBuilder displayAddress = new StringBuilder();
//        boolean first = true;
//        for (String address : business.getLocation().getDisplayAddress()) {
//            displayAddress.append((first ? "" : "\n") + address);
//            first = false;
//        }
//        mTxtPhone.setText(business.getDisplayPhone());
//        mTxtAddress.setText(displayAddress.toString());
        mTxtDistance.setText(getKm(business.getDistance() / 1000));
        Glide.with(this).load(business.getImageUrl()).into(mImgBusiness);
        mRatingBar.setRating(business.getRating());

    }


    public static String getKm(float km) {
        final String doubleZero = "00";
        final String unit;
        final String dot = ".";
        final int indexSubstring;
        String distance = null;
        if (km < 1) {
            unit = " m";
            if (km == 0)
                return (0 + unit);
            indexSubstring = 0;
            distance = String.valueOf((int) (km * 1000));
        } else {
            unit = " km";
            indexSubstring = 3;
            distance = km + doubleZero;
        }
        final int dotIndex = distance.indexOf(".") + indexSubstring;
        return (dotIndex == -1 ? distance + unit : distance.substring(0, dotIndex) + unit);
    }


}

