package ca.bell.test.app.ui.resto;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import ca.bell.test.app.R;
import ca.bell.test.app.resto.Business;
import ca.bell.test.app.resto.Search;
import ca.bell.test.app.ui.EntityAdapter;
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
public class SearchView extends EntityView<Search> implements View.OnClickListener {

    private EntityAdapter<Business> mAdapterBusiness;

    private AppCompatEditText mEditSearchBusiness, mEditSearchLocation;

    public SearchView(Context ctx) {
        super(ctx);
    }

    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getViewResourceId() {
        return R.layout.view_search;
    }


    @Override
    public void bindControls(Context ctx) {

        mEditSearchBusiness = findViewById(R.id.editSearchBusiness);
        mEditSearchLocation = findViewById(R.id.editSearchLocation);

        mEditSearchBusiness.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                onNewSearch();
            }
        });
        mEditSearchLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                onNewSearch();
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerViewBusiness);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        mAdapterBusiness = new EntityAdapter<Business>() {
            @Override
            protected EntityView<Business> createEntityView(Context ctx) {
                BusinessListItemView view = new BusinessListItemView(ctx);
                view.setOnClickListener(SearchView.this);
                view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                return view;
            }
        };
        recyclerView.setAdapter(mAdapterBusiness);
        AppCompatSpinner spinner = findViewById(R.id.spinnerSort);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.businessSortBy, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    boolean ignoreChange = true;

    private void onNewSearch() {
        if (ignoreChange) return;
        if (entity == null) entity = new Search();
        else
            clearResults();
        this.entity.setQuery(mEditSearchBusiness.getText().toString());
        this.entity.setLocation(mEditSearchLocation.getText().toString());
        tryRun();
    }


    Runnable actionToRun;

    private void tryRun() {
        if (actionToRun != null)
            removeCallbacks(actionToRun);
        actionToRun = new Runnable() {
            @Override
            public void run() {

                listener.onNewSearch(getEntity());
            }
        };
        postDelayed(actionToRun, 2000 /*delay*/);
    }

    private void clearResults() {
        entity.getBusinesses().clear();
        mAdapterBusiness.setEntities(entity.getBusinesses());
        mAdapterBusiness.notifyDataSetChanged();
    }



    @Override
    protected void showEntity(Search search) {
        ignoreChange = true;
        mEditSearchBusiness.setText(entity.getQuery());
        mEditSearchLocation.setText(entity.getLocation());
        search.sortByDistance(true);
        mAdapterBusiness.setEntities(search.getBusinesses());
        mAdapterBusiness.notifyDataSetChanged();
        ignoreChange = false;
    }

    @Override
    public void onClick(View v) {
        if (v instanceof BusinessListItemView) {
            listener.onClick(((BusinessListItemView) v).getEntity());
        }
    }


    public Listener listener;

    public interface Listener {

        void onNewSearch(Search search);

        void onClick(Business business);
    }
}
