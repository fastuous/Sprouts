package com.trumandeyoung.sprouts;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;


public class Dot {
    private int lines;
    private Vector2 position;

    public Dot(Vector2 v) {
        position = v;
        lines = 0;
    }

    public Color getColor() {
        Color color = Color.BLACK;

        if (lines < 3) color = Color.GREEN;

        return color;
    }

    public int getLives() {
        return 3 - lines;
    }

    public Vector2 getVector() {
        return position;
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }
}
