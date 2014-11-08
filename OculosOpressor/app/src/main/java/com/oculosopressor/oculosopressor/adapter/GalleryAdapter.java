package com.oculosopressor.oculosopressor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.TextView;

import com.oculosopressor.oculosopressor.R;
import com.oculosopressor.oculosopressor.model.Photo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pedro on 08/11/14.
 */
public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    private int mScreenSize;
    private Context mContext;
    private List<Photo> mPhotoList = new ArrayList<Photo>();

    public GalleryAdapter(Context context, List<Photo> items, int screenSize) {
        mScreenSize = screenSize;
        mContext = context;
        mPhotoList.addAll(items);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_gallery, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        ViewGroup.LayoutParams layoutParams = viewHolder.itemView.getLayoutParams();
        layoutParams.height = mScreenSize/2;

        viewHolder.itemView.setLayoutParams(layoutParams);

        Photo photo = mPhotoList.get(position);

        if (photo != null) {

            String image = photo.getmImage();

            if(image != null && mContext != null && URLUtil.isValidUrl(image)) {

                Picasso.with(mContext)
                        .load(image)
                        .into(viewHolder.photo);

            }
        }

    }

    @Override
    public int getItemCount() {
        return mPhotoList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView photo;

        public ViewHolder(View itemView) {
            super(itemView);
            photo = (ImageView) itemView.findViewById(R.id.photo);
        }

    }

}
