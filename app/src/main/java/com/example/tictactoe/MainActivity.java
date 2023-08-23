package com.example.tictactoe;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView person1, person2;
    boolean player1 = true;
    LinearLayout llp1,llp2;
    //winner array list
    List<int[]> winnerCombination = new ArrayList<>();
    ImageView image1, image2, image3, image4, image5, image6, image7, image8, image9;
    //array of 9 integers with all values 0
    int[] boxPosition = {0, 0, 0, 0, 0, 0, 0, 0, 0};// each value represents the state of the image, 0 means image is empty, 1 means player 1 and 2 means player 2
public void changeTurn(ImageView image,int position){
    if (boxPosition[position] == 0) {
        if (player1) {
            boxPosition[position] = 1;
            player1 = false;
            image.setImageResource(R.drawable.o_image);
            llp1.setBackgroundResource(R.drawable.player_background);
            llp2.setBackgroundResource(R.drawable.active);
            checkWinner();
        } else {
            boxPosition[position] = 2;
            player1 = true;
            image.setImageResource(R.drawable.x_image);
            llp1.setBackgroundResource(R.drawable.active);
            llp2.setBackgroundResource(R.drawable.player_background);
            checkWinner();
        }
    }
}

public void checkWinner(){
    //loop through winnerCombination and check if box position match 1 or 2 in the winnerCombination
    for (int[] winner : winnerCombination) {//first loop 0,1,2
        if (boxPosition[winner[0]] == 1 && boxPosition[winner[1]] == 1 && boxPosition[winner[2]] == 1) {
            Toast.makeText(this, "player 1 wins", Toast.LENGTH_SHORT).show();
            }
        else if (boxPosition[winner[0]] == 2 && boxPosition[winner[1]] == 2 && boxPosition[winner[2]] == 2) {
            Toast.makeText(this, "player 2 wins", Toast.LENGTH_SHORT).show();
        }
        }
    }

    /**
     * Define the winning combinations
     */
    public void addWinnerCombination() {
        winnerCombination.add(new int[]{0, 1, 2});
        winnerCombination.add(new int[]{3, 4, 5});
        winnerCombination.add(new int[]{6, 7, 8});
        winnerCombination.add(new int[]{0, 3, 6});
        winnerCombination.add(new int[]{1, 4, 7});
        winnerCombination.add(new int[]{2, 5, 8});
        winnerCombination.add(new int[]{0, 4, 8});
        winnerCombination.add(new int[]{2, 4, 6});
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addWinnerCombination();
        person1 = findViewById(R.id.player1);
        person1.setText("First");
        person2 = findViewById(R.id.player2);
        person1.setText("Second");
        llp1 = findViewById(R.id.llp1);
        llp2 = findViewById(R.id.llp2);
        llp1.setBackgroundResource(R.drawable.active);
        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);
        image4 = findViewById(R.id.image4);
        image5 = findViewById(R.id.image5);
        image6 = findViewById(R.id.image6);
        image7 = findViewById(R.id.image7);
        image8 = findViewById(R.id.image8);
        image9 = findViewById(R.id.image9);
        image1.setOnClickListener(v -> {
            changeTurn(image1,0);
        });
        image2.setOnClickListener(v -> {
            changeTurn(image2,1);
        });
        image3.setOnClickListener(v -> {
            changeTurn(image3,2);
        });
        image4.setOnClickListener(v -> {
            changeTurn(image4,3);
        });
        image5.setOnClickListener(v -> {
            changeTurn(image5,4);
        });
        image6.setOnClickListener(v -> {
           changeTurn(image6,5);

        });
        image7.setOnClickListener(v -> {
            changeTurn(image7,6);
        });
        image8.setOnClickListener(v -> {
            changeTurn(image8,7);
        });
        image9.setOnClickListener(v -> {
            changeTurn(image9,8);
        });

    }
}