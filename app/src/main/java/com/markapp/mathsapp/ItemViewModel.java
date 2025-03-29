package com.markapp.mathsapp;

import androidx.lifecycle.ViewModel;

import java.io.RandomAccessFile;

public class ItemViewModel extends ViewModel {
    private int ActType = 0;
    private int min = 0;
    private int max = 101;
    private int correct = 0;
    private int wrong = 0;

    public RandomAccessFile file;

    public void selectItem(int NewType) {
        ActType = NewType;
    }
    public void set_min(int set_min){
        min = set_min;
    }
    public void set_max(int set_max) {
        max = set_max;
    }
    public int getSelectedItem() {
        return ActType;
    }
    public int getMin() {
        return min;
    }
    public int getMax() {
        return max;
    }
    public int getCorrect() { return correct; }
    public int getWrong() { return wrong; }
    public void setCorrect(int c) { correct = c; }
    public void setWrong(int w) { wrong = w; }
    public void readData() {
        try {
            file.seek(0);
            min= file.readInt();
            max= file.readInt();
            correct= file.readInt();
            wrong= file.readInt();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void writeData() {
        try {
            file.seek(0);
            file.writeInt(min);
            file.writeInt(max);
            file.writeInt(correct);
            file.writeInt(wrong);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
