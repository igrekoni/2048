package com.tile.janv.userinterface1;


import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.GridLayout;

import com.tile.janv.userinterface1.logic.LogicUtil;
import com.tile.janv.userinterface1.logic.ValueContainer;


/**
 * Extension of {@link GridLayout} to show a 2048 game grid.
 * In API below 21, weight principles cannot be used. As this class aims to support 14+ api,
 * the dimension of the grid elements is calculated based on the size of the device.
 * Alternative would have been {@link android.widget.TableLayout}.
 */
public class Board extends GridLayout {

    private View.OnLayoutChangeListener layoutChangeListener;
    private ValueContainer[][] cardBoard;

    private int[] previousGame;

    public Board(Context context) {
        this(context, null);
    }

    public Board(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Board(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        layoutChangeListener = new OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                initBoard();
                Board.this.removeOnLayoutChangeListener(layoutChangeListener);
            }
        };
        addOnLayoutChangeListener(layoutChangeListener);
    }

    //---------------------
    // methods called by activity
    //---------------------

    public void setPreviousGame(int[] previousGame) {
        this.previousGame = previousGame;
        updateBordValues();
    }

    public void createNewGame() {
        this.previousGame = null;
        updateBordValues();
    }

    /**
     * If there is a board, update the values with the settings as provided, i.e. new game or saved game.
     */
    public void updateBordValues() {
        if (cardBoard == null) {
            return;
        }
        if (previousGame != null) {
            int dim = cardBoard.length;
            for (int i = 0; i < dim; i++) {
                for (int j = 0; j < dim; j++) {
                    cardBoard[i][j].setValue(previousGame[i * dim + j]);
                }
            }
        } else {
            LogicUtil.init(cardBoard);
        }
    }

    public int[] getCurrentGame() {
        if (cardBoard == null) {
            return new int[0];
        }
        int dim = cardBoard.length;
        int[] gameValues = new int[dim * dim];
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                gameValues[i * dim + j] = cardBoard[i][j].getValue();
            }
        }
        return gameValues;
    }

    public ValueContainer[][] getValueContainerGrid() {
        return cardBoard;
    }

    //---------------------
    // private methods
    //---------------------

    private void initBoard() {
        int boardDimension = getResources().getInteger(R.integer.board_dimension);
        int cardDimension = getCardDimension(boardDimension);
        Log.i(Constants.LOG_TAG, String.format("About to initiate the board with dimension %d and card size %d", boardDimension, cardDimension));
        cardBoard = new Card[boardDimension][];
        for (int i = 0; i < cardBoard.length; i++) {
            cardBoard[i] = new Card[boardDimension];
        }
        for (int i = 0; i < cardBoard.length; i++) {
            for (int j = 0; j < cardBoard.length; j++) {
                Card card = createCardView(0);
                card.textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, cardDimension / 8);
                cardBoard[i][j] = card;
                addView(card, cardDimension, cardDimension);
            }
        }
        updateBordValues();
    }

    private Card createCardView(int value) {
        Card card = new Card(getContext());
        card.setValue(value);
        return card;
    }

    /**
     * Calculate card dimensions based on the Board's dimension.
     * @param boardDimension
     * @return
     */
    private int getCardDimension(int boardDimension) {
        int cardFallbackDimension = getResources().getInteger(R.integer.card_fallback_dimension);
        int widthPerTile = getWidth() / boardDimension;
        int heightPerTile = getHeight() / boardDimension;
        if (widthPerTile > 0 && heightPerTile > 0) {
            return widthPerTile > heightPerTile ? heightPerTile : widthPerTile;
        } else if (widthPerTile > 0) {
            // portrait mode
            return widthPerTile;
        } else if (heightPerTile > 0) {
            // landscape mode
            return heightPerTile;
        }
        return cardFallbackDimension;
    }

    private int toPixels(int dps) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dps * scale + 0.5f);
    }
}
