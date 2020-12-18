package com.study_mvc_mvp_mvvm.mvp.view;

public interface MVPView {
    void showWinner(String winningPlayerDisplayLabel);
    void clearWinnerDisplay();
    void clearButtons();
    void setButtonText(int row, int col, String text);
}
