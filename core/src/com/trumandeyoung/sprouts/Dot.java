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
        Color color = new Color(0.7f, 0.7f, 0.7f, 1);


        if (lines < 3) color = new Color(0.4f, 0.91f, 0.46f, 1);

        color.a = 0.6f;
        return color;
    }

    public int getLives() {
        return 3 - lines;
    }

    public void setLines(int lines) {
        this.lines = lines;
    }

    public void lineInc() {
        this.lines++;
    }

    public void lineDec() {
        this.lines--;
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
