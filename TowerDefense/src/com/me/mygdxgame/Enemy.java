package com.me.mygdxgame;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;

public class Enemy {
	public GameScreen screen;
	
	public int xCoor, yCoor;
	public int enemyHealth;
	public int enemyWalk;
	public boolean inGame;
	public int delay = 10, delayCounter = 0;
	
	public Direction direction = Direction.RIGHT;
	public Direction previous = Direction.RIGHT;
	
	public Sprite image;
	public Circle bound;
	
	public static int enemyWorth = 10;
	public static int spawnRate = 125;
	
	public Enemy(GameScreen screen){
		this.screen = screen;
		image = Assets.enemySprite[0];
		enemyHealth = 50;
		enemyWalk = 0;
		inGame = false;
	}
	
	
	public void looseHealth(int damage){
		enemyHealth -= damage;
		if(enemyHealth <= 0){
			screen.playerGold += enemyWorth;
			inGame = false;
		}
	}
	
	//This will spawn an enemy on the map where ground == true
	public void spawnEnemy(){
		int initTile = 0;
	
		for(int i=0; i<screen.maps[screen.currentMap].path.getHeight(); i++){
			if(screen.maps[screen.currentMap].path.getCell(0, i).getTile().getProperties().containsKey("ground") == true){
				initTile = i;
			}
		}
		
		xCoor = 2;
		yCoor = (initTile)*64 + 2;
		bound = new Circle(xCoor+30, yCoor+30, 30);
		inGame = true;
	}
	
	//This part deals with movement.
	public void movement(){
		
		delayCounter++;
		
		if(direction == Direction.RIGHT){
			xCoor += 1;
			bound.x = xCoor+30;
			
			if ( image == Assets.enemySprite[0] && delayCounter >= delay ){
				image = Assets.enemySprite[1];
				delayCounter = 0;
			}
			else if ( image == Assets.enemySprite[1] && delayCounter >= delay  ){
				image = Assets.enemySprite[2];
				delayCounter = 0;
			}
			else if ( image == Assets.enemySprite[2] && delayCounter >= delay  ){
				image = Assets.enemySprite[0];
				delayCounter = 0;
			}
		}
		if(direction == Direction.LEFT){
			xCoor -= 1;
			bound.x = xCoor+30;
			
			if ( image == Assets.enemySprite[3] && delayCounter >= delay ){
				image = Assets.enemySprite[4];
				delayCounter = 0;
			}
			else if ( image == Assets.enemySprite[4] && delayCounter >= delay  ){
				image = Assets.enemySprite[5];
				delayCounter = 0;
			}
			else if ( image == Assets.enemySprite[5] && delayCounter >= delay  ){
				image = Assets.enemySprite[3];
				delayCounter = 0;
			}
		}
		else if(direction == Direction.UPWARD){
			yCoor += 1;
			bound.y = yCoor+30;
			
			if ( image == Assets.enemySprite[10] && delayCounter >= delay ){
				image = Assets.enemySprite[11];
				delayCounter = 0;
			}
			else if ( image == Assets.enemySprite[11] && delayCounter >= delay  ){
				image = Assets.enemySprite[10];
				delayCounter = 0;
			}
		}
		else if(direction == Direction.DOWNWARD){
			yCoor -= 1;
			bound.y = yCoor+30;
			

			if ( image == Assets.enemySprite[6] && delayCounter >= delay ){
				image = Assets.enemySprite[7];
				delayCounter = 0;
			}
			else if ( image == Assets.enemySprite[7] && delayCounter >= delay  ){
				image = Assets.enemySprite[8];
				delayCounter = 0;
			}
			else if ( image == Assets.enemySprite[8] && delayCounter >= delay  ){
				image = Assets.enemySprite[6];
				delayCounter = 0;
			}
		}
			
		enemyWalk += 1;
		
		//This parts checks to see if the enemy has to turn or not.
		if(enemyWalk == screen.maps[screen.currentMap].path.getTileWidth()){
			int x = xCoor;
			int y = yCoor;
			
			if(direction == Direction.RIGHT){
				x = xCoor + 1;
				previous = direction;
			}
			if(direction == Direction.LEFT){
				x = xCoor - 1;
				previous = direction;
			}
			if(direction == Direction.UPWARD){
				y = yCoor + 1;
				previous = direction;
			}
			if(direction == Direction.DOWNWARD){
				y = yCoor - 1;
				previous = direction;
			}
			
			int tileBlockX = (int) (x/screen.maps[screen.currentMap].path.getTileWidth());
			int tileBlockY = (int) (y/screen.maps[screen.currentMap].path.getTileHeight());
			
			//As long as the enemy has not gone the opposite direction, the enemy will continue to move in the direction is was going originally.
			if(tileBlockX < screen.maps[screen.currentMap].path.getWidth()-1 && tileBlockX > 0){
				if(screen.maps[screen.currentMap].path.getCell(tileBlockX, tileBlockY + 1).getTile().getProperties().containsKey("ground") == true && previous != Direction.DOWNWARD){
					direction = Direction.UPWARD;
					image = Assets.enemySprite[10];
				}
				if(screen.maps[screen.currentMap].path.getCell(tileBlockX, tileBlockY - 1).getTile().getProperties().containsKey("ground") == true && previous != Direction.UPWARD){
					direction = Direction.DOWNWARD;
					image = Assets.enemySprite[6];
				}
				if(screen.maps[screen.currentMap].path.getCell(tileBlockX + 1, tileBlockY).getTile().getProperties().containsKey("ground") == true && previous != Direction.LEFT){
					direction = Direction.RIGHT;
					image = Assets.enemySprite[0];
				}
				if(screen.maps[screen.currentMap].path.getCell(tileBlockX - 1, tileBlockY).getTile().getProperties().containsKey("ground") == true && previous != Direction.RIGHT){
					direction = Direction.LEFT;
					image = Assets.enemySprite[3];
				}
			}
			
			//If the enemy makes it to the end, it is no longer in game.
			if(screen.maps[screen.currentMap].path.getCell(tileBlockX, tileBlockY).getTile().getProperties().containsKey("finish") == true){
				inGame = false;
				screen.playerHealth--;
			}
			
			enemyWalk = 0;
		}
	}
}
