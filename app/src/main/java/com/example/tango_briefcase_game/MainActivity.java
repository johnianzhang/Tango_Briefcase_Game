package com.example.tango_briefcase_game;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private final Random rd = new Random(System.currentTimeMillis());
    int winning;
    boolean switched;
    int indexOpened;
    int indexLeft;

    BriefcaseArray bcArray;
    Button but;

    String instruction;
    TextView txt;

    GameChecker gc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        buttonAction();
    }

    void initialize(){
        winning = rd.nextInt(3);
        BriefcaseView bc1 = new BriefcaseView(findViewById(R.id.bc1), winning==0, 0);
        BriefcaseView bc2 = new BriefcaseView(findViewById(R.id.bc2), winning==1, 1);
        BriefcaseView bc3 = new BriefcaseView(findViewById(R.id.bc3), winning==2, 2);
        bcArray = new BriefcaseArray(new BriefcaseView[]{bc1, bc2, bc3});
        but = findViewById(R.id.button);
        txt = findViewById(R.id.instruction);
        howToPlayButton();
        quitButton();
    }

     void alert(String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton("OK", (dialog, which) -> {});
        builder.show();
    }

    void buttonAction(){
        but.setOnClickListener(v -> {
            String status = but.getText().toString();
            String txtOnButton;
            switch (status) {
                case "START":
                case "PLAY\nAGAIN":
                    startGame();
                    txtOnButton = "OK";
                    break;
                case "OK":
                    if(bcArray.originalSelected != -1) {
                        eliminate();
                        txtOnButton = "SWITCH";
                        setStayChoice();
                    }else{
                        alert("Warning","You must make a choice first! ");
                        txtOnButton = "OK";
                    }
                    break;
                case "SWITCH":
                    switchChoice();
                    txtOnButton = "RESULT";
                    break;
                default:
                    showResult();
                    txtOnButton = "PLAY\nAGAIN";
                    break;
            }
            but.setText(txtOnButton);
        });

    }

    void howToPlayButton(){
        FloatingActionButton howToPlay = findViewById(R.id.howToPlay);

        howToPlay.setOnClickListener(v -> {
            final String htp = "The host gets a contestant to play 2 rounds in a game " +
                    "where they must try to identify a prize hidden within 1 of 3 briefcases\n\n" +
                    "In their first round the contestant chooses a briefcase that they believe contains the prize\n\n" +
                    "The host then goes ahead by opening a briefcase that the host knows does not contain the prize\n\n" +
                    "The contestant is then asked if they would like to switch their original choice\n\n" +
                    "The briefcase is then revealed and we find out if the contestant won. \n\n" +
                    "GOOD LUCK!";
            alert("How To Play", htp);
        });
    }

    void quitButton(){
        ImageButton quit = findViewById(R.id.quit);
        quit.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("QUIT");
            builder.setMessage("Do you want to quit the game? ");
            builder.setPositiveButton("YES", (dialog, which) -> System.exit(1));
            builder.setNegativeButton("NO", (dialog, which) -> {});
            builder.show();
        });
    }

    void startGame(){
        bcArray.closeAll();
        instruction = "Please choose a briefcase you think it contains the prize";
        txt.setText(instruction);
        bcArray.selectOne();
    }

    void eliminate(){
        do {
            indexOpened = rd.nextInt(3);
        }while (indexOpened == bcArray.originalSelected || indexOpened == winning);
        indexLeft = bcArray.arr.size() - bcArray.originalSelected - indexOpened;
        bcArray.arr.get(indexOpened).open();
        instruction = "You choose briefcase "+(bcArray.originalSelected+1)+".\nThe briefcase " +
                (indexOpened+1) + " is empty. \nClick \"SWITCH\" switch to briefcase "+
                (indexLeft+1) + ", \nor click the highlighted briefcase to stay your choice.";
        txt.setText(instruction);
    }

    void switchChoice() {
        bcArray.arr.get(bcArray.originalSelected).unselect();
        bcArray.arr.get(indexLeft).select();
        instruction = "You switched your original choice. Click \"RESULT\" to see result!";
        txt.setText(instruction);
        switched = true;
    }

    void setStayChoice(){
        bcArray.disableAllClick();
        switched = false;
        bcArray.arr.get(bcArray.originalSelected).iv.setOnClickListener(v -> {
            instruction = "You stayed on your original choice. Click \"RESULT\" to see result!";
            txt.setText(instruction);

            String r = "RESULT";
            but.setText(r);
        });
    }

    void showResult(){
        bcArray.unselectAll();
        gc = new GameChecker(winning, bcArray.originalSelected, switched);
        instruction = gc.getResult() ? "You Win! Congrats!" : "You Lost! Try again? ";
        txt.setText(instruction);

        bcArray.originalSelected = -1;
        bcArray.openAll();
    }



}