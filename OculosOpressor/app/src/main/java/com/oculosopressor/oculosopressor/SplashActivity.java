package com.oculosopressor.oculosopressor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.oculosopressor.oculosopressor.activity.BaseActivity;

public class SplashActivity extends ActionBarActivity {

    private static final String PREFERENCES = "config_splash";
    private static final String IS_SHOW = "is_show";

    private ImageView mLogo;
    private TextView mTitleLogo;
    private TextView mWelcome;

    private SharedPreferences mSharedConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSharedConfig = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);

        if(mSharedConfig != null && !mSharedConfig.getBoolean(IS_SHOW, false)) {

            setContentView(R.layout.activity_splash);

            mLogo = (ImageView) findViewById(R.id.splash_logo);
            mTitleLogo = (TextView) findViewById(R.id.title_logo);
            mWelcome = (TextView) findViewById(R.id.text_welcome);

            mLogo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.rotate_anim_splash));

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mTitleLogo.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_top_overshoot));
                    mWelcome.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_bottom_overshoot));
                    mWelcome.setVisibility(View.VISIBLE);
                    mTitleLogo.setVisibility(View.VISIBLE);

                }
            }, 1200);


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    ApplySharedPreferences applySharedPreferences = new ApplySharedPreferences();
                    if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.HONEYCOMB) {
                        applySharedPreferences.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    }
                    else {
                        applySharedPreferences.execute();
                    }

                }
            }, 6000);

        } else if(mSharedConfig != null) {
            finishAndStart(true, false);
        } else {
            finishAndStart(false, false);
        }

    }

    public void finishAndStart(boolean canStart, boolean useAnimation) {
        finish();
        if(canStart) {

            if(useAnimation) {
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
            startActivity(new Intent(this, BaseActivity.class));
        }
    }

    public class ApplySharedPreferences extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            SharedPreferences.Editor editor = mSharedConfig.edit();
            editor.putBoolean(IS_SHOW, true);
            editor.commit();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finishAndStart(true, true);
                }
            }, 2000);
        }
    }

}
