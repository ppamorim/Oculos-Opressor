package com.oculosopressor.oculosopressor.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.oculosopressor.oculosopressor.core.fragment.ChildFragment;
import com.oculosopressor.oculosopressor.fragment.HomeFragment;
import com.oculosopressor.oculosopressor.R;

public class BaseActivity extends ActionBarActivity {

    private static final String FRAGMENT_INSTANCE = "FRAGMENT_INSTANCE";

	public static final int SELECT_PHOTO = 100;
	private static Uri selectedImage;

    private FrameLayout mContainer;
    private Fragment currentFragment = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
        configFrameLayout();
        configActionBar(getBaseActionBar());

        if (savedInstanceState != null) {

            currentFragment = getSupportFragmentManager()
                    .getFragment(savedInstanceState, FRAGMENT_INSTANCE);

        }
	}

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        replaceFragment(HomeFragment.newInstance());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        if (getSupportFragmentManager() != null &&
                currentFragment != null) {

            getSupportFragmentManager().putFragment(
                    outState,
                    FRAGMENT_INSTANCE,
                    currentFragment);
        }

        super.onSaveInstanceState(outState);
    }

    private void configFrameLayout() {
        mContainer = (FrameLayout) findViewById(R.id.container);
    }

    private void configActionBar(ActionBar actionBar) {

        actionBar.setTitle("");
        actionBar.setCustomView(R.layout.actionbar_custom);
        actionBar.setDisplayShowCustomEnabled(true);

    }

    public ActionBar getBaseActionBar() {
        return getSupportActionBar();
    }

    public void replaceFragment(Fragment fragment) {
        replaceFragment(fragment, FragmentTransaction.TRANSIT_FRAGMENT_OPEN, null);
    }

    public void replaceFragment(Fragment fragment, int transaction, Fragment from) {

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction replace = fragmentManager
                .beginTransaction()
                .setTransition(transaction);

        if (from != null) {

            if (fragment instanceof ChildFragment) {
                ((ChildFragment)fragment).setParentTag(from.getClass().getName());
            }

            replace.add(mContainer.getId(), fragment, fragment.getClass().getName());
            replace.addToBackStack(fragment.getClass().getName());
            replace.hide(from);

        } else {

            try {
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            } catch (Exception e) {
                e.printStackTrace();
            }

            replace.replace(mContainer.getId(), fragment, fragment.getClass().getName());
        }

        currentFragment = fragment;
        replace.commitAllowingStateLoss();
    }


    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		
		switch(id){

			case R.id.contact:
				
		 
				
 
//		String file = saveToInternalSorage(getBitmap(opressor.getViewBitMap()));
//
//				Toast.makeText(this, "Sua Opress�o est� na galeria:"+file,
//		                Toast.LENGTH_LONG).show();
 
				
				
				
	//			MediaStore.Images.Media.insertImage(getContentResolver(),bitMap,"test_"+ timeStamp + ".jpg",timeStamp.toString());
			break;			
		}
	 
		return super.onOptionsItemSelected(item);
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		super.onActivityResult(requestCode, resultCode, data); 

	    switch(requestCode) { 
	    
	    case SELECT_PHOTO:
//	        if(resultCode == RESULT_OK){
//
//	        	if(data==null)return;
//
//	            selectedImage = data.getData();
//
//	        	if(selectedImage==null)return;
//
//	            try {
//	            	InputStream imageStream = getContentResolver().openInputStream(selectedImage);
//	            	Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
//	            	Bitmap out = Bitmap.createScaledBitmap(yourSelectedImage, 800, 600, false);
//	            	yourSelectedImage.recycle();
//	            	opressor.setImagemOprimido(out);
//
//
//				}catch (Exception e) {
//					Toast.makeText(this, getString(R.string.shit_happens), Toast.LENGTH_LONG).show();
//					e.printStackTrace();
//				}
//	        }
	    }		
	}
 
	private String saveToInternalSorage(Bitmap bitmapImage){
 
		File storageDir = new File(
			    Environment.getExternalStorageDirectory(), 
			    "/OculosOpressor"
			);
        // Create imageDir
        File mypath=new File(storageDir,"opressao"+new SimpleDateFormat("dd.MM.yyyy.hhss").format(new Date())+".jpg");
        String path="";
        
        FileOutputStream fos = null;
        try {           

            fos = new FileOutputStream(mypath);

       // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
            
            path = mypath.getCanonicalPath();
            ContentValues values = new ContentValues();

            values.put(Images.Media.DATE_TAKEN, System.currentTimeMillis());
            values.put(Images.Media.MIME_TYPE, "image/jpeg");
            values.put(MediaStore.MediaColumns.DATA,path);

            getContentResolver().insert(Images.Media.EXTERNAL_CONTENT_URI, values);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }	
	
	public static Uri getSelectedImage() {

		
		return selectedImage;
	}

	private Bitmap getBitmap(View v) {
	    v.clearFocus();
	    v.setPressed(false);

	    boolean willNotCache = v.willNotCacheDrawing();
	    v.setWillNotCacheDrawing(false);

	    // Reset the drawing cache background color to fully transparent
	    // for the duration of this operation
	    int color = v.getDrawingCacheBackgroundColor();
	    v.setDrawingCacheBackgroundColor(0);

	    if (color != 0) {
	        v.destroyDrawingCache();
	    }
	    v.buildDrawingCache();
	    Bitmap cacheBitmap = v.getDrawingCache();
	    if (cacheBitmap == null) {
	        Toast.makeText(this, "Something went wrong",
	                Toast.LENGTH_SHORT).show();
	        return null;
	    }

	    Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);

	    // Restore the view
	    v.destroyDrawingCache();
	    v.setWillNotCacheDrawing(willNotCache);
	    v.setDrawingCacheBackgroundColor(color);

	    return bitmap;
	}

}
