/*
* IntroActivity.java
* Author: Duong Quoc Anh 16020102
* Purpose: Intro
* Include: SliderPage, button interaction
*/
package com.example.croplapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.github.paolorotolo.appintro.model.SliderPage;

public class IntroActivity extends AppIntro {
    String intro;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void onStart() {
        super.onStart();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        intro = bundle.getString(getString(R.string.intro));

        // Set animation
        setFadeAnimation();

        //Set value for page1
        SliderPage sliderPage = new SliderPage();
        sliderPage.setTitle(getString(R.string.tytle1));
        sliderPage.setTitleColor(getResources().getColor(R.color.colorPrimary));
        sliderPage.setDescColor(getResources().getColor(R.color.colorPrimary));
        sliderPage.setDescription(getString(R.string.description1));
        sliderPage.setImageDrawable(R.mipmap.track);
        sliderPage.setBgColor(getResources().getColor(R.color.light_font));
        //Add
        addSlide(AppIntroFragment.newInstance(sliderPage));

        //Set value for page2
        sliderPage.setTitle(getString(R.string.tytle2));
        sliderPage.setDescription(getString(R.string.description2));
        sliderPage.setTitleColor(getResources().getColor(R.color.colorPrimary));
        sliderPage.setDescColor(getResources().getColor(R.color.colorPrimary));
        sliderPage.setImageDrawable(R.mipmap.code);
        sliderPage.setBgColor(getResources().getColor(R.color.light_font));
        //Add
        addSlide(AppIntroFragment.newInstance(sliderPage));

        //Set value for page3
        sliderPage.setTitle(getString(R.string.tytle3));
        sliderPage.setDescription(getString(R.string.description3));
        sliderPage.setTitleColor(getResources().getColor(R.color.colorPrimary));
        sliderPage.setDescColor(getResources().getColor(R.color.colorPrimary));
        sliderPage.setImageDrawable(R.mipmap.filmstore);
        sliderPage.setBgColor(getResources().getColor(R.color.light_font));
        //Add
        addSlide(AppIntroFragment.newInstance(sliderPage));

        //Set value for page4
        sliderPage.setTitle(getString(R.string.tytle4));
        sliderPage.setDescription(getString(R.string.description4));
        sliderPage.setTitleColor(getResources().getColor(R.color.colorPrimary));
        sliderPage.setDescColor(getResources().getColor(R.color.colorPrimary));
        sliderPage.setImageDrawable(R.mipmap.option);
        sliderPage.setBgColor(getResources().getColor(R.color.light_font));
        //Add
        addSlide(AppIntroFragment.newInstance(sliderPage));

        //Set value for page5
        sliderPage.setTitle(getString(R.string.tytle5));
        sliderPage.setDescription(getString(R.string.description5));
        sliderPage.setTitleColor(getResources().getColor(R.color.colorPrimary));
        sliderPage.setDescColor(getResources().getColor(R.color.colorPrimary));
        sliderPage.setImageDrawable(R.mipmap.done);
        sliderPage.setBgColor(getResources().getColor(R.color.light_font));
        //Add
        addSlide(AppIntroFragment.newInstance(sliderPage));





        // OPTIONAL METHODS
        // Override bar/separator color.
        setBarColor(Color.parseColor("#D81B60"));
//        setSeparatorColor(Color.parseColor("#2196F3"));

        // Hide Skip/Done button.
        showSkipButton(true);

        setProgressButtonEnabled(true);
    }

    // Skip seclected
    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        // Do something when users tap on Skip button.
        startMain();
    }

    // Done seclected
    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        // Do something when users tap on Done button
        startMain();
    }

    private void startMain()
    {
        if (intro.contains(getString(R.string.intro))) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            finish();
        }

    }
    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }
}
