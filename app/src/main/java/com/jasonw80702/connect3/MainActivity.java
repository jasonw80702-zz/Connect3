package com.jasonw80702.connect3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    boolean gameActive = true;

    // 0 represents yellow, 1 represents red
    int activePlayer = 0;

    // 2 means not played state
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    int[][] winningPositions = {{0,1,2}, {3,4,5}, {6,7,8},
            {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};

    public void dropIn(View view) {
        ImageView counter = (ImageView) view;
        System.out.println(counter.getTag().toString());

        // 0 - 8
        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        // check if active, if not, gamestate becomes 0/1 depending on which player
        if (gameState[tappedCounter] == 2 && gameActive) {
            counter.setTranslationY(-1000f);
            gameState[tappedCounter] = activePlayer;
            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.red);
                activePlayer = 0;
            }
            counter.animate().translationYBy(1000f).setDuration(300);
        }

        for (int[] currentPosition : winningPositions) {
            if (gameState[currentPosition[0]] == gameState[currentPosition[1]]
                    && gameState[currentPosition[1]] == gameState[currentPosition[2]]
                    && gameState[currentPosition[0]] != 2) {

                String winner = "red";
                if (gameState[currentPosition[0]] == 0) {
                    winner = "yellow";
                }
                TextView winnerMessage = (TextView) findViewById(R.id.playAgainMessage);
                winnerMessage.setText("Player " + winner + " won!");
                gameActive = false;

                LinearLayout playAgain = (LinearLayout) findViewById(R.id.playAgainLayout);
                playAgain.setVisibility(View.VISIBLE);
            } else {

                boolean gameIsOver = true;

                for (int counterState : gameState) {
                    if (counterState == 2) gameIsOver = false;
                }

                if (gameIsOver) {

                    TextView winnerMessage = (TextView) findViewById(R.id.playAgainMessage);
                    winnerMessage.setText("It's a tie!");

                    LinearLayout playAgain = (LinearLayout) findViewById(R.id.playAgainLayout);
                    playAgain.setVisibility(View.VISIBLE);

                }
            }
        }
    }

    public void playAgain(View view) {
        LinearLayout playAgain = (LinearLayout) findViewById(R.id.playAgainLayout);
        playAgain.setVisibility(View.INVISIBLE);

        gameActive = true;

        activePlayer = 0;

        for (int i = 0; i < gameState.length; i++) {
            gameState[i] = 2;
        }

        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);

        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
