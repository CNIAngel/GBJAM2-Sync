package net.cniangel.dex;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class KillScreen implements Screen {

	DexGame game;
	SpriteBatch batch;
	float x, y;
	Sprite bg = new Sprite(Art.bg);
	
	public KillScreen(DexGame dexGame) {
		this.game = dexGame;
		this.batch = dexGame.batch;
		
		x = game.w / 2 - (8 * 7);
		y = game.h / 2 - 8;
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		batch.begin();
		bg.draw(batch);
		Art.drawFont("You win", batch, x, y);
		Art.drawFont("Thanks", batch, x, y - 16);
		Art.drawFont("for", batch, x, y - 32);
		Art.drawFont("playing", batch, x, y - 48);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		

	}

	@Override
	public void show() {
		

	}

	@Override
	public void hide() {
		

	}

	@Override
	public void pause() {
		

	}

	@Override
	public void resume() {
		

	}

	@Override
	public void dispose() {
		

	}

}
