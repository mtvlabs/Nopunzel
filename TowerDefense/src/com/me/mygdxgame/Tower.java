package com.me.mygdxgame;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;

public class Tower {
	enum TowerStatus{
		Created, Placed, Selected;
	}
	
	public static int towerCost = 10;
	public static int upgradeCost = 10;
	
	public Sprite tower;
	public int ID;
	public int xCoor, yCoor;
	public int xTile, yTile;
	
	public TowerStatus status;
	
	public int fireTime;
	public int fireRate;
	public int damage = 10;
	public int shotMob = -1;
	public boolean shooting = false;
	
	public Circle range;
	
	//Set basic things of a tower
	public Tower(int ID, int x, int y){
		tower = Assets.towerSprite;
		this.ID = ID;
		xCoor = x;
		yCoor = y;
		xTile = -1;
		yTile = -1;

		damage = 10;
		shotMob = -1;
		shooting = false;
		
		status = TowerStatus.Created;
		fireTime = 50;
		fireRate = 150;
		range = new Circle(xCoor+32, yCoor+32, 160);
	}
	
	public void setRange(){
		range = new Circle(xCoor+32, yCoor+32, 160);
	}
	
	public void upgrade(){
		fireRate -= 25;
		damage += 10;
		System.out.println("Tower is upgraded");
	}
	
	public void shooting(GameScreen screen){
		//This part cycles through the towers and if the tower's fireTime is greater than or equal to fireRate
		//the tower will fire.
		if(TowerStatus.Placed == status || TowerStatus.Selected == status){
			fireTime++;
			if(fireTime >= fireRate)
				for(int i=0; i<screen.numOfEnemies; i++){
					if(shotMob == i){
						if(range.overlaps(screen.enemies[i].bound) && screen.enemies[i].inGame){
							screen.enemies[i].looseHealth(damage);
							fireTime = 0;
							shooting = true;
							break;
						}
			
						//If the original enemy is no longer in range, if will check to see if another enemy is in range
						//else, the tower will stop shooting
						if(!range.overlaps(screen.enemies[i].bound) || !screen.enemies[i].inGame){
							for(int j=0; j<screen.numOfEnemies; j++){
								if(range.overlaps(screen.enemies[j].bound) && screen.enemies[j].inGame){
									shotMob = j;
									screen.enemies[j].looseHealth(damage);
									fireTime = 0;
									shooting = true;
									break;
								}
								else{
									shotMob = -1;
									shooting = false;
								}
							}
							break;
						}
					}
			
					//If the tower is not shooting anything, check to see if an enemy is in range.
					if(shotMob == -1){
						if(range.overlaps(screen.enemies[i].bound) && screen.enemies[i].inGame){
							shotMob = i;
							screen.enemies[i].looseHealth(damage);
							fireTime = 0;
							shooting = true;
							break;
						}
					}
				}
			else{
				shooting = false;
			}
		}
	}
}
