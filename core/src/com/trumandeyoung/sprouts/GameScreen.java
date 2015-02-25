package com.trumandeyoung.sprouts;

import com.badlogic.gdx.Screen;

public class GameScreen implements Screen {

	GameState gameState;
	GameRenderer renderer;
	GameEngine engine;
	
	public GameScreen() {
		gameState = new GameState();
		renderer = new GameRenderer(gameState);
		engine = new GameEngine(gameState);
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		engine.update(delta);
		renderer.render(delta);
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {

	}

}
