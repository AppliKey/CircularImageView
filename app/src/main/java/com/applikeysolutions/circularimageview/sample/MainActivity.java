package com.applikeysolutions.circularimageview.sample;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.applikeysolutions.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private CircularImageView mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mView = (CircularImageView) findViewById(R.id.view);
        AppCompatSpinner gravitySpinner = (AppCompatSpinner) findViewById(R.id.gravity);
        gravitySpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,
                Gravity.values()));
        gravitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mView.setGravity(Gravity.values()[position].getGravity());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        EditText mSector = (EditText) findViewById(R.id.angle_sector);
        mSector.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                final int angle = s.length() == 0 ? 0 : Integer.parseInt(String.valueOf(s));
                mView.setSectorAngle(angle);
            }
        });
        EditText mOffset = (EditText) findViewById(R.id.angle_offset);
        mOffset.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                final int angle = s.length() == 0 ? 0 : Integer.parseInt(String.valueOf(s));
                mView.setAngleOffset(angle);
            }
        });
        EditText iconsCount = (EditText) findViewById(R.id.count);
        iconsCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                final int count = s.length() == 0 ? 0 : Integer.parseInt(String.valueOf(s));
                fillAdapter(count);
            }
        });
    }

    private void fillAdapter(int itemCount) {
        List<Drawable> drawableList = new ArrayList<>();
        for (int i = 0; i < itemCount; i++) {
            drawableList.add(ContextCompat.getDrawable(this, R.drawable.ic_github));
        }
        mView.setIcons(drawableList);
    }
}
