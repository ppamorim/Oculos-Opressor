package com.oculosopressor.oculosopressor.fragment;
 
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//import com.oculosopressor.oculosopressor.Activity.BaseActivity;
//import com.oculosopressor.oculosopressor.Controller.DragController;
//import com.oculosopressor.oculosopressor.Controller.DragLayer;
import com.oculosopressor.oculosopressor.R;
import com.oculosopressor.oculosopressor.adapter.PhotoAdapter;
import com.oculosopressor.oculosopressor.adapter.View.TabsPagerAdapter;
import com.oculosopressor.oculosopressor.core.view.SlidingTabLayout;
import com.oculosopressor.oculosopressor.model.Photo;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class HomeFragment extends Fragment {

    private List<Fragment> arrayFragments = new Vector<Fragment>(2);
    private List<String> arrayTagFragment = new Vector<String>(2);

    private RecyclerView mRecyclerView;
    private PhotoAdapter mPhotoAdapter;

    private TabsPagerAdapter adapter;
    private SlidingTabLayout mSlidingTabLayout;
    private ViewPager mViewPager;

    private RecyclerView.LayoutManager mLayoutManager;

    private SlidingUpPanelLayout mSlidingPanel;

//	private DragController mDragController;
// 	private DragLayer mDragLayer;
//	private boolean mLongClickStartsDrag = true;
//	private ImageView esquerdaCaviar;
//	private ImageView instrumentopressor;
//	private SeekBar seekBarTamanho;
//	private SeekBar seekBarGirar;
	
	float width;
	float height;
    int panel;

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View mView = inflater.inflate(R.layout.fragment_main, container,false);

        if(mView != null) {

            mRecyclerView = (RecyclerView) mView.findViewById(R.id.recycler_list);
            mSlidingTabLayout = (SlidingTabLayout) mView.findViewById(R.id.sliding_tabs);
            mViewPager = (ViewPager) mView.findViewById(R.id.viewpager);
            mSlidingPanel = (SlidingUpPanelLayout) mView.findViewById(R.id.sliding_layout);

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

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);



        ArrayList<Photo> photos = new ArrayList<Photo>();

        for(int i = 0; i < 20; i++) {
            photos.add(new Photo("http://www.freskimassas.com.br/imagens/produtos/mini-kibe/foto-mini-kibe.jpg", "Android, o melhor"));
        }

        mPhotoAdapter = new PhotoAdapter(getActivity(), photos);
        mRecyclerView.setAdapter(mPhotoAdapter);

        arrayFragments.add(CameraFragment.newInstance());
        arrayFragments.add(GridPhotosFragment.newInstance());

        arrayTagFragment.add(getString(R.string.camera_uppercase));
        arrayTagFragment.add(getString(R.string.gallery_uppercase));

        adapter = new TabsPagerAdapter(getChildFragmentManager(), arrayFragments, arrayTagFragment);

        mViewPager.setOffscreenPageLimit(1);
        mViewPager.setAdapter(adapter);

        mSlidingTabLayout.getViewTreeObserver().addOnGlobalLayoutListener(new android.view.ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mSlidingPanel.setPanelHeight(mSlidingTabLayout.getMeasuredHeight() + 1);
                panel = mSlidingTabLayout.getMeasuredHeight();
            }
        });

        mSlidingTabLayout.setCountDivider(arrayFragments.size());
        mSlidingTabLayout.setViewPager(mViewPager);
        mSlidingTabLayout.setCustomTextColor(getResources().getColor(R.color.yellow));
        mSlidingTabLayout.setOnPageChangeListener(onPageChangeListener);
        mSlidingTabLayout.setCustomTabColorizer(setCustomTabColor);

    }

    //	public View getViewBitMap(){
//
//		return mDragLayer;
//	}
	
//	private void init(){
//
//	    mDragLayer.setDragController(mDragController);
//	    mDragController.addDropTarget (mDragLayer);
//	    seekBarTamanho.setProgress(100);
//	    seekBarGirar.setProgress(50);
//
//		width =instrumentopressor.getLayoutParams().width;
//		height = instrumentopressor.getLayoutParams().height;
//
//
//	    instrumentopressor.setOnLongClickListener(this);
//	    instrumentopressor.setOnTouchListener(this);
//
//	    seekBarTamanho.setOnSeekBarChangeListener(this);
//	    seekBarGirar.setOnSeekBarChangeListener(this);
//
//	    if(BaseActivity.getSelectedImage()!=null){
//		InputStream imageStream;
//		try {
//			imageStream = getActivity().getContentResolver().openInputStream(BaseActivity.getSelectedImage());
//
//			Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
//
//	        setImagemOprimido(yourSelectedImage);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	    }
//	}
 
//	public boolean startDrag (View v)
//	{
//	    // Let the DragController initiate a drag-drop sequence.
//	    // I use the dragInfo to pass along the object being dragged.
//	    // I'm not sure how the Launcher designers do this.
//	    Object dragInfo = v;
//	    mDragController.startDrag (v, mDragLayer, dragInfo, DragController.DRAG_ACTION_MOVE);
//	    return true;
//	}

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (mSlidingPanel != null && !mSlidingPanel.isPanelExpanded()) {
                mSlidingPanel.expandPanel();
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @SuppressWarnings("resource")
    private SlidingTabLayout.TabColorizer setCustomTabColor = new SlidingTabLayout.TabColorizer() {
        @SuppressWarnings("ResourceType")
        @Override
        public int getIndicatorColor(int position) {
            return Color.parseColor(getResources().getString(R.color.yellow));
        }

        @Override
        public int getDividerColor(int position) {
            return Color.TRANSPARENT;
        }

        @SuppressWarnings("ResourceType")
        @Override
        public int getSidesColor(int position) {
            return Color.parseColor(getResources().getString(R.color.black));
        }
    };

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

}
