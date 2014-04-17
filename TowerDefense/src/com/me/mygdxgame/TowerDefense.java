package com.me.mygdxgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;

public class TowerDefense extends Game {
	GameScreen gameScreen;
	
	@Override
	public void create() {
		Texture.setEnforcePotImages(false);
		Assets.load();
		
		gameScreen = new GameScreen(this);
		setScreen(gameScreen);
	}
	
	@Override
	public void dispose(){
		gameScreen.dispose();
	}
}
