package ca.bell.test.app.ui.resto;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

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
public class SearchView extends EntityView<Search> implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

    private EntityAdapter<Business> mAdapterBusiness;

    private AppCompatEditText mEditSearchBusiness, mEditSearchLocation;

    private AppCompatTextView mTxtSortBy, mTxtResults;

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
        mTxtSortBy = findViewById(R.id.txtSortBy);
        mTxtResults = findViewById(R.id.txtResults);
        mEditSearchBusiness = findViewById(R.id.editSearchBusiness);
        mEditSearchLocation = findViewById(R.id.editSearchLocation);

        TextWatcher textWatcher = new TextWatcher() {
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
        };

        mEditSearchBusiness.addTextChangedListener(textWatcher);
        mEditSearchLocation.addTextChangedListener(textWatcher);


        RecyclerView recyclerView = findViewById(R.id.recyclerViewBusiness);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), getResources().getInteger(R.integer.nb_columns));
        recyclerView.setLayoutManager(gridLayoutManager);
        mAdapterBusiness = new EntityAdapter<Business>() {
            @Override
            protected EntityView<Business> createEntityView(Context ctx) {
                BusinessListItemView view = new BusinessListItemView(ctx);
                view.setOnClickListener(SearchView.this);
                return view;
            }
        };
        mAdapterBusiness.listener = new EntityAdapter.Listener() {
            @Override
            public void onEndOfList() {
                listener.onDisplayEndOfList(SearchView.this.entity);
            }
        };
        recyclerView.setAdapter(mAdapterBusiness);
        mTxtSortBy.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(getContext(), mTxtSortBy);
                popup.getMenuInflater().inflate(R.menu.menu_sort, popup.getMenu());
                popup.setOnMenuItemClickListener(SearchView.this);
                popup.show();
            }
        });

    }

    boolean ignoreChange = true;

    private void onNewSearch() {
        if (ignoreChange) return;
        if (entity == null) entity = new Search();
        else
            clearResults();
        mTxtResults.setText(null);
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
    public void show(Search search) {
        if (this.entity != null && this.entity.getBusinesses().size() > 0)
            search.getBusinesses().addAll(0, this.entity.getBusinesses());
        this.entity = search;
        showEntity(this.entity);
    }

    @Override
    protected void showEntity(Search search) {
        ignoreChange = true; // disable textWatcher
        final int total = search.getBusinesses().size();
        if (total != 0)
            mTxtResults.setText("x" + total);
        mEditSearchBusiness.setText(search.getQuery());
        mEditSearchLocation.setText(search.getLocation());
        // search.sortByDistance(true);
        mAdapterBusiness.setEntities(search.getBusinesses());
        mAdapterBusiness.notifyDataSetChanged();
        mTxtSortBy.setText(search.isSortByDistance() ? R.string.sortByDistanceAsc : R.string.sortByRatingAsc);
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

        void onDisplayEndOfList(Search search);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.sortByDistanceAsc) {
            this.entity.setSortByDistance();
            mTxtSortBy.setText(R.string.sortByDistanceAsc);
        } else if (item.getItemId() == R.id.sortByRatingAsc) {
            this.entity.setSortByRating();
            mTxtSortBy.setText(R.string.sortByRatingAsc);
        }
        onNewSearch(); // because of pagination, changing sort requires a complete change of the list
        return true;
    }
}
