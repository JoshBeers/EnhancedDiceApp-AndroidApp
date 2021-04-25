package com.zybooks.diceroller;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public static final int MAX_DICE = 3;
    private static final String TAG = "DiceRoller";
    private static final int COLOR_CODE=10;

    private int mVisibleDice;
    private Dice[] mDice;
    private ImageView[] mDiceImageViews;
    private Menu mMenu;
    private CountDownTimer mTimer;

    private MenuItem rB,sB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create an array of Dice
        mDice = new Dice[MAX_DICE];
        for (int i = 0; i < MAX_DICE; i++) {
            mDice[i] = new Dice(i + 1);
        }

        // Create an array of ImageViews
        mDiceImageViews = new ImageView[MAX_DICE];
        mDiceImageViews[0] = findViewById(R.id.dice1);
        mDiceImageViews[1] = findViewById(R.id.dice2);
        mDiceImageViews[2] = findViewById(R.id.dice3);

        rB= findViewById(R.id.action_roll);
        sB=findViewById(R.id.action_stop);

        // All dice are initially visible
        mVisibleDice = MAX_DICE;

        showDice();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_menu, menu);
        mMenu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Determine which menu option was chosen
        switch (item.getItemId()) {
            case R.id.action_one:

                changeDiceVisibility(1);
                showDice();
                return true;

            case R.id.action_two:
                changeDiceVisibility(2);
                showDice();
                return true;

            case R.id.action_three:
                changeDiceVisibility(3);
                showDice();
                return true;

            case R.id.action_stop:
                mTimer.cancel();
                mMenu.findItem(R.id.action_stop).setVisible(false);
                mMenu.findItem(R.id.action_roll).setVisible(true);
                Log.v("mainActivity","dice roll "+mDice[0].getNumber()+" "+mDice[1].getNumber()+" "+mDice[2].getNumber()+" ");
                item.setVisible(false);
                return true;

            case R.id.action_roll:
                rollDice();
                return true;

            case R.id.color:
                Intent i=new Intent(MainActivity.this,coloer.class);

                try {
                    startActivityForResult(i,COLOR_CODE);
                }catch (Exception e){
                    System.out.println(e);
                    Log.e("landing page","about button failed");
                }

                return true;

            case R.id.about:

                Intent ii=new Intent(MainActivity.this,about.class);

                try {
                    startActivity(ii);
                }catch (Exception e){
                    System.out.println(e);
                    Log.e("landing page","about button failed");
                }


                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void rollDice() {
        mMenu.findItem(R.id.action_stop).setVisible(true);
        mMenu.findItem(R.id.action_roll).setVisible(false);
        if (mTimer != null) {
            mTimer.cancel();
        }

        mTimer = new CountDownTimer(2000, 100) {
            public void onTick(long millisUntilFinished) {
                for (int i = 0; i < mVisibleDice; i++) {
                    mDice[i].roll();
                }
                showDice();
            }

            public void onFinish() {
                mMenu.findItem(R.id.action_stop).setVisible(false);
                mMenu.findItem(R.id.action_roll).setVisible(true);
                Log.v("mainActivity","dice roll "+mDice[0].getNumber()+" "+mDice[1].getNumber()+" "+mDice[2].getNumber()+" ");
            }
        }.start();
    }

    private void changeDiceVisibility(int numVisible) {
        mVisibleDice = numVisible;

        // Make dice visible
        for (int i = 0; i < numVisible; i++) {
            mDiceImageViews[i].setVisibility(View.VISIBLE);
        }

        // Hide remaining dice
        for (int i = numVisible; i < MAX_DICE; i++) {
            mDiceImageViews[i].setVisibility(View.GONE);
        }
    }

    private void showDice() {
        // Display only the number of dice visible
        for (int i = 0; i < mVisibleDice; i++) {
            Drawable diceDrawable;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                diceDrawable = getResources().getDrawable(mDice[i].getImageId(),
                        getApplicationContext().getTheme());
            } else {
                diceDrawable = getResources().getDrawable(mDice[i].getImageId());
            }

            mDiceImageViews[i].setImageDrawable(diceDrawable);
            mDiceImageViews[i].setContentDescription(Integer.toString(mDice[i].getNumber()));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        int color=data.getExtras().getInt("custom_color");

        mDiceImageViews[0].setColorFilter(color);
        mDiceImageViews[1].setColorFilter(color);
        mDiceImageViews[2].setColorFilter(color);

    }
}
