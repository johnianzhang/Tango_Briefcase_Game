package com.example.tango_briefcase_game;

import android.widget.ImageView;

import androidx.annotation.NonNull;

public class BriefcaseView {
    enum Status{CLOSED, WINNING, EMPTY}

    ImageView iv;
    Status curr;
    boolean winning;
    boolean selected;
    int indexNum;

    public BriefcaseView(ImageView iv, boolean winning, int indexNum){
        this.iv = iv;
        this.winning = winning;
        this.indexNum = indexNum;
        this.curr = Status.CLOSED;
        this.selected = false;
    }

    public void open(){
        if (winning){
            curr = Status.WINNING;
            iv.setImageResource(R.drawable.winning);
        }else{
            curr = Status.EMPTY;
            iv.setImageResource(R.drawable.empty);
        }
    }

    public void close(){
        curr = Status.CLOSED;
        iv.setImageResource(R.drawable.closed);
    }

    public void select(){
        selected = true;
        iv.setBackgroundColor(0xFF4CAF50); // Green
    }

    public void unselect(){
        selected = false;
        iv.setBackgroundColor(0xFFFFFFFF);
    }

    @NonNull
    @Override
    public String toString() {
        return "BriefcaseView #" + indexNum +
                "{ Current status: " + curr.toString() +
                ", Winning: " + (winning ? "Yes" : "No") +
                "}\n";
    }
}
