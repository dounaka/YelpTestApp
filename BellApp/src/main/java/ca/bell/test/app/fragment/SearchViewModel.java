package ca.bell.test.app.fragment;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import ca.bell.test.app.resto.Business;
import ca.bell.test.app.resto.Search;
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
public class SearchViewModel extends ViewModel{

    private MutableLiveData<Search> mSearch;
    private MutableLiveData<Business> mSelectedBusiness;


    public MutableLiveData<Search> getCurrentSearch()  {
        if (mSearch ==null) mSearch = new MutableLiveData<>();
        return mSearch;
    }

    public MutableLiveData<Business> getSelectedBusiness()  {
        if (mSelectedBusiness ==null) mSelectedBusiness = new MutableLiveData<>();
        return mSelectedBusiness;
    }
}
