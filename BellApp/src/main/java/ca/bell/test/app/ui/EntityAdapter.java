package ca.bell.test.app.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ca.bell.test.app.resto.Entity;


/**
 * Created by dounaka on 2017-04-13.
 * Generic adapter for entities
 * bind with an Entity view

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

public abstract class EntityAdapter<E extends Entity> extends RecyclerView.Adapter<EntityAdapter.ViewHolder> {


    private ArrayList<E> entities = new ArrayList<>();

    protected abstract EntityView<E> createEntityView(Context ctx);

    public EntityAdapter() {
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        EntityView entityView;

        public ViewHolder(EntityView v) {
            super(v);
            entityView = v;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder vh = new ViewHolder(createEntityView(parent.getContext()));
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.entityView.show(entities.get(position));

        if (listener != null && (entities.size() - position) == 3) listener.onEndOfList();

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return entities.size();
    }


    public void setEntities(List<E> entties) {
        this.entities.clear();
        this.entities.addAll(entties);
    }


    public Listener listener;

    public interface Listener {
        void onEndOfList();
    }

}



