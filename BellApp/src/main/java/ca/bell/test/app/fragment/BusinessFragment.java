package ca.bell.test.app.fragment;

import android.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ca.bell.test.app.api.RestoApi;
import ca.bell.test.app.api.RestoFactory;
import ca.bell.test.app.resto.Business;
import ca.bell.test.app.resto.Search;
import ca.bell.test.app.ui.resto.BusinessDetailView;

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

//TODO refactoring / using support.v4.fragment because of LiveData
public class BusinessFragment extends android.support.v4.app.Fragment {

    public static final String KEY_BUSINESS = "key.business";

    private BusinessDetailViewModel mBusinessModel;
    private Observer<Business> businessObserver;

    BusinessDetailView businessDetailView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBusinessModel = ViewModelProviders.of(this).get(BusinessDetailViewModel.class);
        businessObserver = new Observer<Business>() {
            @Override
            public void onChanged(@Nullable Business b) {
                if (businessDetailView != null) {
                    businessDetailView.show(b);
                }
            }
        };
        mBusinessModel.getBusiness().observe(this, businessObserver);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        businessDetailView = new BusinessDetailView(getActivity());
        return businessDetailView;
    }

    @Override
    public void onResume() {
        super.onResume();

        Business business = (Business) getArguments().getSerializable(KEY_BUSINESS);
        businessDetailView.show(business);

        RestoFactory.getRestoApi(getActivity()).getDetail(business.getId(), new RestoApi.SearchResponse<Business>() {
            @Override
            public void onError(Exception ex) {
                //TODO manage error / inform user of technical errors and report error
            }

            @Override
            public void onSuccess(Business businessResult) {
                mBusinessModel.getBusiness().setValue(businessResult);
            }
        });
    }


    public static final BusinessFragment create(Business business) {
        final BusinessFragment businessFragment = new BusinessFragment();
        Bundle args = new Bundle();
        args.putSerializable(BusinessFragment.KEY_BUSINESS, business);
        businessFragment.setArguments(args);
        return businessFragment;
    }
}
