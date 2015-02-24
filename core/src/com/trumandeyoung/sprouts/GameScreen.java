package com.trumandeyoung.sprouts;

import com.badlogic.gdx.Screen;

public class GameScreen implements Screen {
	
	GameState state;
	GameRenderer renderer;
	GameEngine engine;
	
	public GameScreen() {
		state = new GameState();
		renderer = new GameRenderer(state);
		engine = new GameEngine(state);
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
