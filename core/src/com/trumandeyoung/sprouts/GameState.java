package com.trumandeyoung.sprouts;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Path;
import com.badlogic.gdx.math.Vector2;

/**
 * Tentatively the object to be serialized and passed over the network.
 * 
 * @author Truman
 */
public class GameState {

	public OrthographicCamera camera;
	private List<Path<Vector2>> lines;
	public Path<Vector2> currentLine;
	private List<Dot> dots;
	public int initDots = 2;
	public float zoomLevel = 0;
	public int turn;
    public boolean drawing;
    public Vector2 fingerPos;
    public Vector2 lastDot;

	public int dotRadius = 80;

	public GameState(int startDots) {
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		lines = new LinkedList<Path<Vector2>>();
		dots = new LinkedList<Dot>();
		if (startDots == 1) {
			dots.add(new Dot(new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2)));
		} else if (startDots == 2) {
			dots.add(new Dot(new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2 + 400)));
			dots.add(new Dot(new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2 - 400)));
		} else if (startDots == 3) {
			dots.add(new Dot(new Vector2(Gdx.graphics.getWidth() / 2 - 300, Gdx.graphics.getHeight() / 2 + 300)));
			dots.add(new Dot(new Vector2(Gdx.graphics.getWidth() / 2 + 300, Gdx.graphics.getHeight() / 2 + 300)));
			dots.add(new Dot(new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2 - 300)));
		} else {
			dots.add(new Dot(new Vector2(Gdx.graphics.getWidth() / 2 - 400, Gdx.graphics.getHeight() / 2 - 400)));
			dots.add(new Dot(new Vector2(Gdx.graphics.getWidth() / 2 + 400, Gdx.graphics.getHeight() / 2 - 400)));
			dots.add(new Dot(new Vector2(Gdx.graphics.getWidth() / 2 - 400, Gdx.graphics.getHeight() / 2 + 400)));
			dots.add(new Dot(new Vector2(Gdx.graphics.getWidth() / 2 + 400, Gdx.graphics.getHeight() / 2 + 400)));
		}

		turn = 0;
	}
	
	public void addPath(Path<Vector2> path) {
		lines.add(path);
	}

    public void addDot(Dot dot) {
        dots.add(dot);
    }

	public List<Path<Vector2>> getAllLines() {
		return lines;
	}

	public List<Path<Vector2>> getAllButCurrentLine() {
		List<Path<Vector2>> result = new ArrayList<Path<Vector2>>(lines);
		result.remove(currentLine);

		return result;
	}
	
	public List<Dot> getDots() {
		return dots;
	}
}
