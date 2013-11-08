package net.cniangel.dex;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

public class Input implements InputProcessor {

	public boolean[] buttons=new boolean[64];
	public boolean[] oldButtons=new boolean[64];
	
	public static final int UP=0;
	public static final int LEFT=1;
	public static final int RIGHT=2;
	public static final int DOWN=3;
	
	public static final int Z=4;
	public static final int X=5;
	public static final int SELECT=6;
	public static final int START=7;
	
	
	public void set(int key, boolean down) {
		
		int button = -1;
		
		switch (key) {
		case Keys.UP: button=UP; break;
		case Keys.LEFT: button=LEFT; break;
		case Keys.RIGHT: button=RIGHT; break;
		case Keys.DOWN: button = DOWN; break;
		case Keys.Z: button = Z; break;
		case Keys.X: button = X; break;
		case Keys.SHIFT_RIGHT: button = SELECT; break;
		case Keys.ENTER: button = START; break;
		}
		
		if(button >= 0){
			buttons[button]=down;
		}
		
		
	}
	
	public void tick(){
		for(int i=0;i <buttons.length;i++){
			oldButtons[i]=buttons[i];
		}
	}
	
	public void releaseAllKeys()
	{
		for (int i = 0; i < buttons.length; i++) buttons[i] = false;
	}
	
	public boolean keyDown(int keycode) {
		set(keycode,true);
		System.out.println("Key number " + keycode + "pressed.");
		return false;
	}
	
	public boolean keyUp(int keycode) {
		set(keycode,false);
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
