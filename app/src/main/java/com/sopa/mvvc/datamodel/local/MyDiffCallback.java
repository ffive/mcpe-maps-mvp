package com.sopa.mvvc.datamodel.local;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.sopa.mvvc.datamodel.remote.backendless.Category;

import java.util.List;

public class MyDiffCallback extends DiffUtil.Callback{

    List<Category> oldPersons;
    List<Category> newPersons;

    public MyDiffCallback(List<Category> newPersons, List<Category> oldPersons) {
        this.newPersons = newPersons;
        this.oldPersons = oldPersons;
    }

    @Override
    public int getOldListSize() {
        return oldPersons.size();
    }

    @Override
    public int getNewListSize() {
        return newPersons.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldPersons.get(oldItemPosition).getObjectId() .equals( newPersons.get(newItemPosition).getObjectId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldPersons.get(oldItemPosition).equals(newPersons.get(newItemPosition));
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        //you can return particular field for changed item.
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}