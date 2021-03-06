package com.trumandeyoung.sprouts;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Path;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import static com.badlogic.gdx.math.Intersector.intersectSegments;

public class GameEngine extends InputAdapter {

    private GameState gameState;
    private CatmullRomSpline<Vector2> currentLine;
    private Dot startDot, endDot;
    private List<Vector2> currentPoints;
    private ListIterator<Vector2> iterator;
    private boolean leftStartDot;


    public GameEngine(GameState gameState) {
        this.gameState = gameState;
        gameState.drawing = false;
    }

    private boolean inStartDot(Vector2 pos) {

        if (startDot.getVector().dst(pos) < gameState.dotRadius) return true;


        return false;
    }

    private boolean collision(Vector2 p0, Vector2 p1, Vector2 q0, Vector2 q1) {
        Vector2 t = new Vector2();

        if (intersectSegments(p0, p1, q0, q1, t)) {
            if (t.equals(p0) || t.equals(p1) || t.equals(q0) || t.equals(q1)) return false;
            else return true;
        }

        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
//        int x = Gdx.input.getX();
//        int y = Gdx.graphics.getHeight() - Gdx.input.getY();
//        int z = 0;
//        Vector3 v = gameState.camera.unproject(new Vector3(x, y, z));
        Vector2 first = new Vector2(screenX, Gdx.graphics.getHeight() - screenY);
        gameState.drawing = false;

        for (Dot dot : gameState.getDots()) {
            if (dot.getVector().dst(first) < gameState.dotRadius && dot.getLives() > 0) {
                startDot = dot;
                startDot.lineInc();

                currentLine = new CatmullRomSpline<Vector2>();
                gameState.currentLine = currentLine;
                currentPoints = new LinkedList<Vector2>();
                gameState.addPath(currentLine);

                iterator = currentPoints.listIterator();
                iterator.add(startDot.getVector());
                iterator.add(startDot.getVector());


                updatePath();
                gameState.fingerPos = first.cpy();
                leftStartDot = false;
                gameState.drawing = true;

                break;
            }

        }

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (gameState.drawing) {
            gameState.getAllLines().remove(currentLine);
            gameState.currentLine = null;
            startDot.lineDec();
            gameState.drawing = false;
        }

        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (gameState.drawing) {
            iterator = currentPoints.listIterator(currentPoints.size());
            Vector2 lastPoint = iterator.previous();
            Vector2 currentPoint = new Vector2(screenX, Gdx.graphics.getHeight() - screenY);

            if (!inStartDot(currentPoint)) leftStartDot = true;

            if (currentPoint.dst(lastPoint) > 5) {

                // start collision checking
                for (Path<Vector2> path : gameState.getAllButCurrentLine()) {

                    int controlPoints = ((CatmullRomSpline<Vector2>) path).controlPoints.length;
                    int samplePoints = controlPoints * 20;
                    float sampleDistance = 1f / samplePoints;
                    Vector2 v1 = new Vector2(), v2 = new Vector2();
                    float val = 0;
                    path.valueAt(v2, 0);
                    while (val <= 1f) {
                        path.valueAt(v1, val);
                        if (collision(lastPoint, currentPoint, v1, v2)) {
                            gameState.getAllLines().remove(currentLine);
                            gameState.currentLine = null;
                            gameState.drawing = false;
                            startDot.lineDec();

                            return true;
                        }
                        v2 = v1.cpy();
                        val += sampleDistance;
                    }

                }

                iterator = currentPoints.listIterator(currentPoints.size());
                iterator.add(currentPoint);

            }

            // check for line completion
            for (Dot dot : gameState.getDots()) {
                if (dot.getVector().dst(currentPoint) < gameState.dotRadius && leftStartDot) {
                    if (dot.getLives() > 0) {
                        endDot = dot;

                        iterator = currentPoints.listIterator(currentPoints.size());
                        iterator.add(endDot.getVector());
                        iterator.add(endDot.getVector());
                        updatePath();
                        Dot bisect = new Dot(new Vector2());
                        currentLine.valueAt(bisect.getVector(), 0.5f);

                        bisect.setLines(2);
                        gameState.addDot(bisect);
                        endDot.lineInc();

                        gameState.drawing = false;
                        break;
                    } else {
                        gameState.getAllLines().remove(currentLine);
                        gameState.currentLine = null;
                        gameState.drawing = false;
                        startDot.lineDec();
                    }
                }
            }

            updatePath();
            gameState.fingerPos = currentPoint.cpy();
        }
        return true;
    }


    public void updatePath() {
        Vector2 tempArr[] = new Vector2[currentPoints.size()];
        tempArr = currentPoints.toArray(tempArr);
        currentLine.set(tempArr, false);
    }
}
