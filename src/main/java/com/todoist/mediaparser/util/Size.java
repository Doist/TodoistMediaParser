package com.todoist.mediaparser.util;

public class Size {
    public String key;
    public int smallestSide;

    public Size(String key, int smallestSide) {
        this.key = key;
        this.smallestSide = smallestSide;
    }
}
