package com.trumandeyoung.sprouts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Path;
import com.badlogic.gdx.math.Vector2;

public class GameRenderer extends GestureDetector.GestureAdapter {

    private GameState gameState;
    private ShapeRenderer renderer;

    private int samplePoints;
    private float sampleDistance;

    private Vector2 v1, v2;

    public GameRenderer(GameState gameState) {
        this.gameState = gameState;

        renderer = new ShapeRenderer();

        v1 = new Vector2();
        v2 = new Vector2();
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        gameState.zoomLevel *= distance / initialDistance;

        return true;
    }



    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glLineWidth(3f);

        Matrix4 mat = renderer.getProjectionMatrix();
        mat.scale(1.0f, 1.0f, gameState.zoomLevel);
        renderer.setProjectionMatrix(mat);
        renderer.begin(ShapeType.Line);
        renderer.setColor(Color.BLACK);
        for (Path<Vector2> path : gameState.getLines()) {
            samplePoints = ((CatmullRomSpline<Vector2>) path).controlPoints.length * 20;
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
        if (gameState.myTurn) {
            renderer.line(gameState.fingerPos, v1);
        }

        renderer.end();

        renderer.begin(ShapeType.Filled);
        renderer.setColor(Color.GREEN);
        for (Dot dot : gameState.getDots()) {
            renderer.setColor(dot.getColor());
            renderer.circle(dot.getX(), dot.getY(), 30);
        }
        renderer.end();
    }
}