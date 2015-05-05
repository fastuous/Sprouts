package com.trumandeyoung.sprouts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.*;
import sun.rmi.runtime.Log;

public class GameRenderer extends GestureDetector.GestureAdapter {

    private GameState gameState;
    private ShapeRenderer renderer;
    private Camera camera;

    private int controlPoints;
    private int samplePoints;
    private float sampleDistance;

    private Vector2 v1, v2;

    public GameRenderer(GameState gameState) {
        this.gameState = gameState;
        camera = gameState.camera;

        renderer = new ShapeRenderer();

        v1 = new Vector2();
        v2 = new Vector2();
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        float factor = distance / initialDistance;
        if (distance > initialDistance) {
            camera.translate(0, 0, 0.2f * factor);
        } else camera.translate(0, 0, -0.2f * factor);
        Gdx.app.error("Sprouts", "zoomLevel = " + gameState.zoomLevel);


        return true;
    }


    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glLineWidth(12f);

//        camera.rotate(new Vector3(0, 0, 1), gameState.zoomLevel);
        camera.update();


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
        if (gameState.drawing && controlPoints > 3) {
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