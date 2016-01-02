package com.tile.janv.userinterface1;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WelcomeActivity extends AppCompatActivity {

    @Bind(R.id.button_option_continue)
    protected Button continueButton;

    //---------------------
    // lifecycle methods
    //---------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateView();
    }

    //---------------------
    // menu methods
    //---------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_welcome, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_restart) {
            Log.i(Constants.LOG_TAG, "main activity menu reset button clicked.");
            getSharedPreferences(Constants.SP_2048_GAME, MODE_PRIVATE).edit()
                    .remove(Constants.SP_BEST_SCORE)
                    .remove(Constants.SP_LAST_GAME)
                    .commit();
            Toast.makeText(getApplicationContext(), getString(R.string.reset_info_text), Toast.LENGTH_SHORT).show();
            updateView();
            return true;
        }

        if (id == R.id.action_settings) {
            Log.i(Constants.LOG_TAG, "main activity menu settings button clicked.");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //---------------------
    // click button methods
    //---------------------

    @OnClick(R.id.button_option_continue)
    public void onResetGameButtonClicked() {
        Log.i(Constants.LOG_TAG, "main activity continue button clicked.");
        startActivity(PlayActivity.createIntent(this, true));
    }

    @OnClick(R.id.button_option_new_game)
    public void onNewGameButtonClicked() {
        Log.i(Constants.LOG_TAG, "main activity new game button clicked.");
        startActivity(PlayActivity.createIntent(this, false));
    }

    @OnClick(R.id.button_option_about)
    public void onAboutButtonClicked() {
        Log.i(Constants.LOG_TAG, "main activity about button clicked.");
        new AlertDialog.Builder(WelcomeActivity.this)
                .setMessage(R.string.about_dialog_message)
                .setTitle(R.string.about_dialog_title)
                .create()
                .show();
    }

    @OnClick(R.id.button_option_exit)
    public void onExitButtonClicked() {
        Log.i(Constants.LOG_TAG, "main activity exit button clicked.");
        // exit the application
        // see http://stackoverflow.com/questions/4756835/how-to-launch-home-screen-programmatically-in-android
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    //---------------------
    // private methods
    //---------------------

    private void updateView() {
        // check the values in shared preference
        SharedPreferences sp = getSharedPreferences(Constants.SP_2048_GAME, MODE_PRIVATE);
        continueButton.setEnabled(sp.contains(Constants.SP_LAST_GAME));
    }
}
