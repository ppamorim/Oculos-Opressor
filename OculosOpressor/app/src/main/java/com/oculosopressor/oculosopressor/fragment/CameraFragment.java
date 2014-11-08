package com.oculosopressor.oculosopressor.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.oculosopressor.oculosopressor.R;
import com.oculosopressor.oculosopressor.core.view.CameraPreview;

public class CameraFragment extends Fragment {

    private CameraPreview mPreview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_camera, container, false);

            if(view != null) {
                mPreview = (CameraPreview) view.findViewById(R.id.camera_view);
            }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(mPreview != null) {
            mPreview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getActivity(), "clico para tirar a foto", Toast.LENGTH_LONG).show();
                }
            });
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        disableCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        disableCamera();
    }

    private void disableCamera() {
        if(mPreview != null) {
            mPreview.stop();
            mPreview = null;
        }
    }

    public static CameraFragment newInstance() {
        return new CameraFragment();
    }

}
