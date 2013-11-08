package net.cniangel.dex;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Tile {

	Vector2 position = new Vector2();
	Sprite tile;
	boolean lit = false, blocked = false;
	Rectangle box = new Rectangle();
	
	public Tile(int x, int y, int state) {
		tile = new Sprite();
		position.set(x, y);
		
		switch (state) {
		case 0: tile.set(new Sprite(Art.tile[0][0])); break; 
		case 1: tile.set(new Sprite(Art.tile[1][0])); lit = true; break;
		case 2: tile.set(new Sprite(Art.tile[2][0])); blocked = true; break;
		}
		
		tile.setBounds(x, y, 16, 16);
		box.set(x, y, 16, 16);
		
	}
	
	public void render(SpriteBatch batch) {
		tile.setBounds(position.x, position.y, 16, 16);
		tile.draw(batch);
	}
	
	public void setSprite(int state) {
		switch (state) {
		case 0: tile.set(new Sprite(Art.tile[0][0])); break; 
		case 1: tile.set(new Sprite(Art.tile[1][0])); lit = true; break;
		case 2: tile.set(new Sprite(Art.tile[2][0])); blocked = true; break;
		}
	}

}
