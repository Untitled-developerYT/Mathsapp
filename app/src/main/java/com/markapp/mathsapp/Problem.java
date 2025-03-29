package com.markapp.mathsapp;

import static java.lang.Math.abs;

import java.util.Random;

public class Problem {
    int a, b, c, res;
    int min, max, type, range;
    Random rnd;
    char[] myOperator= new char[]{'x', '÷', '+', '-'};
    public Problem(int set_min, int set_max, int set_type) {
        min= set_min;
        max= set_max;
        type= set_type;
        range= max-min;
        rnd= new Random();
        a = 1;
        b = 1;
        c = 1;
        res = 1;
    }
    public void generate() {
        a= min+rnd.nextInt(range);
        b= min+rnd.nextInt(range);
        if (type == 0) {
            b = b/10;
            if (b < 2) b = 2;
            res = a*b;
        }
        if (type == 1) {
            b = b/10;
            if (b < 2) b = 2;
            res = a;
            a = b*res;
        }
        if (type == 2) {
            res = a+b;
        }
        if (type == 3) {
            res= a-b;
        }
        if (type == 4) {
            res = min+rnd.nextInt(range);
            a = a / 10;
            if (abs(a) < 2) {
                a = 2;
            }
            c = a*res+b;
        }
    }

    public String queryString() {
        String query;
        if (type == 4) {
            query = Integer.toString(a)+"x + "+Integer.toString(b)+" = "+Integer.toString(c);
            query += "\nx = ?";
        }
        else {
            query = Integer.toString(a)+myOperator[type]+Integer.toString(b)+"=";
        }
        return query;
    }
    public boolean check(int ans) {
        return (res == ans);
    }
}
