package com.oculosopressor.oculosopressor.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.oculosopressor.oculosopressor.R;
import com.oculosopressor.oculosopressor.model.Gallery;

import java.util.ArrayList;
import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    private int mScreenSize;
    private Context mContext;
    private List<Gallery> mGalleryList = new ArrayList<Gallery>();

    public GalleryAdapter(Context context, int screenSize) {
        mScreenSize = screenSize;
        mContext = context;
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

        Gallery gallery = mGalleryList.get(position);

        viewHolder.photo.setImageBitmap(gallery.getBitmap());
//        viewHolder.photo.setTag(gallery.);

    }

    @Override
    public int getItemCount() {
        return mGalleryList.size();
    }

    public void updateList(Gallery galleries){
        this.mGalleryList.add(galleries);
        this.notifyDataSetChanged();
    }

    public void updateImageList(Bitmap bitmap) {

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView photo;

        public ViewHolder(View itemView) {
            super(itemView);
            photo = (ImageView) itemView.findViewById(R.id.photo);
        }

    }

}
