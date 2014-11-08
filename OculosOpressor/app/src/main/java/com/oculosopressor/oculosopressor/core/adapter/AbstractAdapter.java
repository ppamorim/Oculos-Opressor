package com.oculosopressor.oculosopressor.core.adapter;

import android.content.Context;
import android.widget.BaseAdapter;
import android.widget.Filterable;

import java.util.List;

public abstract class AbstractAdapter<T> extends BaseAdapter {

    private final Context mContext;
    private List<T> mDataAll;
    private List<T> mData;

    public AbstractAdapter(Context context, List<T> list) {
        mContext = context;
        mData = list;

        if (this instanceof Filterable) {
            mDataAll = list;
        }
    }

    public final Context getContext() {
        return mContext;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public T getItem(int i) {
        return mData.get(i);
    }

    public List<T> getDataAll() {
        return mDataAll;
    }

    public int getCountAll() {
        return mDataAll.size();
    }

    public void setData(List<T> mData) {
        this.mData = mData;
    }
}
