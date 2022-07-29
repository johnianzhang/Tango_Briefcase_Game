package com.example.tango_briefcase_game;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;

public class BriefcaseArray {
    ArrayList<BriefcaseView> arr;
    int originalSelected = -1;

    public BriefcaseArray(BriefcaseView[] arr) {
        this.arr = new ArrayList<>(Arrays.asList(arr));
    }

    public void selectOne(){
        for (int i=0; i<arr.size(); i++){
            int finalI = i;
            arr.get(i).iv.setOnClickListener(v -> {
                for (int j=0; j<arr.size(); j++){
                    if (finalI ==j) {
                        arr.get(j).select();
                        originalSelected = finalI;
                    }else{
                        arr.get(j).unselect();
                    }
                }
            });
        }
    }

    public void disableAllClick(){
        for (BriefcaseView briefcaseView : arr) {
            briefcaseView.iv.setOnClickListener(v -> {});
        }
    }

    public void unselectAll(){
        disableAllClick();
        for (BriefcaseView briefcaseView : arr) {
            briefcaseView.unselect();
        }
    }

    public void openAll(){
        for (BriefcaseView briefcaseView : arr) {
            briefcaseView.open();
        }
    }

    public void closeAll(){
        for (BriefcaseView briefcaseView : arr) {
            briefcaseView.close();
        }
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        for (BriefcaseView bc : arr){
            out.append(bc.toString());
        }
        return "BriefcaseArray{" +
                "arr=" + out +
                '}';
    }
}
