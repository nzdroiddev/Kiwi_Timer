package com.gdanz.nzdroiddev.kiwitimer;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ViewFlipper;
import android.view.MotionEvent;


public class MainActivity extends Activity {
    private ViewFlipper viewFlipper;
    private float lastX;
    private Button buttonStart, buttonStop, buttonSplit;
    private TextView textViewStopwatchDisplay, textViewSplits;
    private int splitCount;
    private boolean canReset, canPause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);

        splitCount = 0;
        canReset = canPause = false;

        Typeface fontNixie = Typeface.createFromAsset(this.getAssets(), "fonts/NixieOne-Regular.ttf");

        textViewStopwatchDisplay = (TextView) findViewById(R.id.textViewStopwatchDisplay);
        textViewStopwatchDisplay.setTypeface(fontNixie);

        textViewSplits  = (TextView) findViewById(R.id.textViewSplits);

        buttonStart = (Button) findViewById(R.id.buttonStart);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (canPause) {
                    buttonStart.setText("START");
                    canPause = false;
                }
                else {
                    buttonStop.setText("STOP");
                    buttonStart.setText("PAUSE");
                    canPause = true;
                }
            }
        });

        buttonStop = (Button) findViewById(R.id.buttonStop);
        buttonStop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (canReset) {
                    textViewStopwatchDisplay.setText("00:00");
                    textViewSplits.setText("");
                    buttonStart.setText("START");
                    buttonStop.setText("STOP");
                    canReset = false;
                }
                else {
                    buttonStop.setText("RESET");
                    canReset = true;
                }
            }
        });

        buttonSplit = (Button) findViewById(R.id.buttonSplit);
        buttonSplit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                textViewSplits.append("Split " + String.valueOf(splitCount) + ": " + textViewStopwatchDisplay.getText() + "\n");
                splitCount++;
            }
        });


    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
*/
    // Using the following method, we will handle all screen swaps.
    public boolean onTouchEvent(MotionEvent touchevent) {
        switch (touchevent.getAction()) {

            case MotionEvent.ACTION_DOWN:
                lastX = touchevent.getX();
                break;
            case MotionEvent.ACTION_UP:
                float currentX = touchevent.getX();

                // Handling left to right screen swap.
                if (lastX < currentX) {

                    // If there aren't any other children, just break.
                    if (viewFlipper.getDisplayedChild() == 0)
                    break;

                    // Next screen comes in from left.
                    viewFlipper.setInAnimation(this, R.anim.slide_in_from_left);
                    // Current screen goes out from right.
                    viewFlipper.setOutAnimation(this, R.anim.slide_out_to_right);

                    // Display next screen.
                    viewFlipper.showNext();
                }

                // Handling right to left screen swap.
                if (lastX > currentX) {

                    // If there is a child (to the left), kust break.
                    if (viewFlipper.getDisplayedChild() == 1)
                    break;

                    // Next screen comes in from right.
                    viewFlipper.setInAnimation(this, R.anim.slide_in_from_right);
                    // Current screen goes out from left.
                    viewFlipper.setOutAnimation(this, R.anim.slide_out_to_left);

                    // Display previous screen.
                    viewFlipper.showPrevious();
                }
                break;
        }
        return false;
    }


}
