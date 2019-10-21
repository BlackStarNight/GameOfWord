package com.blackapp.gameofwords;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public Button NewGame;  //кнопка новой игры
    public TextView Balls;  //количество баллов
    public EditText WordInput;  //вводимое слово
    public TextView Word;     //слово из которого нужно составить
    public Button Enter;   //кнопка ввода
    public InputStream is;
    public String[] Words;   //слова из текстового файла
    public int m;
    public int ballsInt;
    public int rand;
    public String underWord;   //перемешанное слово

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NewGame = (Button) findViewById(R.id.NewGameButton);
        Balls = (TextView) findViewById(R.id.BallView);
        WordInput = (EditText) findViewById(R.id.EditTextView);
        Word = (TextView) findViewById(R.id.Word);
        Enter = (Button) findViewById(R.id.Enter);
        newGame();
        NewGame.setOnClickListener(this);
        Enter.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.NewGameButton:    //срабатывает при нажатии на кнопку новой игры
                newGame();
                Toast.makeText(MainActivity.this,"Новая Игра", Toast.LENGTH_SHORT).show();
                break;
            case R.id.Enter:    //срабатывает при нажатии на ввод
                if (check()){   //обработка метода check, который ниже
                    ballsInt++;
                    update();
                    newWord();
                    Toast.makeText(MainActivity.this,"Верно, +1 балл", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    newGame();
                    Toast.makeText(MainActivity.this,"Проигрыш, старт новой игры", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void fileChecker() {   //проверка текстового файла и получение из него слов
        try {
        is = getResources().openRawResource(R.raw.text);
        InputStreamReader reader = new InputStreamReader(is);
        BufferedReader buffer = new BufferedReader(reader);
        m = Integer.parseInt(buffer.readLine());  //получение количества слов
        Words = new String[m];
        for (int i=0;i<m;i++){
            Words[i] = buffer.readLine();   //записывание всех слов в массив стрингов
        }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getWord(String word){  //метод "перемешки" слова. после этого метода все букы в слове перемешиваются
        String abs = "";
        ArrayList<Character> r = new ArrayList<>(word.length());
        for (int i=0;i<word.length();i++){
            r.add(word.charAt(i));
        }
        Collections.shuffle(r);
        for (int i=0;i<word.length();i++) {
            abs += r.get(i);
        }
        return abs;
    }

    public void newWord(){   //метод получения нового слова
        rand = (int) (Math.random() * 10);
        underWord = getWord(Words[rand]);
        Word.setText(underWord);
        WordInput.setText(underWord);
    }

    public boolean check(){   //метод сравнения введеного слова и начального
        if (String.valueOf(WordInput.getText()).equals(Words[rand])) {
            return true;
        } else return false;
    }

    public void update(){   //обновление балла
        Balls.setText(String.valueOf(ballsInt));
    }

    public void newGame(){  //метод старта новой игры
        fileChecker();
        newWord();
        ballsInt = 0;
        update();
    }

}
