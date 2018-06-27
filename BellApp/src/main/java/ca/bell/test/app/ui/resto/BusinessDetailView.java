package ca.bell.test.app.ui.resto;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

    public BusinessDetailView(Context ctx) {
        super(ctx);
    }

    public BusinessDetailView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private BusinessListItemView mViewBusinessItem;
    private LinearLayout mHscrollviewPhotos;
    private TextView mTxtPhone, mTxtAddress, mTxtCategs, mTxtHours;
    @Override
    public int getViewResourceId() {
        return R.layout.view_business_detail;
    }

    @Override
    public void bindControls(Context ctx) {
        mHscrollviewPhotos = findViewById(R.id.hscrollviewPhotos);
        mTxtAddress = findViewById(R.id.txtAddress);
        mTxtPhone = findViewById(R.id.txtPhone);
        mTxtCategs = findViewById(R.id.txtCategs);
        mTxtHours = findViewById(R.id.txtHours);
        mViewBusinessItem = findViewById(R.id.viewBusinessItem);
        mViewBusinessItem.setFlatView();
    }

    @Override
    protected void showEntity(Business business) {
        mViewBusinessItem.showEntity(business);
        StringBuilder displayAddress = new StringBuilder();
        boolean first = true;
        for (String address : business.getLocation().getDisplayAddress()) {
            displayAddress.append((first ? "" : "\n") + address);
            first = false;
        }
        mTxtPhone.setText(business.getDisplayPhone());
        mTxtAddress.setText(displayAddress.toString());
        StringBuilder displayCategs = new StringBuilder();
        for (Business.Categ categ : business.getCategories())
            displayCategs.append("\n" + categ.getTitle());
        mTxtCategs.setText(displayCategs.toString());


        StringBuilder displayHours = new StringBuilder();
        if (business.getHours() != null)
            for (Business.WeekService weekService : business.getHours()) {
            //TODO refactoring replace Strings by constants
                displayHours.append("\n" + (weekService.isOpenNow() ?
                        getResources().getString(R.string.open_now) :
                        getResources().getString(R.string.close_now)));

                displayHours.append(" (" + weekService.getHoursType() + ")");
                for (Business.DayService dayService : weekService.getOpen()) {
                    displayHours.append("\n" + getDay(dayService.getDay()));
                    displayHours.append(" " + dayService.getStart());
                    displayHours.append(" - ");
                    displayHours.append(dayService.getEnd());
                }

            }

        mTxtHours.setText(displayHours.toString());

        LayoutInflater li = LayoutInflater.from(getContext());
        mHscrollviewPhotos.removeAllViews();
        if (business.getPhotos() != null)
            for (String photo : business.getPhotos()) {
                ImageView imageView = (ImageView) li.inflate(R.layout.view_business_photo, null);
                mHscrollviewPhotos.addView(imageView);
                Glide.with(this).load(photo).into(imageView);
            }


    }

    private String getDay(int index) {

        if (index == 0) return getResources().getString(R.string.sun);
        else if (index == 1) return getResources().getString(R.string.mon);
        else if (index == 2) return getResources().getString(R.string.tue);
        else if (index == 3) return getResources().getString(R.string.wed);
        else if (index == 4) return getResources().getString(R.string.thu);
        else if (index == 5) return getResources().getString(R.string.fri);
        else
            return getResources().getString(R.string.sat);
    }


}

