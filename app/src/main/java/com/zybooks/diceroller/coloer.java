package com.zybooks.diceroller;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

public class coloer extends AppCompatActivity {

    SeekBar rB,gB,bB;
    ImageView img;

    SeekBar.OnSeekBarChangeListener oSBCL=new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            updateColor();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coloer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);





        rB=findViewById(R.id.red);
        gB=findViewById(R.id.green);
        bB=findViewById(R.id.blue);

        bB.setOnSeekBarChangeListener(oSBCL);
        rB.setOnSeekBarChangeListener(oSBCL);

        gB.setOnSeekBarChangeListener(oSBCL);

        img=findViewById(R.id.img);


    }

    private void updateColor() {
        int c = Color.argb(255, rB.getProgress(), gB.getProgress(), bB.getProgress());

        img.setBackgroundColor(c);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int c=Color.argb(255, rB.getProgress(), gB.getProgress(), bB.getProgress());

        Log.v("color",""+c);
        Intent t=getIntent().putExtra("custom_color",c);

        setResult(RESULT_OK,t);

        finish();

        return super.onOptionsItemSelected(item);
    }






}
