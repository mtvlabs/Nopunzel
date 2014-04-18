package com.me.mygdxgame;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Button {
	public Sprite image;
	public Rectangle bound;
	public int xCoor;
	public int yCoor;
	
	private boolean pressed;
	
	//Set the button and its location
	public Button(Sprite image, int x, int y){
		this.image = image;
		bound = image.getBoundingRectangle();
		xCoor = x;
		yCoor = y;
		setPressed(false);
	}

	public boolean isPressed() {
		return pressed;
	}

	public void setPressed(boolean pressed) {
		this.pressed = pressed;
	}
}
