package com.oculosopressor.oculosopressor.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.oculosopressor.oculosopressor.R;
import com.oculosopressor.oculosopressor.core.view.CameraPreview;

public class CameraFragment extends Fragment {

    private RelativeLayout mContainer;
    private CameraPreview mPreview;

    private ImageView mCameraLoader;

    private Animation mCameraAnimation;

    private Handler mHandler = new Handler();
    private boolean mIsCameraOnline;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCameraAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.resize_fade_out);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_camera, container, false);

            if(view != null) {
                mContainer = (RelativeLayout) view.findViewById(R.id.container_camera);
                mCameraLoader = (ImageView) view.findViewById(R.id.loader);
            }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mCameraLoader.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResume() {
        super.onResume();

        mPreview = new CameraPreview(getActivity(), 1, CameraPreview.LayoutMode.NoBlank, mContainer);
        mPreview.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        LayoutParams previewLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
//        ViewGroup.LayoutParams containerParams = mContainer.getLayoutParams();
//        previewLayoutParams.height = containerParams.height*2;
//        previewLayoutParams.width = containerParams.width;
        mContainer.addView(mPreview, 0, previewLayoutParams);

        if(mPreview != null) {
            mPreview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getActivity(), "clicou na camera", Toast.LENGTH_LONG).show();
                }
            });
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        endCamera(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        endCamera(true);
    }

    private void endCamera(boolean isSetNull) {
        if(mPreview != null) {
            mPreview.disable(true, true);
            mContainer.removeView(mPreview); // This is necessary.
            if(isSetNull) {
                mPreview = null;
            }
        }
    }

    public void enableCamera() {
        if(!mIsCameraOnline) {
            mIsCameraOnline = true;
            mCameraLoader.setVisibility(View.GONE);
            mCameraLoader.startAnimation(mCameraAnimation);
            mCameraAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mCameraLoader.clearAnimation();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mPreview.enable(false);
                }
            }, 1000);
        }
    }

    public void disableCamera() {
        mIsCameraOnline = false;
        mCameraLoader.clearAnimation();
        mCameraAnimation.cancel();
        mPreview.disable(false, false);
        mCameraLoader.setVisibility(View.VISIBLE);
    }

    public void resetView() {
        mCameraLoader.clearAnimation();
        mCameraAnimation.cancel();
        mCameraLoader.setVisibility(View.VISIBLE);
    }

    public void updateAlpha(float value) {
        mPreview.setAlpha(value);
    }

    public static CameraFragment newInstance() {
        return new CameraFragment();
    }

}
