package com.trumandeyoung.sprouts;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Path;
import com.badlogic.gdx.math.Vector2;

/**
 * Tentatively the object to be serialized and passed over the network.
 * 
 * @author Truman
 */
public class GameState {

	private List<Path<Vector2>> lines;
	private List<Dot> dots;
	public int initDots = 2;
	public float zoomLevel = 1;
	public int turn;
	public boolean myTurn;
	public Vector2 fingerPos;

	public GameState() {
		lines = new LinkedList<Path<Vector2>>();
		dots = new LinkedList<Dot>();
		dots.add(new Dot(new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2 + 50)));
		dots.add(new Dot(new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2 - 50)));
		turn = 0;
	}
	
	public void addPath(Path<Vector2> path) {
		lines.add(path);
	}

	public void addDot(Vector2 v) {
		dots.add(new Dot(v));
	}
	
	public List<Path<Vector2>> getLines() {
		return lines;
	}
	
	public List<Dot> getDots() {
		return dots;
	}
}
