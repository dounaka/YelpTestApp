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

import ca.bell.test.app.R;
import ca.bell.test.app.api.RestoApi;
import ca.bell.test.app.api.RestoFactory;
import ca.bell.test.app.resto.Business;
import ca.bell.test.app.resto.Search;
import ca.bell.test.app.ui.resto.SearchView;
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
public class SearchFragment extends Fragment implements SearchView.Listener {

    private SearchViewModel mSearchModel;
    private Observer<Search> searchObserver;

    private SearchView mSearchView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSearchModel = ViewModelProviders.of((AppCompatActivity) getActivity()).get(SearchViewModel.class);
        searchObserver = new Observer<Search>() {
            @Override
            public void onChanged(@Nullable Search search) {
                if (mSearchView != null) {
                    mSearchView.show(search);
                }
            }
        };
        mSearchModel.getCurrentSearch().observe((AppCompatActivity) getActivity(), searchObserver);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSearchModel.getCurrentSearch().removeObserver(searchObserver);
    }


    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        mSearchView.show(mSearchModel.getCurrentSearch().getValue());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View mainView = inflater.inflate(R.layout.fragment_search, container, false);
        mSearchView = mainView.findViewById(R.id.searchView);
        mSearchView.listener = this;
        return mainView;
    }


    @Override
    public void onClick(Business business) {
        mSearchModel.getSelectedBusiness().setValue(business);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mSearchModel.getCurrentSearch().getValue() == null)
            mSearchModel.getCurrentSearch().setValue(new Search());
        else
            mSearchView.show(mSearchModel.getCurrentSearch().getValue());
    }

    @Override
    public void onNewSearch(final Search search) {
        mSearchModel.getSelectedBusiness().setValue(null);
        runSearch(search);
    }


    @Override
    public void onDisplayEndOfList(final Search search) {
        runSearch(search);
    }

    private void runSearch(Search search) {

        RestoFactory.getRestoApi(getActivity()).search(search, new RestoApi.SearchResponse<Search>() {
            @Override
            public void onError(Exception ex) {
                //TODO manage error / inform user of technical errors and report error
            }

            @Override
            public void onSuccess(Search searchResult) {
                mSearchModel.getCurrentSearch().setValue(searchResult);
            }
        });
    }

}
