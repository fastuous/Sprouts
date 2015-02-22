package com.trumandeyoung.sprouts;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.Vector2;

public class Sprouts extends ApplicationAdapter {
	SpriteBatch batch;
	ShapeRenderer renderer;
	BitmapFont font;
	CharSequence str;
	Texture img;
	boolean touched;
	
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
		
		touched = false;
	}

	@Override
	public void render () {
		Vector2 p1;
		Vector2 p2;
		boolean path = false;
		
		if (!touched && Gdx.input.justTouched()) {
			p1 = new Vector2(Gdx.input.getX(), Gdx.input.getY());
			touched = true;
		} else if (Gdx.input.justTouched())
		{
			p2 = new Vector2(Gdx.input.getX(), Gdx.input.getY());
			path = true;
		}
		Bezier<Vector2> p = new Bezier<Vector2>(p1, p2);
		if (path) {
			
		}
		
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		int x = Gdx.input.getX();
		int y = Gdx.input.getY();
		if(Gdx.input.justTouched()) Gdx.input.vibrate(100);
		
		renderer.begin();
		
	}
}
