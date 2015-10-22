package com.tile.janv.userinterface1;


import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import com.tile.janv.userinterface1.logic.LogicUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Board#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Board extends Fragment implements SwipeCallback {

    public static int BOARD_DIMENSION = 4;

//    @Bind(R.id.board_grid)
    GridLayout board;

    private CardView[][] cardBoard;

    public static Board newInstance() {
        Board fragment = new Board();
//        fragment.initBoard();
        return fragment;
    }

    public Board() {
        // Required empty public constructor
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void initBoard() {
//        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
//        params.setMargins(5,5,5,5);
//        params.width = toPixels(50);
//        params.height = toPixels(50);
        board.setColumnCount(BOARD_DIMENSION);
        board.setRowCount(BOARD_DIMENSION);
        cardBoard = new CardView[BOARD_DIMENSION][];
        for (int i = 0; i < cardBoard.length; i++) {
            cardBoard[i] = new CardView[BOARD_DIMENSION];
        }
        for (int i = 0; i < cardBoard.length; i++) {
            for (int j = 0; j < cardBoard.length; j++) {
                CardView cardView = createCardView(0);
                cardBoard[i][j] = cardView;
                board.addView(cardView, toPixels(50), toPixels(50));
            }
        }
        LogicUtil.init(cardBoard);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        board = (GridLayout) inflater.inflate(R.layout.fragment_board, container, false);
        Log.i("Board", "Board onCreateView " + (board != null ? "hasBoard" : "hasNoBoard"));
        initBoard();
        return board;
    }

    private CardView createCardView(int value) {
        CardView cardView = new CardView(board.getContext());
        cardView.setValue(value);
        return cardView;
    }

    private int toPixels(int dps) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dps * scale + 0.5f);
    }

    @Override
    public void up() {
        LogicUtil.perform(LogicUtil.Action.UP, cardBoard);
    }

    @Override
    public void down() {
        LogicUtil.perform(LogicUtil.Action.DOWN, cardBoard);
    }

    @Override
    public void left() {
        LogicUtil.perform(LogicUtil.Action.LEFT, cardBoard);
    }

    @Override
    public void right() {
        LogicUtil.perform(LogicUtil.Action.RIGHT, cardBoard);
    }

}
