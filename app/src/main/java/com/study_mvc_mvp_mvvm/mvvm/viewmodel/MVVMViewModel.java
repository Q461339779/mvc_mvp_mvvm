package com.study_mvc_mvp_mvvm.mvvm.viewmodel;


import androidx.databinding.ObservableArrayMap;
import androidx.databinding.ObservableField;

import com.study_mvc_mvp_mvvm.mvvm.model.Board;
import com.study_mvc_mvp_mvvm.mvvm.model.Player;

public class MVVMViewModel {

    private Board model;

    public final ObservableArrayMap<String, String> cells = new ObservableArrayMap<>();
    public final ObservableField<String> winner = new ObservableField<>();

    public MVVMViewModel() {
        model = new Board();
    }

    public void onResetSelected() {
        model.restart();
        winner.set(null);
        cells.clear();
    }

    public void onClickedCellAt(int row, int col) {
        Player playerThatMoved = model.mark(row, col);
        if(playerThatMoved != null) {
            cells.put("" + row + col, playerThatMoved == null ? null : playerThatMoved.toString());
            winner.set(model.getWinner() == null ? null : model.getWinner().toString());
        }
    }
}
