package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tictactoe.helper.SharedPref;

public class NameActivity extends AppCompatActivity {
    EditText name1, name2;
    Button startGame;
    SharedPref sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);
        name1 = findViewById(R.id.player1Name);
        name2 = findViewById(R.id.player2Name);
        startGame = findViewById(R.id.startGame);
        name1.setText(SharedPref.getString(this, SharedPref.Player1));
        name2.setText(SharedPref.getString(this, SharedPref.Player2));
        startGame.setOnClickListener(v -> {
        String player1Name = name1.getText().toString().trim();
        String player2Name = name2.getText().toString().trim();

            if(player1Name.isEmpty() || player2Name.isEmpty()){
                Toast.makeText(this, "Please enter both names", Toast.LENGTH_SHORT).show();
            }
            else{
                Intent intent = new Intent(NameActivity.this, MainActivity.class);
                intent.putExtra("player1Name", player1Name);
                intent.putExtra("player2Name", player2Name);

                SharedPref.setString(this,SharedPref.Player1, player1Name);
                SharedPref.setString(this,SharedPref.Player2, player2Name);
                startActivity(intent);
                finish();
            }
        });
    }
}