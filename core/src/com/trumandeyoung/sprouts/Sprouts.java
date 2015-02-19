package com.trumandeyoung.sprouts;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Sprouts extends ApplicationAdapter {
	SpriteBatch batch;
	BitmapFont font;
	CharSequence str;
	Texture img;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		font = new BitmapFont();
		font.setColor(Color.BLACK);
		font.setScale(2f);
		String res = "";
		res += "Width: " + Gdx.graphics.getWidth();
		res += " Height: " + Gdx.graphics.getHeight();
		res += " Density: " + Gdx.graphics.getDensity();
		str = res;
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		int x = Gdx.input.getX();
		int y = Gdx.input.getY();
		if(Gdx.input.justTouched()) Gdx.input.vibrate(100);
		batch.begin();
		font.draw(batch, str, x, Gdx.graphics.getHeight() - y);
		batch.end();
	}
}
