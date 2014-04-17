package com.me.mygdxgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.me.mygdxgame.GameScreen.Difficulty;
import com.me.mygdxgame.GameScreen.GameStatus;
import com.me.mygdxgame.Tower.TowerStatus;

public class Input implements InputProcessor {

	public int currentTower = 0;
	public boolean holding;
	public boolean select;
	public Tower tower;
	public Tower selectedTower;
	
	public GameScreen gameScreen;
	
	public Input(GameScreen gameScreen){
		super();
		holding = false;
		select = false;
		this.gameScreen = gameScreen;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		int xTouch = (int) (gameScreen.widthRatio*Gdx.input.getX());
		int yTouch = (int) (gameScreen.heightRatio*Gdx.input.getY());
		
		if(GameStatus.Title == gameScreen.status){
			if(Gdx.input.isTouched()){
				gameScreen.status = GameStatus.Menu;
			}
		}
		else if(GameStatus.Menu == gameScreen.status){
			if(!Assets.menuBackground.isPlaying()){
				Assets.menuBackground.play();
			}
			if(xTouch >= 487 && xTouch <= 1145){
				if(yTouch >= 270 && yTouch <= 400){
					gameScreen.currentMap = 0;
					gameScreen.difficulty = Difficulty.None;
					setGame();
					gameScreen.status = GameStatus.Select;
				}
				else if(yTouch >= 534 && yTouch <= 651){
					Gdx.app.exit();
				}
			}
		}
		else if(GameStatus.Select == gameScreen.status){
			if(xTouch >= 433 && xTouch <= 1165){
				if(yTouch >= 142 && yTouch <= 284){
					if(Assets.menuBackground.isPlaying()){
						Assets.menuBackground.stop();
					}
					gameScreen.difficulty = Difficulty.Easy;
					gameScreen.status = GameStatus.Game;
				}
				else if(yTouch >= 384 && yTouch <= 529){
					if(Assets.menuBackground.isPlaying()){
						Assets.menuBackground.stop();
					}
					gameScreen.difficulty = Difficulty.Medium;
					gameScreen.status = GameStatus.Game;
				}
				else if(yTouch >= 602 && yTouch <= 739){
					if(Assets.menuBackground.isPlaying()){
						Assets.menuBackground.stop();
					}
					gameScreen.difficulty = Difficulty.Hard;
					gameScreen.status = GameStatus.Game;
				}
			}
		}
		else if(GameStatus.Game == gameScreen.status){			
			if(xTouch >= (gameScreen.towerButton.xCoor) && xTouch <= (gameScreen.towerButton.xCoor+gameScreen.towerButton.bound.width)){
				if(yTouch >= (960-gameScreen.towerButton.yCoor-gameScreen.towerButton.bound.height) && yTouch <= (960-gameScreen.towerButton.yCoor)){
					if(!holding && gameScreen.playerGold >= Tower.towerCost){						
						holding = true;
						tower = new Tower(currentTower, (int) (xTouch-32), (int) (960-32-yTouch));
						gameScreen.towers.add(tower);
					}
				}
			}
			else if(xTouch >= (gameScreen.upgradeButton.xCoor) && xTouch <= (gameScreen.upgradeButton.xCoor+gameScreen.upgradeButton.bound.width)){
				if(yTouch >= (960-gameScreen.upgradeButton.yCoor-gameScreen.upgradeButton.bound.height) && yTouch <= (960-gameScreen.upgradeButton.yCoor)){
					if(select && gameScreen.playerGold >= Tower.upgradeCost){
						gameScreen.playerGold -= Tower.upgradeCost;
						selectedTower.upgrade();
						return true;
					}
				}
			}
			
			if(!select){
				int xCell = (int)(xTouch/64);
				int yCell = (int)((960-yTouch)/64);
				
				for(Tower t: gameScreen.towers){
					if(xCell == t.xTile && yCell == t.yTile){
						select = true;
						selectedTower = t;
						selectedTower.status = TowerStatus.Selected;
						return true;
					}
				}
			}
			else{
				select = false;
				selectedTower.status = TowerStatus.Placed;
				selectedTower = null;
			}
		}
		else if(GameStatus.Win == gameScreen.status){
			gameScreen.maps[gameScreen.currentMap].music.stop();
			Assets.winResult.play();
			if(xTouch >= 499 && xTouch <= 1080){
				if(yTouch >= 499 && yTouch <= 650){
					gameScreen.status = GameStatus.WinContPressed;
				}
				else if(yTouch >= 699 && yTouch <= 850){
					gameScreen.status = GameStatus.WinExitPressed;
				}
			}
		}
		else if(GameStatus.Lose == gameScreen.status){
			gameScreen.maps[gameScreen.currentMap].music.stop();
			Assets.loseResult.play();
			if(xTouch >= 492 && xTouch <= 1085){
				if(yTouch >= 315 && yTouch <= 452){
					Assets.loseResult.stop();
					setGame();
					gameScreen.status = GameStatus.Game;
				}
				else if(yTouch >= 512 && yTouch <= 660){
					Assets.loseResult.stop();
					gameScreen.status = GameStatus.Menu;
				}
				else if(yTouch >= 714 && yTouch <= 855){
					Assets.loseResult.stop();
					Gdx.app.exit();
				}
			}
		}
		else if(GameStatus.Quit == gameScreen.status){
			if(xTouch >= 580 && xTouch <= 1079){
				if(yTouch >= 321 && yTouch <= 465){
					gameScreen.status = GameStatus.Menu;
				}
				else if(yTouch >= 509 && yTouch <= 642){
					Gdx.app.exit();
				}
			}
		}
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		int xTouch = (int) (gameScreen.widthRatio*Gdx.input.getX());
		int yTouch = (int) (gameScreen.heightRatio*Gdx.input.getY());
		
		if(GameStatus.Game == gameScreen.status){
			boolean towerIsHere = false;
		
			//Locate the closes cell to the user's last location
			int xCell = (int)(xTouch/64);
			int yCell = (int)((960-yTouch)/64);
			
			//This part checks to see if a tower was already here.
			//If a tower is here, the current tower cannot be placed here.
			if(holding){
				if(xCell < gameScreen.maps[gameScreen.currentMap].path.getWidth()){
					for(Tower t: gameScreen.towers){
						if(t.xTile == xCell && t.yTile == yCell){
							towerIsHere = true;
						}
					}
					//If there is no tower here, make sure that it is not the path and place the tower.
					if(towerIsHere == false && gameScreen.maps[gameScreen.currentMap].path.getCell(xCell, yCell).getTile().getProperties().containsKey("grass") == true){
						holding = false;
						gameScreen.playerGold -= Tower.towerCost;
						tower.status = TowerStatus.Selected;
						tower.xCoor = xCell*64;
						tower.yCoor = yCell*64;
						tower.xTile = xCell;
						tower.yTile = yCell;
						tower.setRange();
						currentTower++;
					
						selectedTower = tower;
						select = true;
					
						Assets.buildTower.play();
						tower = null;
					}	
					else{
						holding = false;
						gameScreen.towers.remove(currentTower);
						tower = null;
					}
				}
				else{
					holding = false;
					gameScreen.towers.remove(currentTower);
				}
			}
		}
		else if(GameStatus.WinContPressed == gameScreen.status){
			Assets.winResult.stop();
			gameScreen.currentMap++;
			if(gameScreen.currentMap != gameScreen.maps.length ){
				setGame();
				gameScreen.status = GameStatus.Game;
			}
				else{
					gameScreen.status = GameStatus.Quit;
			}
		}
		else if(GameStatus.WinExitPressed == gameScreen.status){
			Assets.winResult.stop();
			Gdx.app.exit();
		}
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		int xTouch = (int) (gameScreen.widthRatio*Gdx.input.getX());
		int yTouch = (int) (gameScreen.heightRatio*Gdx.input.getY());
		
		if(holding){
			tower.xCoor = (int)(xTouch - 32);
			tower.yCoor = (int)(960 - 32 - yTouch);
			tower.setRange();
		}
		return true;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {

		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	private void setGame() {
		gameScreen.playerHealth = gameScreen.maps[gameScreen.currentMap].healthOfPlayer;
		gameScreen.playerGold = gameScreen.maps[gameScreen.currentMap].currency;
		
		gameScreen.enemies = new Enemy[gameScreen.maps[gameScreen.currentMap].numOfEnemies];
		for(int i=0; i< gameScreen.enemies.length; i++){
			gameScreen.enemies[i] = new Enemy(gameScreen);
		}
		gameScreen.numOfEnemies = 0;
		gameScreen.time = 0;
		
		gameScreen.towers.clear();
		currentTower = 0;
		
		gameScreen.maps[gameScreen.currentMap].music.play();
	}
}
