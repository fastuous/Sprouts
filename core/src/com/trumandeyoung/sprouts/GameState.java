package com.trumandeyoung.sprouts;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.math.Path;
import com.badlogic.gdx.math.Vector2;

/**
 * Tentatively the object to be serialized and passed over the network.
 * 
 * @author Truman
 */
public class GameState {

	private List<Path<Vector2>> lines;
	private List<Vector2> dots;

	public GameState() {
		lines = new LinkedList<Path<Vector2>>();
		dots = new LinkedList<Vector2>();
	}
	
	public void addPath(Path<Vector2> path) {
		lines.add(path);
	}

	public void addDot(Vector2 dot) {
		dots.add(dot);
	}
	
	public List<Path<Vector2>> getLines() {
		return lines;
	}
	
	public List<Vector2> getDots() {
		return dots;
	}
}
