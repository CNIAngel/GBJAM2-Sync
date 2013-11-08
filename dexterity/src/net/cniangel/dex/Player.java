package net.cniangel.dex;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player {

	Vector2 position, nextPosition;
	Sprite sprite;
	Rectangle bounds;
	boolean isDead = false;
	PlayScreen play;
	
	// Counters for player actions aka moderate that shit so it isn't going like crazy
	int switchCounter = 0, moveCounter = 0;
	
	public Player(PlayScreen playScreen, int x, int y) {
		this.play = playScreen;
		
		sprite = new Sprite(Art.player[0][0]);
		bounds = new Rectangle();
		position = new Vector2();
		nextPosition = new Vector2();
		
		position.set(x, y);
		nextPosition.set(x, y);
		sprite.setBounds(x, y, 16, 16);
		bounds.set(x, y, 16, 16);
	}
	
	public void update(float delta) {
		/**
		 * Movement booleans are used so if the player holds down a button it isn't read like a thousand times.
		 * They are currently not in use.
		 */
		boolean leftPressed = play.game.input.buttons[Input.LEFT], oldLeftPressed = play.game.input.oldButtons[Input.LEFT];
		boolean rightPressed = play.game.input.buttons[Input.RIGHT], oldRightPressed = play.game.input.oldButtons[Input.RIGHT];
		boolean upPressed = play.game.input.buttons[Input.UP], oldUpPressed = play.game.input.oldButtons[Input.UP];
		boolean downPressed = play.game.input.buttons[Input.DOWN], oldDownPressed = play.game.input.oldButtons[Input.DOWN];
		boolean xPressed = play.game.input.buttons[Input.X], oldXPressed = play.game.input.oldButtons[Input.LEFT];
		
		// Collision nonsense
		float oldX = nextPosition.x, oldY = nextPosition.y;
		boolean collision = false;
		
		// If the player isn't dead then do these things
		if (isDead != true) {
			// Check for player movement
			// If the player presses the corresponding arrow key and the move counter is over 12
			// then add (or subtract) 16 to it's x or y value.
			// Then reset the move counter to 0.
			if (Gdx.input.isKeyPressed(Keys.LEFT) && moveCounter > 12) {
				nextPosition.sub(16, 0);
				moveCounter = 0;
			}
			if (Gdx.input.isKeyPressed(Keys.RIGHT) && moveCounter > 12) {
				nextPosition.add(16, 0);
				moveCounter = 0;
			}
			if (Gdx.input.isKeyPressed(Keys.UP) && moveCounter > 12) {
				nextPosition.add(0, 16);
				moveCounter = 0;
			}
			if (Gdx.input.isKeyPressed(Keys.DOWN) && moveCounter > 12) {
				nextPosition.sub(0, 16);
				moveCounter = 0;
			}
			
			// Check if the player wants to switch the tile
			if (Gdx.input.isKeyPressed(Keys.X) && switchCounter > 12) {
				// Go through all the tiles
				for (Tile tile: play.floor) {
					// If the tile is lit, then un-light it and visa versa
					if (bounds.overlaps(tile.box) && tile.lit == false) {
						tile.lit = true;
						tile.setSprite(1);
					} else if (bounds.overlaps(tile.box) && tile.lit == true) {
						tile.lit = false;
						tile.setSprite(0);
					}
				}
				// Reset the counter to 0
				switchCounter = 0;
			}
			// Add one to the counters
			switchCounter++;
			moveCounter++;
		}
		
		// Collision Detection
		// For every tile
		for (Tile tile: play.floor) {
			
			// Keep track of the out rectangle position then set it to it's next position
			float boxOldX = bounds.getX(), boxOldY = bounds.getY();
			bounds.setPosition(nextPosition);
			// If the rectangle is overlaping a tile and the tile is blocked
			if (bounds.overlaps(tile.box) && tile.blocked == true) {
				// Then we probably shouldn't move there
				collision = true;
			}
			// Bring the rectangle back
			bounds.setPosition(boxOldX, boxOldY);
		}
		
		// Boundaries checking. So the player doesn't leave the screen.
		if (nextPosition.x < 0 ) {
			nextPosition.set(oldX, nextPosition.y);
		}
		if (nextPosition.x > play.game.w - 16) {
			nextPosition.set(oldX, nextPosition.y);
		}
		
		if (nextPosition.y < 0) {
			nextPosition.set(nextPosition.x, oldY);
		}
		
		if ( nextPosition.y > play.game.h - 16) {
			nextPosition.set(nextPosition.x, oldY);
		}
		
		// If no collision, then move the player on. Else, hold them back and hold them tight 
		if (collision != true) {
			position.set(nextPosition);
			//sprite.setX(nextPosition.x);
			//sprite.setY(nextPosition.y);
			bounds.setPosition(nextPosition);
		} else {
			nextPosition.set(oldX, oldY);
		}
		// Check to see if an enemy is in the same space as the player
		// If they are then the player is dead.
		for (Enemy enemy: play.baddies) {
			if (enemy.bounds.overlaps(bounds)) {
				isDead = true;
			}
		}
		
		
	}
	
	public void render(SpriteBatch batch) {
		// Check if the tile under the player is lit or not.
		for (Tile tile: play.floor) {
			if (bounds.overlaps(tile.box)) {
				// If it is, switch the player sprite to the dark one
				// Else, set is as the light one
				if (tile.lit == true) {
					sprite.set(new Sprite(Art.player[0][1]));
				} else {
					sprite.set(new Sprite(Art.player[0][0]));
				}
				
			} 
		}
		
		// Update the position of the sprite and do a render call 
		sprite.setPosition(position.x, position.y);
		sprite.draw(batch);
	}

}
