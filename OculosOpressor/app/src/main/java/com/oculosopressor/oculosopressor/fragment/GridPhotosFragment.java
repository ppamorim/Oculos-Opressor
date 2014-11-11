package com.oculosopressor.oculosopressor.fragment;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.oculosopressor.oculosopressor.R;
import com.oculosopressor.oculosopressor.adapter.GalleryAdapter;
import com.oculosopressor.oculosopressor.adapter.PhotoAdapter;
import com.oculosopressor.oculosopressor.core.view.SlidingTabLayout;
import com.oculosopressor.oculosopressor.model.Gallery;
import com.oculosopressor.oculosopressor.model.Photo;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.io.IOException;
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

        mGalleryAdapter = new GalleryAdapter(getActivity(), screen_width);
        mRecyclerView.setAdapter(mGalleryAdapter);

        LoadImagesFromSDCard loadImagesFromSDCard = new LoadImagesFromSDCard();
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.HONEYCOMB) {
            loadImagesFromSDCard.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
        else {
            loadImagesFromSDCard.execute();
        }
    }

    public static GridPhotosFragment newInstance() {
        return new GridPhotosFragment();
    }

    class LoadImagesFromSDCard extends AsyncTask<Object, Gallery, Object> {

        /**
         * Load images from SD Card in the background, and display each image on the screen.
         *
         */
        @Override
        protected Object doInBackground(Object... params) {

            //setProgressBarIndeterminateVisibility(true);
            Bitmap bitmap = null;
            Bitmap newBitmap = null;
            Uri uri = null;

            // Set up an array of the Thumbnail Image ID column we want
            String[] projection = {MediaStore.Images.Thumbnails._ID};
            // Create the cursor pointing to the SDCard
            Cursor cursor = getActivity().getContentResolver().query(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI,
                    projection, // Which columns to return
                    null,       // Return all rows
                    null,
                    MediaStore.Images.Thumbnails.IMAGE_ID + " DESC");
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails._ID);
            int size = cursor.getCount();
            // If size is 0, there are no images on the SD Card.
            if (size == 0) {
                //No Images available, post some message to the user
            }
            int imageID = 0;
            for (int i = 0; i < size; i++) {
                cursor.moveToPosition(i);
                imageID = cursor.getInt(columnIndex);
                uri = Uri.withAppendedPath(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, "" + imageID);
                try {
                    bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uri));
                    if (bitmap != null) {
                        newBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth()/2, bitmap.getHeight()/2, true);
                        bitmap.recycle();
                        if (newBitmap != null) {
                            publishProgress(new Gallery(newBitmap));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    //Error fetching image, try to recover
                } catch (OutOfMemoryError eo) {
                    eo.printStackTrace();
                }
            }
            cursor.close();
            return null;
        }
        /**
         * Add a new LoadedImage in the images grid.
         *
         * @param value The image.
         */
        @Override
        public void onProgressUpdate(Gallery... value) {
            mGalleryAdapter.updateList(value[0]);
        }

    }

}
