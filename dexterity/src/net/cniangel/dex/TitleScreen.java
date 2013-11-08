package net.cniangel.dex;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class TitleScreen implements Screen {

	DexGame game;
	SpriteBatch batch;
	
	Sprite title0, title1, title2, cursor;
	int counter = 0, state = 0, stage = 0, cursorState = 0, screenState = 0;
	Vector2 startPos, continuePos;
	boolean done = false, noContinue = false;
	
	
	public TitleScreen(DexGame dexGame) {
		this.game = dexGame;
		this.batch = dexGame.batch;
		
		startPos = new Vector2();
		continuePos = new Vector2();
		
		startPos.set(game.w / 2 - (8 * 5) - 24, game.h / 2 - 8);
		continuePos.set(game.w / 2 - (6 * 8) - 24, game.h / 2 - 32);
		
		title0 = new Sprite(Art.title[0][0]);
		title1 = new Sprite(Art.title[1][0]);
		title2 = new Sprite(Art.title[2][0]);
		cursor = new Sprite(Art.player[0][0]);
		
		title0.setBounds(0, 0, 160, 144);
		title1.setBounds(0, 0, 160, 144);
		title2.setBounds(0, 0, 160, 144);
		cursor.setBounds(startPos.x, startPos.y, 16, 16);
		
		System.out.println("TITLE SCREEN LOADED");
		
		if (game.level == 0) {
			noContinue = true;
		}
		
		Sfx.load(0);
		Sfx.play(0, true);
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		inputCheck();
		
		// Update Screens
		if (done != true) {
			if (counter > 45) {
				counter = 0;
				stage++;
				switch (state) {
				case 0: title0.setX(title0.getX() - 32); break;
				case 1: title1.setX(title1.getX() + 32); break;
				}
				
				if (stage == 5 && state == 1) {
					done = true;
				}
				
				if (stage == 5) {
					state++;
					stage = 0;
				}
				
				
			}
		}
		
		Gdx.graphics.requestRendering();
		batch.begin();
		title2.draw(batch);
		if (done == true) {
			Art.drawFont("START", batch, game.w / 2 - (8 * 5), game.h / 2 - 8);
			if (noContinue != true) {
				Art.drawFont("CONTINUE", batch, game.w / 2 - (6 * 8), game.h / 2 - 32);
			}
			cursor.draw(batch);
		} else {
			title1.draw(batch);
			title0.draw(batch);
		}
		batch.end();
		counter++;
	}

	private void inputCheck() {

		if (Gdx.input.isKeyPressed(Keys.Z))
			done = true;
		
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
		
		if (noContinue != true) {
			if (Gdx.input.isKeyPressed(Keys.DOWN) && cursorState == 0) {
				cursor.setBounds(continuePos.x, continuePos.y, 16, 16);
				cursorState = 1;
			}
			
			if (Gdx.input.isKeyPressed(Keys.UP) && cursorState == 1) {
				cursor.setBounds(startPos.x, startPos.y, 16, 16);
				cursorState = 0;
			}
		}
		
		if (done == true) {
			if (Gdx.input.isKeyPressed(Keys.X)) {
				if (cursorState == 1) {
					Sfx.end(0);
					game.transit = new TransistionScreen(game);
					game.setScreen(game.transit);
				} else {
					Sfx.end(0);
					game.prefs.putInteger("level", 0);
					game.level = 0;
					game.prefs.flush();
					game.transit = new TransistionScreen(game);
					game.setScreen(game.transit);
				}
				
			}
		}
		
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

}
