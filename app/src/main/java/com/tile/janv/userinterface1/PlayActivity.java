package com.tile.janv.userinterface1;

import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.TextView;

public class PlayActivity extends AppCompatActivity {

    static final String PREVIOUS_GAME = "previousGame";
    static final String BEST_SCORE = "bestScore";
    static final String RESET = "reset";

    private Board board;

    int bestScore = 0;

    private GestureDetectorCompat mDetector;
    private SwipeListener swipeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        board = (Board) getSupportFragmentManager().findFragmentById(R.id.play_board);
        swipeListener = new SwipeListener();
        swipeListener.addCallback(board);
        mDetector = new GestureDetectorCompat(this, swipeListener);

        Bundle extras = getIntent().getExtras();
        boolean reset = extras.getBoolean(RESET);

        if (savedInstanceState != null) {
            // Restore value of members from saved state
            int[] previousGame = savedInstanceState.getIntArray(PREVIOUS_GAME);
            //TODO send to board
            bestScore = savedInstanceState.getInt(BEST_SCORE);
        } else {
            // Probably initialize members with default values for a new instance
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_play, menu);
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        savedInstanceState.putIntArray(PREVIOUS_GAME, board.getGridValues());
        savedInstanceState.putInt(BEST_SCORE, Math.max(bestScore, board.getScore()));

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }
}
