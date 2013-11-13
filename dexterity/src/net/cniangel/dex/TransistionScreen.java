package net.cniangel.dex;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TransistionScreen implements Screen {

	DexGame game;
	SpriteBatch batch;
	String msg = "Round-";
	Sprite bg = new Sprite(Art.bg);
	int counter = 0, screenState = 0;
	boolean won = false;
	
	public TransistionScreen(DexGame dexGame) {
		this.game = dexGame;
		this.batch = dexGame.batch;
		
		System.out.println("TRANSITION SCREEN LOADED");
		
		msg += Integer.toString(game.level);
		
		System.out.println(game.level);
		
		if (game.level == 25) {
			won = true;
		}
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0,0,0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		inputCheck();
		
		if (counter > 85) {
			
			if (won == true) {
				game.kill = new KillScreen(game);
				game.setScreen(game.kill);
			} else {
				game.play = new PlayScreen(game);
				game.setScreen(game.play);
			}
		}
		
		Gdx.graphics.requestRendering();
		batch.begin();
		bg.draw(batch);
		Art.drawFont(msg, batch, game.w / 2 - (8 * msg.length()), game.h / 2 - 8);
		batch.end();
		
		counter++;
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}
	
	private void inputCheck() {

		if (Gdx.input.isKeyPressed(Keys.ESCAPE))
			Gdx.app.exit();
		
		if (Gdx.input.isKeyPressed(Keys.F1)) {
			switch (screenState) {
			case 0: Gdx.graphics.setDisplayMode(160 * 2, 144 * 2, false); screenState = 1; break;
			case 1: Gdx.graphics.setDisplayMode(160 * 3, 144 * 3, false); screenState = 2; break;
			case 2: Gdx.graphics.setDisplayMode(160 * 4, 144 * 4, false); screenState = 3; break;
			case 3: Gdx.graphics.setDisplayMode(160 * 5, 144 * 5, false); screenState = 4; break;
			case 4: Gdx.graphics.setDisplayMode(160, 144, false); screenState = 0; break;
			}
		}
		
	}

}
