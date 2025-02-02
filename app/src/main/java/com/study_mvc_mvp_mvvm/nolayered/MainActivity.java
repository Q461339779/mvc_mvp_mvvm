package com.study_mvc_mvp_mvvm.nolayered;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.study_mvc_mvp_mvvm.R;

import static com.study_mvc_mvp_mvvm.nolayered.MainActivity.Player.O;
import static com.study_mvc_mvp_mvvm.nolayered.MainActivity.Player.X;

/**
 * 改进为mvc模式
 * m 为model 用于数据和处理数据的
 * v 试图层
 * c 逻辑控制层
 *
 * 进行mvc的分层处理后 将数据层单独化为一层
 *
 * 但是controler 和 view 还是揉合在一起 导致代码太过于臃肿
 *
 *
 */
public class MainActivity extends AppCompatActivity {
    private static String TAG = MainActivity.class.getName();

    /**
     * ---------------------提出作为数据层start-------------------
     */
    public enum Player {X, O}

    public class Cell {
        private Player value;

        public Player getValue() {
            return value;
        }

        public void setValue(Player value) {
            this.value = value;
        }
    }

    private Cell[][] cells = new Cell[3][3];
    private Player winner;
    private GameState state;
    private Player currentTurn;

    private enum GameState {IN_PROGRESS, FINISHED}

    /**
     * ---------------------提出作为数据层end-------------------
     */

    /* Views */
    private ViewGroup buttonGrid;
    private View winnerPlayerViewGroup;
    private TextView winnerPlayerLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mvvm);
        winnerPlayerLabel = (TextView) findViewById(R.id.winnerPlayerLabel);
        winnerPlayerViewGroup = findViewById(R.id.winnerPlayerViewGroup);
        buttonGrid = (ViewGroup) findViewById(R.id.buttonGrid);
        restart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_mvx, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_reset:
                restart();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onCellClicked(View v) {
        Button button = (Button) v;
        String tag = button.getTag().toString();
        int row = Integer.valueOf(tag.substring(0, 1));
        int col = Integer.valueOf(tag.substring(1, 2));
        Log.i(TAG, "Click Row: [" + row + "," + col + "]");
        Player playerThatMoved = mark(row, col);
        if (playerThatMoved != null) {
            button.setText(playerThatMoved.toString());
            if (getWinner() != null) {
                winnerPlayerLabel.setText(playerThatMoved.toString());
                winnerPlayerViewGroup.setVisibility(View.VISIBLE);
            }
        }

    }

    /**
     * 开始一个新游戏，清楚计分板和状态
     */
    public void restart() {
        /**2---------------------提出作为数据层start-------------------*/
        clearCells();
        winner = null;
        currentTurn = X;
        state = GameState.IN_PROGRESS;
        /**2---------------------提出作为数据层end-------------------*/
        /*Reset View*/
        winnerPlayerViewGroup.setVisibility(View.GONE);
        winnerPlayerLabel.setText("");
        for (int i = 0; i < buttonGrid.getChildCount(); i++) {
            ((Button) buttonGrid.getChildAt(i)).setText("");
        }
    }

    /**3---------------------提出作为数据层start-------------------*/


    /**
     * 标记当前的选手选择了哪行哪列
     * 如果不是在没有选中的9个格子里面点击将视作无效；
     * 另外，如果游戏已经结束，本次标记忽略
     *
     * @param row 0..2
     * @param col 0..2
     * @return 返回当前选手，如果点击无效发挥为null
     */
    public Player mark(int row, int col) {
        Player playerThatMoved = null;
        if (isValid(row, col)) {
            cells[row][col].setValue(currentTurn);
            playerThatMoved = currentTurn;
            if (isWinningMoveByPlayer(currentTurn, row, col)) {
                state = GameState.FINISHED;
                winner = currentTurn;
            } else {
                // 切换到另外一起棋手，继续
                flipCurrentTurn();
            }
        }

        return playerThatMoved;
    }

    public Player getWinner() {
        return winner;
    }

    private void clearCells() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cells[i][j] = new Cell();
            }
        }
    }

    private boolean isValid(int row, int col) {
        if (state == GameState.FINISHED) {
            return false;
        } else if (isCellValueAlreadySet(row, col)) {
            return false;
        } else {
            return true;
        }
    }

    private boolean isCellValueAlreadySet(int row, int col) {
        return cells[row][col].getValue() != null;
    }

    /**
     * @param player
     * @param currentRow
     * @param currentCol
     * @return 如果当前行、当前列、或者两对角线为同一位棋手下的棋子返回为真
     */
    private boolean isWinningMoveByPlayer(Player player, int currentRow, int currentCol) {

        return (cells[currentRow][0].getValue() == player         // 3-行
                && cells[currentRow][1].getValue() == player
                && cells[currentRow][2].getValue() == player
                || cells[0][currentCol].getValue() == player      // 3-列
                && cells[1][currentCol].getValue() == player
                && cells[2][currentCol].getValue() == player
                || currentRow == currentCol            // 3-对角线
                && cells[0][0].getValue() == player
                && cells[1][1].getValue() == player
                && cells[2][2].getValue() == player
                || currentRow + currentCol == 2    // 3-反对角线
                && cells[0][2].getValue() == player
                && cells[1][1].getValue() == player
                && cells[2][0].getValue() == player);
    }

    private void flipCurrentTurn() {
        currentTurn = currentTurn == X ? O : X;
    }

    /**3---------------------提出作为数据层end-------------------*/
}
