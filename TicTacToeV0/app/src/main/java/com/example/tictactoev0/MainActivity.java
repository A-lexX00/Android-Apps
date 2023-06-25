package com.example.tictactoev0;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TicTacToe tttGame = new TicTacToe();
    private Button[][] buttons;
    private TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView( R.layout.activity_main );
        buildGuiByCode();
    }

    // When we click the screen v1
    public void onClick(View v) {
        Log.w("MainActivity", "Inside onClick, v = " + v);

        for (int row = 0; row < TicTacToe.SIDE; row++)
            for (int column = 0; column < TicTacToe.SIDE; column++)
                if (v == buttons[row][column])
                    update(row, column);
    }

    // Update the boxes v1
    public void update(int row, int col) {
        Log.w("MainActivity", "Inside update: " + row + ", " + col);
        int play = tttGame.play(row, col);
        if (play == 1)
            buttons[row][col].setText("X");
        else if (play == 2)
            buttons[row][col].setText("O");
        if (tttGame.isGameOver()) // game over
        {
            enableButtons(false);
            status.setBackgroundColor(Color.RED);
            status.setText("Game Over");
            showNewGameDialog(); // play again?
        }
    }

    // Implement the ButtonHandler event v1
    private class ButtonHandler implements View.OnClickListener {
        public void onClick(View v) {
            Log.w("MainActivity", "Inside onClick, v = " + v);

            for (int row = 0; row < TicTacToe.SIDE; row++)
                for (int column = 0; column < TicTacToe.SIDE; column++)
                    if (v == buttons[row][column])
                        update(row, column);
        }

        // Define resetButtons v4
        public void resetButtons() {
            for (int row = 0; row < TicTacToe.SIDE; row++)
                for (int col = 0; col < TicTacToe.SIDE; col++) buttons[row][col].setText("");
        }
    }

    // Enable play v2
    public void enableButtons(boolean enabled) {
        for (int row = 0; row < TicTacToe.SIDE; row++)
            for (int col = 0; col < TicTacToe.SIDE; col++)
                buttons[row][col].setEnabled(enabled);
    }

    // Define method resetButtons v4
    public void resetButtons() {
        for (int row = 0; row < TicTacToe.SIDE; row++)
            for (int col = 0; col < TicTacToe.SIDE; col++)
                buttons[row][col].setText("");
    }

    // Define method newGameDialog v4
    public void showNewGameDialog() {
        AlertDialog.Builder alert = new
                AlertDialog.Builder(this);
        alert.setTitle("This is fun");
        alert.setMessage("Play again?");
        PlayDialog playAgain = new PlayDialog();
        alert.setPositiveButton("YES", playAgain);
        alert.setNegativeButton("NO", playAgain);
        alert.show();
    }

    public void buildGuiByCode() {
        // Get width of the screen
        Point size = new Point();
        //YOUR CODE – Retrieve the width of the screen
        getWindowManager().getDefaultDisplay().getSize(size);
        //YOUR CODE – Assign one third of the width of the screen to a variable w
        int w = size.x / TicTacToe.SIDE;

        // Create the layout manager as a GridLayout
        GridLayout gridLayout = new GridLayout(this);
        gridLayout.setColumnCount(TicTacToe.SIDE);
        // Make the play box v3
        gridLayout.setRowCount(TicTacToe.SIDE + 1);
        status = new TextView(this);
        // Create vertical and horizontal specs v3
        GridLayout.Spec rowSpec = GridLayout.spec(3, 1);
        GridLayout.Spec columnSpec = GridLayout.spec(0, 3);
        // Declare params v3
        GridLayout.LayoutParams lp = new GridLayout.LayoutParams(rowSpec, columnSpec);

        // Create new text view v3
        status.setLayoutParams(lp);
        status.setBackgroundColor(Color.GREEN);

        // Add the textView to the GridLayout v3
        status.setWidth(TicTacToe.SIDE * w);
        status.setHeight(size.y / TicTacToe.SIDE);
        status.setGravity(Gravity.CENTER);
        status.setTextSize((float) (w * 0.15));
        status.setText("Play!!");
        // Create the buttons and add them to gridLayout v3
        gridLayout.addView(status);

        buttons = new Button[TicTacToe.SIDE][TicTacToe.SIDE];
        //Instantiate a ButtonHandler object
        ButtonHandler bh = new ButtonHandler();
        for (int row = 0; row < TicTacToe.SIDE; row++) {
            for (int col = 0; col < TicTacToe.SIDE; col++) {
                buttons[row][col] = new Button(this);
                //Set the textsize for each button to w * 0.2
                buttons[row][col].setTextSize((float) (w * 0.2));
                // Register the event for each button
                buttons[row][col].setOnClickListener(bh);
                gridLayout.addView(buttons[row][col], w, w);
            }
        }

        // Set gridLayout as the View of this Activity
        setContentView(gridLayout);
    }

    // Declare Tic Tac Toe Class v0
    public static class TicTacToe {
        public static final int SIDE = 3;
        private int turn;
        private int[][] game;

        public TicTacToe() {
            game = new int[SIDE][SIDE];
            resetGame();
        }

        public int play(int row, int col) {
            int currentTurn = turn;
            if (row >= 0 && col >= 0 && row < SIDE && col < SIDE
                    && game[row][col] == 0) {
                game[row][col] = turn;
                if (turn == 1)
                    turn = 2;
                else
                    turn = 1;

                return currentTurn;
            } else
                return 0;
        }

        public int whoWon() {
            int rows = checkRows();
            if (rows > 0)
                return rows;
            int columns = checkColumns();
            if (columns > 0)
                return columns;
            int diagonals = checkDiagonals();
            if (diagonals > 0)
                return diagonals;
            return 0;
        }

        protected int checkRows() {
            for (int row = 0; row < SIDE; row++)
                if (game[row][0] != 0 && game[row][0] == game[row][1] && game[row][1] == game[row][2])
                    return game[row][0];
            return 0;
        }

        protected int checkColumns() {
            for (int col = 0; col < SIDE; col++)
                if (game[0][col] != 0 && game[0][col] == game[1][col] && game[1][col] == game[2][col])
                    return game[0][col];
            return 0;
        }

        protected int checkDiagonals() {
            if (game[0][0] != 0 && game[0][0] == game[1][1]
                    && game[1][1] == game[2][2]) return game[0][0];
            if (game[0][2] != 0 && game[0][2] == game[1][1] && game[1][1] == game[2][0])
                return game[2][0];
            return 0;
        }

        public boolean canNotPlay() {
            boolean result = true;
            for (int row = 0; row < SIDE; row++)
                for (int col = 0; col < SIDE; col++)
                    if (game[row][col] == 0)
                        result = false;


            return result;
        }

        public boolean isGameOver() {
            return canNotPlay() || (whoWon() > 0);
        }

        public void resetGame() {
            for (int row = 0; row < SIDE; row++)
                for (int col = 0; col < SIDE; col++) game[row][col] = 0;
            turn = 1;
        }
    }

    // Define class PlayDialog v4
    private class PlayDialog implements DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int id) {
            // start a new game or exit the app
            if (id == -1) /* YES button */ {
                tttGame.resetGame();
                enableButtons(true);
                resetButtons();
                status.setBackgroundColor(Color.GREEN);
                status.setText("Play Again?");
            } else if (id == -2) // NO button
                MainActivity.this.finish();

        }
    }
}

