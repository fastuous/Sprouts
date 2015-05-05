package com.trumandeyoung.sprouts;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class Sprouts extends Game {

    @Override
    public void create() {

        setScreen(new GameScreen(2));
    }

}
