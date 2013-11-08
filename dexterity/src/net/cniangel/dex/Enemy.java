package net.cniangel.dex;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Enemy {

	Vector2 position = new Vector2();
	Sprite sprite;
	Random rand = new Random();
	Rectangle bounds;
	int hatchTimer, moveTimer, moveSpeed, enemytype, switchCounter, switchSpeed;
	boolean hatched = false;
	PlayScreen play;
	
	public Enemy(PlayScreen playScreen, int x, int y, int type) {
		this.play = playScreen;
		position.set(x, y);
		
		bounds = new Rectangle();
		sprite = new Sprite(Art.enemy[0][0]);
		sprite.setBounds(x, y, 16, 16);
		bounds.set(x, y, 16, 16);
		
		System.out.println(x+" "+y+" "+bounds.width+" "+bounds.height);
		
		
		enemytype = type;
		
		switch (type) {
		case 0: moveSpeed = 160; switchSpeed = 2; break;
		case 1: moveSpeed = 100; switchSpeed = 4; break;
		case 2: moveSpeed = 40; switchSpeed = 6; break;
		case 3: moveSpeed = 40; switchSpeed = 2; break;
		}
	}
	
	public void update(float delta) {
		
		if (hatchTimer > 100) {
			hatched = true;
			
			switch (enemytype) {
			case 0: sprite.set(new Sprite(Art.enemy[1][0])); break;
			case 1: sprite.set(new Sprite(Art.enemy[2][0])); break;
			case 2: sprite.set(new Sprite(Art.enemy[3][0])); break;
			case 3: sprite.set(new Sprite(Art.enemy[4][0])); break;
			}
			
			sprite.setPosition(position.x, position.y);
		}
		
		if (hatched == true) {
			if (moveTimer > moveSpeed) {
				moveTimer = 0;
				
				int dir = rand.nextInt(4);
				float oldX = position.x, oldY = position.y;
				
				switch (dir) {
				case 0: position.add(0, 16); break;
				case 1: position.sub(16, 0); break;
				case 2: position.add(16, 0); break;
				case 3: position.sub(0, 16); break;
				}
				
				for (Tile tile: play.floor) {
					
					float boxOldX = bounds.getX(), boxOldY = bounds.getY();
					bounds.setPosition(position);
					
					if (bounds.overlaps(tile.box) && tile.blocked == true) {
						bounds.setPosition(boxOldX, boxOldY);
						position.set(oldX, oldY);
					} else {
						//
					}
					
				}
				
				if (position.x < 0 ) {
					position.set(oldX, position.y);
				} else if (position.x > play.game.w - 16) {
					position.set(oldX, position.y);
				}
				
				if (position.y < 0) {
					position.set(position.x, oldY);
				} else if ( position.y > play.game.h - 16) {
					position.set(position.x, oldY);
				}
				
				switchCounter++;
			}
			
			
			
			sprite.setPosition(position.x, position.y);
			bounds.setPosition(position);
			
			moveTimer++;
			
			if (switchCounter > switchSpeed) {
				switchCounter = 0;
				
				for (Tile tile: play.floor) {
					if (bounds.overlaps(tile.box) && tile.lit == true) {
						tile.lit = false;
						tile.setSprite(0);
					}
				}
			}
		}
		
		if (hatched != true) {
			hatchTimer++;
		}
	}
	
	public void render(SpriteBatch batch) {
		
		for (Tile tile: play.floor) {
			if (bounds.overlaps(tile.box)) {
				if (tile.lit == true) {
					switch (enemytype) {
					case 0: sprite.set(new Sprite(Art.enemy[1][1])); break;
					case 1: sprite.set(new Sprite(Art.enemy[2][1])); break;
					case 2: sprite.set(new Sprite(Art.enemy[3][1])); break;
					case 3: sprite.set(new Sprite(Art.enemy[4][1])); break;
					}
					//System.out.println("INVERT SPRITE");
				} else {
				switch (enemytype) {
				case 0: sprite.set(new Sprite(Art.enemy[1][0])); break;
				case 1: sprite.set(new Sprite(Art.enemy[2][0])); break;
				case 2: sprite.set(new Sprite(Art.enemy[3][0])); break;
				case 3: sprite.set(new Sprite(Art.enemy[4][0])); break;
					}
				
				}
				
			} 
		}
		sprite.setPosition(position.x, position.y);
		bounds.setPosition(position);
		sprite.draw(batch);
		
	}

}
