package com.oculosopressor.oculosopressor.model;

import com.oculosopressor.oculosopressor.core.model.BaseModel;

public class Photo extends BaseModel {

    private String mImage;
    private String mComment;

    public Photo(String image, String comment) {
        this.mImage = image;
        this.mComment = comment;
    }

    public String getmImage() {
        return mImage;
    }

    public void setmImage(String mImage) {
        this.mImage = mImage;
    }

    public String getmComment() {
        return mComment;
    }

    public void setmComment(String mComment) {
        this.mComment = mComment;
    }

}
