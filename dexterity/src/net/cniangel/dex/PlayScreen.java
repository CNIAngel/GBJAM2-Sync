package net.cniangel.dex;

import java.io.IOException;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

public class PlayScreen implements Screen {

	DexGame game;
	SpriteBatch batch;
	
	ArrayList<Enemy> baddies = new ArrayList<Enemy>();
	ArrayList<Tile> floor = new ArrayList<Tile>();
	Player player = null;
	boolean levelWon = false, litCheck, gameOver = false, playGOSound;
	String ips, path = "data/art/levels/";
	Sprite gameover;
	
	int screenState = 0, endTimer, moveTimer, tileAmount, leftStart = 0, rightStart = 96, stallTimer, level;
	
	public PlayScreen(DexGame dexGame) {
		this.game = dexGame;
		this.batch = dexGame.batch;
		
		Sfx.load(1);
		Sfx.play(1, true);
		floor.clear();
		baddies.clear();
		level = game.level;
		System.out.println(level);
		
		loadMap();
		
		gameover = new Sprite(Art.gameover);
		gameover.setBounds(0, game.h - (16 * 7 - 8), 160, 80);
	}
	
	private void loadMap() {
		
		Element root = null;
		try {
			root = new XmlReader().parse(Gdx.files.internal(path+"level"+level+".oel"));
			System.out.println("File "+path+"level"+level+".oel"+" loaded");
			
			if (root == null) {
				System.out.println("NULL MOTHERFUCKER");
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("LoadMap Failed!");
			e.printStackTrace();
		}
		
		Element tileLayer = root.getChild(0);
		Element objectLayer = root.getChildByName("objectLayer");
		Element playerData = objectLayer.getChildByName("Player");
		player = new Player(this, playerData.getInt("x"), playerData.getInt("y"));
		
		for (int i = 0; i < tileLayer.getChildCount(); i++) {
			Element tileData = tileLayer.getChild(i);
			if (tileData == null) System.out.println("TILE NULL");
			int id = tileData.getInt("id");
			Tile tile = null;
			switch (id) {
			case 0: tile = new Tile(tileData.getInt("x") * 16, tileData.getInt("y") * 16, 0); tileAmount++; break;
			case 1: tile = new Tile(tileData.getInt("x") * 16, tileData.getInt("y") * 16, 1); tileAmount++; break;
			case 2: tile = new Tile(tileData.getInt("x") * 16, tileData.getInt("y") * 16, 2); break;
			}
			floor.add(tile);
			
		}
		
		for (int i = 0; i < objectLayer.getChildCount(); i++) {
			Element object = objectLayer.getChild(i);
			System.out.println(object.getAttributes());
			System.out.println("Element Number: "+i+"\nElement Name: "+object.getName()+"\nElement X & Y: "+object.getInt("x")+" "+object.getInt("y"));
			int type = object.getInt("type");
			
				switch(type) {
				case 0: addEnemy(object.getInt("x"), object.getInt("y"), type); System.out.println("BUNNY ADDED"); break;
				case 1: addEnemy(object.getInt("x"), object.getInt("y"), type); System.out.println("VIRUS ADDED"); break;
				case 2: addEnemy(object.getInt("x"), object.getInt("y"), type); System.out.println("SYPHER ADDED"); break;
				case 3: addEnemy(object.getInt("x"), object.getInt("y"), type); System.out.println("TRIANGLE ADDED"); break;
			}
		}
		
		
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		int index = 0;
		
		if (levelWon == true) {
			 game.level++;
			game.prefs.putInteger("level",  game.level);
			game.prefs.flush();
			Sfx.end(1);
			game.transit = new TransistionScreen(game);
			game.setScreen(game.transit);
		}
		
		
		
		inputCheck();
		game.input.tick();
		player.update(delta);
		
		batch.begin();
		for (Tile tile: floor) {
			tile.render(batch);
		}
		if (gameOver != true) {
			for (Enemy enemy: baddies) {
				enemy.update(delta);
				enemy.render(batch);
			}
		}
		player.render(batch);
		if (gameOver == true) {
			gameover.draw(batch);
			
			Art.drawFont("GAME", batch, leftStart + endTimer, 5 * 16);
			Art.drawFont("OVER", batch, rightStart - endTimer, 4 * 16);
			
			
			if (endTimer < 50) {
				endTimer++;
			} else {
				//Sfx.end(2);
				stallTimer++;
				
			}
			
			
			if (stallTimer > 40) {
				game.title = new TitleScreen(game);
				game.setScreen(game.title);
			}
		}
		batch.end();
		game.input.releaseAllKeys();
		
		if (player.isDead == true) {
			gameOver = true;
			Sfx.end(1);
		}
		
		for (Tile tile : floor) {
			if (tile.lit == true) {
				litCheck = true; index++;
			} else  {
				litCheck = false;
			}
			
			if (litCheck == true && index == tileAmount) {
				levelWon = true;
			}
		}
		
		
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
	
	private void addEnemy(int x, int y, int type) {
		Enemy enemy = new Enemy(this, x, y, type);
		baddies.add(enemy);
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
