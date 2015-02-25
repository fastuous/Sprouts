package com.trumandeyoung.sprouts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Path;
import com.badlogic.gdx.math.Vector2;

public class GameRenderer {

    private GameState state;
    private ShapeRenderer renderer;

    private int samplePoints = 100;
    private float sampleDistance = 1f / samplePoints;

    private Vector2 v1, v2;

    public GameRenderer(GameState state) {
        this.state = state;

        renderer = new ShapeRenderer();

        v1 = new Vector2();
        v2 = new Vector2();
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glLineWidth(2.5f);

        renderer.begin(ShapeType.Line);
        renderer.setColor(Color.BLACK);
        for (Path<Vector2> path : state.getLines()) {
            float val = 0;
            path.valueAt(v2, 0);
            while (val <= 1f) {
                path.valueAt(v1, val);
                renderer.line(v1, v2);
                v2 = v1.cpy();
                val += sampleDistance;
            }
        }
        renderer.end();

        renderer.begin(ShapeType.Filled);
        renderer.setColor(Color.GREEN);
        for (Vector2 dot : state.getDots()) {
            renderer.circle(dot.x, dot.y, 20);
        }
        renderer.end();
    }
}