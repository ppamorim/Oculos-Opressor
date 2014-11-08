package com.oculosopressor.oculosopressor.core.fragment;

import android.support.v4.app.Fragment;

public class ChildFragment extends Fragment {

    private String mParentTag;

    public String getParentTag() {
        return this.mParentTag;
    }

    public void setParentTag(String tag) {
        this.mParentTag = tag;
    }
}
