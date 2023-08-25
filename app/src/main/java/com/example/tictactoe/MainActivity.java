package com.example.tictactoe;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tictactoe.helper.DatabaseCrud;
import com.example.tictactoe.helper.SharedPref;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import nl.dionsegijn.konfetti.core.Party;
import nl.dionsegijn.konfetti.core.PartyFactory;
import nl.dionsegijn.konfetti.core.emitter.Emitter;
import nl.dionsegijn.konfetti.core.emitter.EmitterConfig;
import nl.dionsegijn.konfetti.core.models.Size;
import nl.dionsegijn.konfetti.xml.KonfettiView;

public class MainActivity extends AppCompatActivity {
    TextView person1, person2;

    int count = 0;
    boolean player1 = true;
    LinearLayout llp1, llp2;
    //winner array list
    List<int[]> winnerCombination = new ArrayList<>();
    ImageView image1, image2, image3, image4, image5, image6, image7, image8, image9;
    Button leaderboardBtn;
    //array of 9 integers with all values 0
    int[] boxPosition = {0, 0, 0, 0, 0, 0, 0, 0, 0};// each value represents the state of the image, 0 means image is empty, 1 means player 1 and 2 means player 2

    public void changeTurn(ImageView image, int position) {
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

    public void checkWinner() {
        //loop through winnerCombination and check if box position match 1 or 2 in the winnerCombination
        for (int[] winner : winnerCombination) {//first loop 0,1,2
            if (boxPosition[winner[0]] == 1 && boxPosition[winner[1]] == 1 && boxPosition[winner[2]] == 1) {
                showResult(1);
                Toast.makeText(this, "player 1 wins", Toast.LENGTH_SHORT).show();
            } else if (boxPosition[winner[0]] == 2 && boxPosition[winner[1]] == 2 && boxPosition[winner[2]] == 2) {
                showResult(2);
                Toast.makeText(this, "player 2 wins", Toast.LENGTH_SHORT).show();
            }
        }
        count++;
        if (count == 9) {
            showResult(0);
            Toast.makeText(this, "Draw", Toast.LENGTH_SHORT).show();
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

    public  void showLeaderboard(){
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Leaderboard");
        DatabaseCrud databaseCrud = new DatabaseCrud(this);
        alertDialog.setMessage(databaseCrud.getPlayerWithScore());
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                (dialog, which) -> dialog.dismiss());
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Clear",
                (dialog, which) -> {
                    databaseCrud.deleteAllPlayer();
                    dialog.dismiss();
                });
        alertDialog.show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addWinnerCombination();
        person1 = findViewById(R.id.tvPlayer1);
        person2 = findViewById(R.id.tvPlayer2);
        leaderboardBtn = findViewById(R.id.leaderboardBtn);
        leaderboardBtn.setOnClickListener(v -> {
                    showLeaderboard();
        }
        );
        //get from extras and set name to textview
        Intent intent = getIntent();
        String player1Name = intent.getStringExtra("player1Name");
        String player2Name = intent.getStringExtra("player2Name");
        person1.setText(player1Name);
        person2.setText(player2Name);
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
            changeTurn(image1, 0);
        });
        image2.setOnClickListener(v -> {
            changeTurn(image2, 1);
        });
        image3.setOnClickListener(v -> {
            changeTurn(image3, 2);
        });
        image4.setOnClickListener(v -> {
            changeTurn(image4, 3);
        });
        image5.setOnClickListener(v -> {
            changeTurn(image5, 4);
        });
        image6.setOnClickListener(v -> {
            changeTurn(image6, 5);

        });
        image7.setOnClickListener(v -> {
            changeTurn(image7, 6);
        });
        image8.setOnClickListener(v -> {
            changeTurn(image8, 7);
        });
        image9.setOnClickListener(v -> {
            changeTurn(image9, 8);
        });

    }

    void showResult(int id) {
        LayoutInflater factory = LayoutInflater.from(this);
        final View resultDialogView = factory.inflate(R.layout.result_ui, null);
        final AlertDialog builder = new AlertDialog.Builder(this).create();
        builder.setView(resultDialogView);
       String message ="";
        if(id==0)
            message="Match Draw.";
        else if(id==1)
            message="Hurray!you " + SharedPref.getString(this,SharedPref.Player1) + " won the match";
        else
            message="Hurray!you " + SharedPref.getString(this,SharedPref.Player2)+ " won the match";
        message = message + "\nDo you want to restart the match?";
        builder.setCancelable(true);
        TextView textView = resultDialogView.findViewById(R.id.messageText);
        if (id != 0) {
            DatabaseCrud databaseCrud = new DatabaseCrud(this);
            if (id == 1) {
                databaseCrud.updateScore(person1.getText().toString());
            } else {
                databaseCrud.updateScore(person2.getText().toString());
            }

            KonfettiView konfettiView = resultDialogView.findViewById(R.id.konfettiView);
            EmitterConfig emitterConfig = new Emitter(2L, TimeUnit.SECONDS).perSecond(100);
            Party party = new PartyFactory(emitterConfig)
                    .angle(360)
                    .spread(90)
                    .setSpeedBetween(1f, 30f)
                    .timeToLive(4000L)
                    .sizes(new Size(12, 8f, 0.2f))
                    .build();
            konfettiView.start(party);
            Party party1 = new PartyFactory(emitterConfig)
                    .angle(180)
                    .spread(90)
                    .setSpeedBetween(1f, 10f)
                    .timeToLive(2000L)
                    .sizes(new Size(12, 5f, 0.2f))
                    .build();
            konfettiView.start(party1);
            MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.wow);
            mediaPlayer.start();
        }

        textView.setText(message );
        resultDialogView.findViewById(R.id.startAgainButton).setOnClickListener(v -> {
            //your business logic
            restart();
            builder.dismiss();
        });
        builder.show();

    }

    public void restart() {
        count = 0;
        player1 = true;
        llp1.setBackgroundResource(R.drawable.active);
        llp2.setBackgroundResource(R.drawable.player_background);
        image1.setImageResource(R.drawable.player_background);
        image2.setImageResource(R.drawable.player_background);
        image3.setImageResource(R.drawable.player_background);
        image4.setImageResource(R.drawable.player_background);
        image5.setImageResource(R.drawable.player_background);
        image6.setImageResource(R.drawable.player_background);
        image7.setImageResource(R.drawable.player_background);
        image8.setImageResource(R.drawable.player_background);
        for (int i = 0; i < boxPosition.length; i++) {
            boxPosition[i] = 0;
        }
    }
}