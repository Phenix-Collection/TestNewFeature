package com.example.wenjunzhong.testnewfeature.admob;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.example.wenjunzhong.testnewfeature.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

/**
 * Created by wenjun.zhong on 2017/2/7.
 */

public class AdmobActivity extends Activity {
    private static final String TAG = "AdmobActivity";
    private AdView mAdView;
    private RewardedVideoAd mAd;
    private boolean mIsRewardedVideoLoading;
    private final Object mLock = new Object();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admob_layout);

        loadVideoAd();
    }

    private void loadVideoAd() {
        mAd = MobileAds.getRewardedVideoAdInstance(this);
        mAd.setRewardedVideoAdListener(new RewardedVideoAdListener() {
            @Override
            public void onRewardedVideoAdLoaded() {
                Log.w(TAG, "onRewardedVideoAdLoaded");
                synchronized (mLock) {
                    mIsRewardedVideoLoading = false;
                }
                mAd.show();
            }

            @Override
            public void onRewardedVideoAdOpened() {
                Log.w(TAG, "onRewardedVideoAdOpened");
            }

            @Override
            public void onRewardedVideoStarted() {
                Log.w(TAG, "onRewardedVideoStarted");
            }

            @Override
            public void onRewardedVideoAdClosed() {
                Log.w(TAG, "onRewardedVideoAdClosed");
            }

            @Override
            public void onRewarded(RewardItem rewardItem) {
                Log.w(TAG, "onRewarded");
            }

            @Override
            public void onRewardedVideoAdLeftApplication() {
                Log.w(TAG, "onRewardedVideoAdLeftApplication");
            }

            @Override
            public void onRewardedVideoAdFailedToLoad(int i) {
                Log.w(TAG, "onRewardedVideoAdFailedToLoad " + i);
                synchronized (mLock) {
                    mIsRewardedVideoLoading = false;
                }
            }
        });

        synchronized (mLock) {
            if (!mIsRewardedVideoLoading) {
                mIsRewardedVideoLoading = true;
                Bundle extras = new Bundle();
                extras.putBoolean("_noRefresh", true);
                AdRequest adRequest =
                        new AdRequest.Builder()
                                // .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                                .addTestDevice("AC98C820A50B4AD8A2106EDE96FB87D4").build();
                mAd.loadAd(getString(R.string.banner_ad_unit_id), adRequest);
            }
        }
    }

    private void loadAdmobBanner() {
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR) // All
                                                                                                  // emulators
                .addTestDevice("AC98C820A50B4AD8A2106EDE96FB87D4") // An example device ID
                .build();
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                Log.w(TAG, "onAdClosed");
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                Log.w(TAG, "onAdFailedToLoad");
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
                Log.w(TAG, "onAdLeftApplication");
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
                Log.w(TAG, "onAdOpened");
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Log.w(TAG, "onAdLoaded");
            }
        });
    }
}
