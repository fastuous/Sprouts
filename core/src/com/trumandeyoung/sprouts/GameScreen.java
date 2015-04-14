package com.trumandeyoung.sprouts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.input.GestureDetector;

public class GameScreen implements Screen {

	GameState gameState;
	GameRenderer renderer;
	GameEngine engine;
	
	public GameScreen() {
		gameState = new GameState();
		renderer = new GameRenderer(gameState);
		engine = new GameEngine(gameState);
		InputMultiplexer mux = new InputMultiplexer();
		mux.addProcessor(new GestureDetector(renderer));
		mux.addProcessor(engine);
		Gdx.input.setInputProcessor(mux);
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
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
