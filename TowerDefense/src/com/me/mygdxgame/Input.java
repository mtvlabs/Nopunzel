package com.me.mygdxgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
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
			if(xTouch >= 499 && xTouch <= 1080){
				if(yTouch >= 499 && yTouch <= 650){
					gameScreen.status = GameStatus.MenuStart;
				}
				else if(yTouch >= 699 && yTouch <= 850){
					gameScreen.status = GameStatus.MenuQuit;
				}
			}
		}
		else if(GameStatus.Select == gameScreen.status){
			if(xTouch >= 499 && xTouch <= 1080){
				if(yTouch >= 299 && yTouch <= 450){
					gameScreen.status = GameStatus.SelectEasy;
				}
				else if(yTouch >= 499 && yTouch <= 650){
					gameScreen.status = GameStatus.SelectMedium;
				}
				else if(yTouch >= 699 && yTouch <= 850){
					gameScreen.status = GameStatus.SelectHard;
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
			
			if(xTouch >= (gameScreen.upgradeButton.xCoor) && xTouch <= (gameScreen.upgradeButton.xCoor+gameScreen.upgradeButton.bound.width)){
				if(yTouch >= (960-gameScreen.upgradeButton.yCoor-gameScreen.upgradeButton.bound.height) && yTouch <= (960-gameScreen.upgradeButton.yCoor)){
					if(select && gameScreen.playerGold >= Tower.upgradeCost){
						gameScreen.upgradeButton.setPressed(true);
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
			if(xTouch >= 499 && xTouch <= 1080){
				if(yTouch >= 499 && yTouch <= 650){
					gameScreen.status = GameStatus.WinContinue;
				}
				else if(yTouch >= 699 && yTouch <= 850){
					gameScreen.status = GameStatus.WinExit;
				}
			}
		}
		else if(GameStatus.Lose == gameScreen.status){
			if(xTouch >= 499 && xTouch <= 1080){
				if(yTouch >= 299 && yTouch <= 450){
					gameScreen.status = GameStatus.LoseReset;
				}
				else if(yTouch >= 499 && yTouch <= 650){
					gameScreen.status = GameStatus.LoseMenu;
				}
				else if(yTouch >= 699 && yTouch <= 850){
					gameScreen.status = GameStatus.LoseQuit;
				}
			}
		}
		else if(GameStatus.Quit == gameScreen.status){
			if(xTouch >= 499 && xTouch <= 1080){
				if(yTouch >= 499 && yTouch <= 650){
					gameScreen.status = GameStatus.QuitMenu;
				}
				else if(yTouch >= 699 && yTouch <= 850){
					gameScreen.status = GameStatus.QuitQuit;
				}
			}
		}
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		int xTouch = (int) (gameScreen.widthRatio*Gdx.input.getX());
		int yTouch = (int) (gameScreen.heightRatio*Gdx.input.getY());
		
		if(xTouch < 1580 && xTouch >= 0){
			if(yTouch < 960 && yTouch >= 0){
				if(GameStatus.MenuStart == gameScreen.status){
					gameScreen.currentMap = 0;
					gameScreen.maps = null;
					gameScreen.status = GameStatus.Select;
				}
				else if(GameStatus.MenuQuit == gameScreen.status){
					Gdx.app.exit();
				}
				else if(GameStatus.SelectEasy == gameScreen.status){
					if(Assets.menuBackground.isPlaying()){
						Assets.menuBackground.stop();
					}
					gameScreen.maps = gameScreen.easyMaps;
					setGame();
					gameScreen.status = GameStatus.Game;
				}
				else if(GameStatus.SelectMedium == gameScreen.status){
					if(Assets.menuBackground.isPlaying()){
						Assets.menuBackground.stop();
					}
					gameScreen.maps = gameScreen.mediumMaps;
					setGame();
					gameScreen.status = GameStatus.Game;
				}
				else if(GameStatus.SelectHard == gameScreen.status){
					if(Assets.menuBackground.isPlaying()){
						Assets.menuBackground.stop();
					}
					gameScreen.maps = gameScreen.hardMaps;
					setGame();
					gameScreen.status = GameStatus.Game;
				}
				else if(GameStatus.Game == gameScreen.status){
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
					if(gameScreen.upgradeButton.isPressed()){
						gameScreen.playerGold -= Tower.upgradeCost;
						selectedTower.upgrade();
						gameScreen.upgradeButton.setPressed(false);
					}
				}
				else if(GameStatus.WinContinue == gameScreen.status){
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
				else if(GameStatus.WinExit == gameScreen.status){
					Gdx.app.exit();
				}
				else if(GameStatus.LoseReset == gameScreen.status){
					Assets.loseResult.stop();
					setGame();
					gameScreen.status = GameStatus.Game;
				}
				else if(GameStatus.LoseMenu == gameScreen.status){
					Assets.loseResult.stop();
					gameScreen.status = GameStatus.Menu;
				}
				else if(GameStatus.LoseQuit == gameScreen.status){
					Gdx.app.exit();
				}
				else if(GameStatus.QuitMenu == gameScreen.status){
					gameScreen.status = GameStatus.Menu;
				}
				else if(GameStatus.QuitQuit == gameScreen.status){
					Gdx.app.exit();
				}
			}
			else{
				if(holding){
					holding = false;
					gameScreen.towers.remove(currentTower);
					tower = null;
				}
			}
		}
		else{
			if(holding){
				holding = false;
				gameScreen.towers.remove(currentTower);
				tower = null;
			}
		}
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if(GameStatus.Game == gameScreen.status){
			int xTouch = (int) (gameScreen.widthRatio*Gdx.input.getX());
			int yTouch = (int) (gameScreen.heightRatio*Gdx.input.getY());
		
			if(holding){
				tower.xCoor = (int)(xTouch - 32);
				tower.yCoor = (int)(960 - 32 - yTouch);
				tower.setRange();
			}
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
