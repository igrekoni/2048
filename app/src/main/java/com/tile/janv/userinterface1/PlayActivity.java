package com.tile.janv.userinterface1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.tile.janv.userinterface1.logic.LogicUtil;
import com.tile.janv.userinterface1.swipe.SwipeCallback;
import com.tile.janv.userinterface1.swipe.SwipeListener;
import com.tile.janv.userinterface1.util.GameInfo;
import com.tile.janv.userinterface1.util.GameUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PlayActivity extends AppCompatActivity implements SwipeCallback {

    private static final String PREVIOUS_GAME = "previousGame";
    private static final String SCORE = "score";
    private static final String CONTINUE = "continuePreviousGame";

    @Bind(R.id.board_grid)
    protected Board board;

    @Bind(R.id.current_score)
    protected TextView currentScoreText;

    @Bind(R.id.best_score)
    protected TextView bestScoreText;

    private int score = 0;
    private int bestScore = 0;

    private GestureDetectorCompat mDetector;
    private SwipeListener swipeListener;

    public static Intent createIntent(Context context, boolean continuePreviousGame) {
        Intent intent = new Intent(context, PlayActivity.class);
        Bundle extra = new Bundle();
        extra.putBoolean(PlayActivity.CONTINUE, continuePreviousGame);
        intent.putExtras(extra);
        return intent;
    }

    //---------------------
    // lifecycle methods
    //---------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        ButterKnife.bind(this);
        linkSwipe();

        SharedPreferences mySharedPreferences = getSharedPreferences(Constants.SP_2048_GAME, MODE_PRIVATE);
        bestScore = mySharedPreferences.getInt(Constants.SP_BEST_SCORE, 0);

        if (savedInstanceState != null) {
            // Restore value of members from saved state
            int[] previousGame = savedInstanceState.getIntArray(PREVIOUS_GAME);
            board.setPreviousGame(previousGame);
            score = savedInstanceState.getInt(SCORE);
        } else if (getIntent().getExtras() != null &&
                getIntent().getExtras().getBoolean(PlayActivity.CONTINUE) &&
                mySharedPreferences.contains(Constants.SP_LAST_GAME)) {
            // fill board with values of previous game
            String previousGameAsString = mySharedPreferences.getString(Constants.SP_LAST_GAME, null);
            if (previousGameAsString != null) {
                extractGameInfo(previousGameAsString);
            } else {
                createNewGame();
            }
        } else {
            createNewGame();
        }

        // update view
        bestScoreText.setText(getString(R.string.best_score_label, bestScore));
        updateScoreView();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        savedInstanceState.putIntArray(PREVIOUS_GAME, board.getCurrentGame());
        savedInstanceState.putInt(SCORE, score);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onPause() {
        getSharedPreferences(Constants.SP_2048_GAME, MODE_PRIVATE).edit()
                .putInt(Constants.SP_BEST_SCORE, Math.max(bestScore, score))
                .commit();
        boolean gameIsNotBusted = true;
        if (gameIsNotBusted) {
            getSharedPreferences(Constants.SP_2048_GAME, MODE_PRIVATE).edit()
                    .putString(Constants.SP_LAST_GAME, GameUtil.gameInfoToString(
                            new GameInfo(score, board.getCurrentGame())))
                    .commit();
        } else {
            getSharedPreferences(Constants.SP_2048_GAME, MODE_PRIVATE).edit()
                    .remove(Constants.SP_LAST_GAME)
                    .commit();
        }
        super.onPause();
    }

    //---------------------
    // menu methods
    //---------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_play, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Log.i(Constants.LOG_TAG, "play activity menu settings button clicked.");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //---------------------
    // SwipeCallback methods
    //---------------------

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public void up() {
        Log.i(Constants.LOG_TAG, "Swiped up.");
        score += LogicUtil.perform(LogicUtil.Action.UP, board.getValueContainerGrid());
        updateScoreView();
    }

    @Override
    public void down() {
        Log.i(Constants.LOG_TAG, "Swiped down.");
        score += LogicUtil.perform(LogicUtil.Action.DOWN, board.getValueContainerGrid());
        updateScoreView();
    }

    @Override
    public void left() {
        Log.i(Constants.LOG_TAG, "Swiped left.");
        score += LogicUtil.perform(LogicUtil.Action.LEFT, board.getValueContainerGrid());
        updateScoreView();
    }

    @Override
    public void right() {
        Log.i(Constants.LOG_TAG, "Swiped right.");
        score += LogicUtil.perform(LogicUtil.Action.RIGHT, board.getValueContainerGrid());
        updateScoreView();
    }

    //---------------------
    // private methods
    //---------------------

    private void updateScoreView() {
        currentScoreText.setText(getString(R.string.current_score_label, score));
        if (score > bestScore) {
            bestScoreText.setText(getString(R.string.best_score_label, score));
        }
    }

    private void linkSwipe() {
        swipeListener = new SwipeListener();
        swipeListener.addCallback(this);
        mDetector = new GestureDetectorCompat(this, swipeListener);
    }

    private void extractGameInfo(String gameInfoAsString) {
        GameInfo gameInfo = GameUtil.extractGameInfo(gameInfoAsString);
        score = gameInfo.getScore();
        board.setPreviousGame(gameInfo.getValues());
    }

    private void createNewGame() {
        board.createNewGame();
        score = 0;
    }
}
