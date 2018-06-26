package ca.bell.test.app.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import ca.bell.test.app.resto.Entity;
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
 * Entity View
 * provide
 */

public abstract class EntityView<E extends Entity> extends FrameLayout {

    protected E entity;

    public EntityView(Context ctx) {
        super(ctx);
        initView(ctx, null);
    }

    public EntityView(Context ctx, ViewGroup parent) {
        super(ctx);
        initView(ctx, parent);
    }

    public EntityView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, null);
    }

    public EntityView(Context context, AttributeSet attrs, int defstyle) {
        super(context, attrs, defstyle);
        initView(context, null);
    }

    public abstract int getViewResourceId();

    public void show(E e) {
        this.entity = e;
        showEntity(this.entity);
    }

    public E getEntity() {return this.entity;}

    protected abstract void showEntity(E entity);

    public abstract void bindControls(Context ctx);

    private void initView(final Context ctx, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(getViewResourceId(), this, true);
        bindControls(ctx);
    }


}
