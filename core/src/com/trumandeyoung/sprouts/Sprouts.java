package com.trumandeyoung.sprouts;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Path;
import com.badlogic.gdx.math.Vector2;

public class Sprouts extends ApplicationAdapter {
	int SAMPLE_POINTS = 100;
	float SAMPLE_POINT_DISTANCE = 1f / SAMPLE_POINTS;

	SpriteBatch batch;
	ImmediateModeRenderer renderer;
	BitmapFont font;
	CharSequence str;
	Texture img;
	List<Vector2> points = new LinkedList<Vector2>();
	Path<Vector2> path;
	final Vector2 tmpV = new Vector2();

	@Override
	public void create() {

		renderer = new ImmediateModeRenderer20(false, false, 0);
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
	public void render() {

		if (Gdx.input.justTouched()) {
			points.add(new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY()));
		}

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (points.size() > 0) {
			Vector2 pointArray[] = new Vector2[points.size()];
			pointArray = points.toArray(pointArray);
			path = new CatmullRomSpline<Vector2>(pointArray, true);

			renderer.begin(batch.getProjectionMatrix(), GL20.GL_LINE_STRIP);
			float val = 0f;
			while (val <= 1f) {
				renderer.color(0f, 0f, 0f, 1f);
				path.valueAt(tmpV, val);
				renderer.vertex(tmpV.x, tmpV.y, 0);
				val += SAMPLE_POINT_DISTANCE;
			}
			renderer.end();

		}

	}
}
