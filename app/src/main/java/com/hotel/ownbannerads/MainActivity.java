package com.hotel.ownbannerads;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.loveWebhibe.bannerads.BannerAds;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BannerAds bannerAds=findViewById(R.id.banner);
        bannerAds.adBanner("123");
    }
}
