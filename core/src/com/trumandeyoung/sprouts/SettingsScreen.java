package com.trumandeyoung.sprouts;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Truman on 5/5/2015.
 */
public class SettingsScreen implements Screen {

    private Game game;
    private SpriteBatch batch;
    private Texture bg;

    public SettingsScreen(Game game) {
        this.game = game;
        batch = new SpriteBatch();
        bg = new Texture(Gdx.files.internal("choose.jpg"));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isTouched()) {
            int y = Gdx.input.getY();
            if (y > 1080 && y < 1270) {
                game.setScreen(new GameScreen(game, 1));
            } else if (y > 1355 && y < 1545) {
                game.setScreen(new GameScreen(game, 2));
            } else if (y > 1630 && y < 1820) {
                game.setScreen(new GameScreen(game, 3));
            } else if (y > 1905 && y < 2100) {
                game.setScreen(new GameScreen(game, 4));
            }
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(bg, 0, 0);
        batch.end();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
