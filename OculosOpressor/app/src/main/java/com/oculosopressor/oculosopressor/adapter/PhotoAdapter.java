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
public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {


    private Context mContext;
    private List<Photo> mPhotoList = new ArrayList<Photo>();

    public PhotoAdapter(Context context, List<Photo> items) {
        mContext = context;
        mPhotoList.addAll(items);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_photo, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        Photo photo = mPhotoList.get(position);

        if (photo != null) {

            String image = photo.getmImage();
            String comment = photo.getmComment();

            if(image != null && mContext != null && URLUtil.isValidUrl(image)) {

                Picasso.with(mContext)
                        .load(image)
                        .into(viewHolder.photo);

            }

            if(comment != null) {
                viewHolder.title.setText(comment);
            } else {
                viewHolder.title.setVisibility(View.GONE);
            }
        }

    }

    @Override
    public int getItemCount() {
        return mPhotoList.size();
    }

    public void updateList(ArrayList<Photo> photos){
        this.mPhotoList.addAll(photos);
        this.notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView photo;
        public TextView title;
        public TextView subtitle;

        public ViewHolder(View itemView) {
            super(itemView);

            photo = (ImageView) itemView.findViewById(R.id.photo);
            title = (TextView) itemView.findViewById(R.id.title);
            subtitle = (TextView) itemView.findViewById(R.id.subtitle);

        }

    }

}
