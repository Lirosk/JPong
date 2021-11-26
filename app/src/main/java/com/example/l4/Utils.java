package com.example.l4;

import com.example.l4.Models.UserScore;

import java.util.ArrayList;

public class Utils {
    public static float rand(float min, float max) {
        return (float) (Math.random() * (max - min) + min);
    }
}
