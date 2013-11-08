package net.cniangel.dex;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DexGame extends Game {

	public SpriteBatch batch;
	public Input input;
	float w, h;
	public Preferences prefs;
	public int level = 0;
	
	// Screens
	TitleScreen title;
	TransistionScreen transit;
	PlayScreen play;
	KillScreen kill;
	
	@Override
	public void create() {
		// Disable the power of 2 rule
		Texture.setEnforcePotImages(false);

		// Initialize, load, and set shit
		batch = new SpriteBatch();
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();
		batch = new SpriteBatch();
		Art.loadAll();
		input = new Input();
		Gdx.input.setInputProcessor(input);
		
		// Set up Preferences so peple can come back and play from where they left off
		prefs = Gdx.app.getPreferences("settings.prefs");
		level = prefs.getInteger("level", 0);
		prefs.flush();
		
		// Initialize the TitleScreen and set it as the current screen
		if (title == null) title = new TitleScreen(this);
		setScreen(title);
		
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}
}
