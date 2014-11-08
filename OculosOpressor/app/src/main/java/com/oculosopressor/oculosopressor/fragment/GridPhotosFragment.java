package com.oculosopressor.oculosopressor.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.oculosopressor.oculosopressor.R;
import com.oculosopressor.oculosopressor.adapter.GalleryAdapter;
import com.oculosopressor.oculosopressor.adapter.PhotoAdapter;
import com.oculosopressor.oculosopressor.core.view.SlidingTabLayout;
import com.oculosopressor.oculosopressor.model.Photo;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;

/**
 * Created by pedro on 08/11/14.
 */
public class GridPhotosFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private GalleryAdapter mGalleryAdapter;

    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View mView = inflater.inflate(R.layout.fragment_gallery, container,false);

        if(mView != null) {

            mRecyclerView = (RecyclerView) mView.findViewById(R.id.recycler_list);

//        mDragLayer = (DragLayer) mView.findViewById(R.id.drag_layer);
//        esquerdaCaviar = (ImageView) mView.findViewById(R.id.drag_layer).findViewById(R.id.esquerdaCaviar);
//        instrumentopressor= (ImageView) mView.findViewById(R.id.drag_layer).findViewById (R.id.instrumentopressor);
//        seekBarTamanho =(SeekBar) mView.findViewById(R.id.topopressor).findViewById(R.id.seekBar_containerTamanho).findViewById (R.id.seekBarTamanho);
//        seekBarGirar   =(SeekBar) mView.findViewById(R.id.topopressor).findViewById(R.id.seekBar_containerGirar).findViewById (R.id.seekBarGirar);

        }
        return mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);



        ArrayList<Photo> photos = new ArrayList<Photo>();

        for(int i = 0; i < 20; i++) {
            photos.add(new Photo("http://1.bp.blogspot.com/_kwOIOa5AC2E/TTOC4d-B31I/AAAAAAAAIMw/sOGJW6qH2L8/s1600/Nicole-Bahls_01.jpg", "PS4"));
        }

        int screen_width = getResources().getDisplayMetrics().widthPixels;
        if(android.os.Build.VERSION.SDK_INT >= 13) {
            screen_width = ((WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
        }

        mGalleryAdapter = new GalleryAdapter(getActivity(), photos, screen_width);
        mRecyclerView.setAdapter(mGalleryAdapter);
    }

    public static GridPhotosFragment newInstance() {
        return new GridPhotosFragment();
    }

}
