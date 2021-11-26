package com.example.l4.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.l4.Adapters.UserScoreRecyclerViewAdapter;
import com.example.l4.DBHelper;
import com.example.l4.Models.UserScore;
import com.example.l4.R;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    EditText etUsername;
    Button btnLogin;
    RecyclerView rv;
    UserScoreRecyclerViewAdapter adapter;
    ArrayList<UserScore> userScores = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setFields();
        setListeners();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setFields() {
        DBHelper dbHelper = new DBHelper(getApplicationContext());

        userScores = new ArrayList<>();
        userScores.add(new UserScore("aboba", 2));
        userScores.add(new UserScore("pupa", 4));
        userScores.add(new UserScore("lupa", 1));
        userScores.add(new UserScore("bara", 9));
        userScores.add(new UserScore("bere", 0));

        for (UserScore userScore : userScores) {
            if (!dbHelper.add(userScore)) {
                break;
            }
        }

        userScores = (ArrayList<UserScore>) dbHelper.getTopN(100);

        etUsername = findViewById(R.id.etUsername);
        btnLogin = findViewById(R.id.btnLogin);
        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));

//        userScores.sort(Comparator.comparing(UserScore::getScore));
//        Collections.reverse(userScores);

        adapter = new UserScoreRecyclerViewAdapter(this, userScores);
        rv.setAdapter(adapter);
    }

    private void setListeners() {
        etUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = s.toString();
                if (text.isEmpty()) {
                    rv.setAdapter(adapter);
                }
                else {
                    rv.setAdapter(
                            new UserScoreRecyclerViewAdapter(
                                    getApplicationContext(),
                                    new DBHelper(
                                            getApplicationContext()
                                        ).getlikeUsername(text, 100))
                    );
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnLogin.setOnClickListener((v -> {
            String username = etUsername.getText().toString();
            if (username.isEmpty()) {
                Toast.makeText(this, "Enter nickname", Toast.LENGTH_SHORT).show();
                return;
            }
            ScoresActivity.currentUsername = username;

//            DBHelper db = new DBHelper(getApplicationContext());
//            if (db.has(username)) {
//                Toast.makeText(this, "Pick another nickname", Toast.LENGTH_SHORT).show();
//                return;
//            }

            Intent intent = new Intent(this, GameActivity.class);
            startActivity(intent);
        }));
    }
}