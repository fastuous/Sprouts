package com.trumandeyoung.sprouts;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.*;
import sun.rmi.runtime.Log;

public class GameRenderer extends GestureDetector.GestureAdapter {

    private GameState gameState;
    private Game game;
    private ShapeRenderer renderer;
    private SpriteBatch batch;
    private Texture back;
    private Camera camera;

    private int controlPoints;
    private int samplePoints;
    private float sampleDistance;

    private Vector2 v1, v2;

    public GameRenderer(GameState gameState, Game game) {
        this.gameState = gameState;
        this.game = game;
        camera = gameState.camera;

        renderer = new ShapeRenderer();
        batch = new SpriteBatch();
        back = new Texture(Gdx.files.internal("button.jpg"));

        v1 = new Vector2();
        v2 = new Vector2();
    }



    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glLineWidth(12f);

        if (Gdx.input.isTouched()) {
            int x = Gdx.input.getX();
            int y = Gdx.input.getY();
            if (x < 200 && y < 200) {
                game.setScreen(new SettingsScreen(game));
            }
        }

        batch.begin();
        batch.draw(back, 0, Gdx.graphics.getHeight() - 200);
        batch.end();

        renderer.setProjectionMatrix(camera.combined);
        renderer.begin(ShapeType.Line);
        renderer.setColor(new Color(0.7f, 0.7f, 0.7f, 1));

        for (Path<Vector2> path : gameState.getAllLines()) {
            controlPoints = ((CatmullRomSpline<Vector2>) path).controlPoints.length;
            if (controlPoints < 4) continue;
            samplePoints = controlPoints * 20;
            sampleDistance = 1f / samplePoints;
            float val = 0;
            path.valueAt(v2, 0);
            while (val <= 1f) {
                path.valueAt(v1, val);
                renderer.line(v1, v2);
                v2 = v1.cpy();
                val += sampleDistance;
            }

        }
        if (gameState.drawing && controlPoints > 1) {
            renderer.line(gameState.fingerPos, v1);
        }

        renderer.end();

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        renderer.begin(ShapeType.Filled);

        for (Dot dot : gameState.getDots()) {
            renderer.setColor(dot.getColor());
            Vector2 v = new Vector2(dot.getVector());

            renderer.circle(dot.getX(), dot.getY(), gameState.dotRadius);
        }
        renderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }
}