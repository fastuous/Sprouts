package com.trumandeyoung.sprouts;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;

public class GameEngine {

	private GameState state;
	private CatmullRomSpline<Vector2> currentLine;
	private List<Vector2> currentPoints;
	private int turn;
	
	public GameEngine(GameState state) {
		this.state = state;
		currentPoints = new LinkedList<Vector2>();
		turn = 1;
	}

	public void update(float delta) {
		
		if (Gdx.input.justTouched()) {
			Vector2 v = new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
			if (state.getLines().size() < turn) {
				state.addPath(new CatmullRomSpline<Vector2>());
				currentLine = (CatmullRomSpline<Vector2>) state.getLines().get(turn - 1);
			}
			currentPoints.add(v);
			state.addDot(v);
			Vector2 tempArr[] = new Vector2[currentPoints.size()];
			tempArr = currentPoints.toArray(tempArr);
			currentLine.set(tempArr, true);
		}
	}
	
	
}