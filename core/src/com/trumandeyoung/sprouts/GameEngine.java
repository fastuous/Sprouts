package com.trumandeyoung.sprouts;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;

public class GameEngine extends InputAdapter {

	private GameState gameState;
	private CatmullRomSpline<Vector2> currentLine;
    private Dot startDot, endDot;
    private List<Vector2> currentPoints;
	private ListIterator<Vector2> iterator;


	public GameEngine(GameState gameState) {
		this.gameState = gameState;
        gameState.drawing = false;
    }

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		Vector2 first = new Vector2(screenX, Gdx.graphics.getHeight() - screenY);
		boolean init = false;
        gameState.drawing = false;

		for (Dot dot : gameState.getDots()) {
            if (dot.getVector().dst(first) < 50 && dot.getLives() > 0) {
                startDot = dot;
                startDot.lineInc();
                init = true;
                gameState.drawing = true;
            }

		}

		if (init) {
			currentLine = new CatmullRomSpline<Vector2>();
			currentPoints = new LinkedList<Vector2>();
			gameState.addPath(currentLine);

			iterator = currentPoints.listIterator();
			iterator.add(first);
			iterator.add(first);
            gameState.lastDot = first;

			updatePath();
			gameState.fingerPos = first.cpy();

		}
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (gameState.drawing) {
            Vector2 last = new Vector2(screenX, Gdx.graphics.getHeight() - screenY);
            boolean finish = false;
            for (Dot dot : gameState.getDots()) {
                if (dot.getVector().dst(last) < 50 && dot.getLives() > 0) {
                    endDot = dot;
                    finish = true;
                }
            }
            if (finish) {
                iterator = currentPoints.listIterator(currentPoints.size());
                iterator.add(last);
                iterator.add(last);
                gameState.lastDot = last;
                updatePath();
                Dot bisect = new Dot(new Vector2());
                currentLine.valueAt(bisect.getVector(), 0.5f);

                bisect.setLines(2);
                gameState.addDot(bisect);
                endDot.lineInc();
            } else {
                gameState.getLines().remove(currentLine);
                startDot.lineDec();
            }

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

            if (currentPoint.dst(lastPoint) > 70) {
                iterator.next();
				iterator.add(currentPoint);
                gameState.lastDot = currentPoint;
                updatePath();
			}

			gameState.fingerPos = currentPoint.cpy();
		}
		return true;
	}

//	public void update(float delta) {
//
//		if (Gdx.input.justTouched()) {
//			Vector2 v = new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
//			if (gameState.getLines().size() < turn) {
//				gameState.addPath(new CatmullRomSpline<Vector2>());
//				currentLine = (CatmullRomSpline<Vector2>) gameState.getLines().get(turn - 1);
//			}
//			currentPoints.add(v);
//			gameState.addDot(v);
//			Vector2 tempArr[] = new Vector2[currentPoints.size()];
//			tempArr = currentPoints.toArray(tempArr);
//			currentLine.set(tempArr, true);
//		}
//	}

	public void updatePath() {
		Vector2 tempArr[] = new Vector2[currentPoints.size()];
		tempArr = currentPoints.toArray(tempArr);
		currentLine.set(tempArr, false);
	}
}
