/*
 * OMane: TestFragment.java
 * Author: Duong Quoc Anh 16020102
 * Purpose: show last tiem access database when offline
 * Include: setText, Intent
 */
package com.example.croplapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

//Show last time access database on fragment
public class TestFragment extends Fragment {

    Button btFrg;
    TextView txt;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);
        txt = view.findViewById(R.id.frg_txt);
        Bundle frgBundle = getArguments();
        
        if(frgBundle != null) {
            txt.setText(getString(R.string.lastaccessDB) + " " + frgBundle.getString(getString(R.string.keyTime)));
        }
        return view;
    }
}
