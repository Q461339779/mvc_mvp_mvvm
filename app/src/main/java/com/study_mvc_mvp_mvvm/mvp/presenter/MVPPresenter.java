package com.study_mvc_mvp_mvvm.mvp.presenter;

import com.study_mvc_mvp_mvvm.mvp.model.Board;
import com.study_mvc_mvp_mvvm.mvp.model.Player;
import com.study_mvc_mvp_mvvm.mvp.view.MVPView;

public class MVPPresenter {

    private MVPView view;
    private Board model;

    public MVPPresenter(MVPView view) {
        this.view = view;
        this.model = new Board();
    }

    public void onButtonSelected(int row, int col) {
        Player playerThatMoved = model.mark(row, col);

        if(playerThatMoved != null) {
            view.setButtonText(row, col, playerThatMoved.toString());

            if (model.getWinner() != null) {
                view.showWinner(playerThatMoved.toString());
            }
        }
    }

    public void onResetSelected() {
        model.restart();
        view.clearWinnerDisplay();
        view.clearButtons();
    }
}
