package com.me.mygdxgame;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.me.mygdxgame.Tower.TowerStatus;

public class GameScreen implements Screen{
	
	enum GameStatus{
		Title, Menu, MenuStart, MenuQuit, Select, SelectEasy, SelectMedium, SelectHard,
		Game, Win, WinContinue, WinExit, Lose, LoseMenu, LoseReset, LoseQuit, Quit, QuitMenu, QuitQuit;
	}
	
	public TowerDefense towerDefense;
	public OrthographicCamera camera;
	public ShapeRenderer shapeRen;
	public SpriteBatch batch;
	
	public Map maps[];
	public int currentMap;
	
	public Map easyMaps[] = new Map[4];
	public Map mediumMaps[] = new Map[4];
	public Map hardMaps[] = new Map[4];

	public ArrayList<Tower> towers;
	
	public Enemy enemies[];
	public int numOfEnemies;
	public int time;
	
	public int playerHealth;
	public int playerGold;

	public int timeDelay;
	
	public String health;
	public String currency;
	
	public GameStatus status;
	
	public boolean paused;
	
	public Button towerButton;
	public Button upgradeButton;
	
	public float screenWidth = Gdx.graphics.getWidth();
	public float screenHeight = Gdx.graphics.getHeight();
	
	public float widthRatio = 1580/screenWidth;
	public float heightRatio = 960/screenHeight;
	
	public GameScreen(TowerDefense towerDefense) {
		this.towerDefense = towerDefense;
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1580, 960);
		
		batch = new SpriteBatch();
		shapeRen = new ShapeRenderer();
		
		easyMaps[0] = new Map(30, 20, 100, Assets.maps[0], Assets.background_1);
		easyMaps[1] = new Map(30, 20, 100, Assets.maps[1], Assets.background_1);
		easyMaps[2] = new Map(30, 20, 100, Assets.maps[2], Assets.background_1);
		easyMaps[3] = new Map(30, 20, 100, Assets.maps[3], Assets.background_1);
		
		mediumMaps[0] = new Map(30, 15, 40, Assets.maps[4], Assets.background_1);
		mediumMaps[1] = new Map(30, 15, 40, Assets.maps[5], Assets.background_1);
		mediumMaps[2] = new Map(30, 15, 40, Assets.maps[6], Assets.background_1);
		mediumMaps[3] = new Map(30, 15, 40, Assets.maps[7], Assets.background_1);
		
		hardMaps[0] = new Map(35, 10, 30, Assets.maps[8], Assets.background_1);
		hardMaps[1] = new Map(35, 10, 30, Assets.maps[9], Assets.background_1);
		hardMaps[2] = new Map(35, 10, 30, Assets.maps[10], Assets.background_1);
		hardMaps[3] = new Map(35, 10, 30, Assets.maps[11], Assets.background_1);
		
		towers = new ArrayList<Tower>();
		
		health = "";
		currency = "";
		
		status = GameStatus.Title;
		
		paused = false;
		
		towerButton = new Button(Assets.towerSprite, 1385, 500);
		upgradeButton = new Button(Assets.upgradeButton, 1350, 350);
		
		Gdx.input.setInputProcessor(new Input(this));
	}
	
	public boolean enemiesOnMap(){
		for(int i=0; i<maps[currentMap].numOfEnemies; i++){
			if(enemies[i].inGame){
				return true;
			}
		}
		return false;
	}
	
	public void update(){
		if(GameStatus.Game == status){
			if(numOfEnemies < maps[currentMap].numOfEnemies && time >= Enemy.spawnRate){
				enemies[numOfEnemies].spawnEnemy();
				numOfEnemies++;
				time = 0;
			}
			time++;
			
			for(int i=0; i<numOfEnemies; i++){
				if(enemies[i].inGame){
					enemies[i].movement();
				}
			}
			
			if(!towers.isEmpty()){
				for(Tower t: towers){
					t.shooting(this);
				}
			}
			
			if(playerHealth == 0 || numOfEnemies == maps[currentMap].numOfEnemies){
				if(playerHealth == 0){
					if(timeDelay == 50){
						maps[currentMap].music.stop();
						Assets.loseResult.play();
						status = GameStatus.Lose;
						timeDelay = 0;
					}
					timeDelay++;
				}
				else{
					if(!enemiesOnMap()){
						if(timeDelay == 50){
							maps[currentMap].music.stop();
							Assets.winResult.play();
							status = GameStatus.Win;
							timeDelay = 0;
						}
						timeDelay++;
					}
				}
			}
		}
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		Gdx.gl10.glLineWidth(2 / camera.zoom);
		
		camera.update();
		
		if(!paused){
			update();
		}		

		batch.setProjectionMatrix(camera.combined);
		if(GameStatus.Title == status){
			batch.begin();
				batch.draw(Assets.titleScreen, 0, 0);
			batch.end();
		}
		else if(GameStatus.Menu == status){
			batch.begin();
				batch.draw(Assets.menuScreen, 0, 0);
			batch.end();
		}
		else if(GameStatus.Select == status){
			batch.begin();
				batch.draw(Assets.difficultyScreen, 0, 0);
			batch.end();
		}
		else if(GameStatus.SelectEasy == status){
			batch.begin();
				batch.draw(Assets.difficultyEasyScreen, 0, 0);
			batch.end();
		}
		else if(GameStatus.SelectMedium == status){
			batch.begin();
				batch.draw(Assets.difficultyMediumScreen, 0, 0);
			batch.end();
		}
		else if(GameStatus.SelectHard == status){
			batch.begin();
				batch.draw(Assets.difficultyHardScreen, 0, 0);
			batch.end();
		}
		else if(GameStatus.Game == status){	
			maps[currentMap].renderer(camera);
			
			batch.begin();
				//This display health of player
				batch.draw(Assets.healthIcon, 1305, 875);
				health = String.valueOf(playerHealth);
		
				Assets.font.draw(batch, health, 1390, 925);
				//End of user Health
		
				//This display currency of player
				batch.draw(Assets.currencyIcon, 1305, 775);
				currency = String.valueOf(playerGold);
		
				Assets.font.draw(batch, currency, 1390 ,825);
				//End of user Currency
		
				//Draw Tower Button
				batch.draw(towerButton.image, towerButton.xCoor, towerButton.yCoor);
				
				if(!upgradeButton.isPressed()){
					batch.draw(upgradeButton.image, upgradeButton.xCoor, upgradeButton.yCoor);
				}
				else{
					batch.draw(Assets.upgradePressed, upgradeButton.xCoor, upgradeButton.yCoor);
				}
			
				for(int i=0; i<enemies.length; i++){
					if(enemies[i].inGame){
						batch.draw(enemies[i].image, enemies[i].xCoor, enemies[i].yCoor);
					}
				}
				
				if(!towers.isEmpty()){
					for(Tower t: towers){
						batch.draw(t.tower, t.xCoor, t.yCoor);
					}
				}
			batch.end();
			
			shapeRen.setProjectionMatrix(camera.combined);
			shapeRen.begin(ShapeType.Line);
				shapeRen.setColor(1, 0, 1, 1);
				if(!towers.isEmpty()){
					for(Tower t: towers){
						if(TowerStatus.Selected == t.status || TowerStatus.Created == t.status){
							shapeRen.circle(t.range.x, t.range.y, t.range.radius);
						}
						if(t.shooting){
							shapeRen.line(t.xCoor + 32, t.yCoor + 32,
									enemies[t.shotMob].xCoor + 30, enemies[t.shotMob].yCoor + 30);
							Assets.attackSound.play();
						}
					}
				}
				
				shapeRen.setColor(1, 0, 0, 1);
				for(int i=0; i<enemies.length; i++){
					if(enemies[i].inGame){
						shapeRen.line(enemies[i].xCoor+5, enemies[i].yCoor+enemies[i].image.getHeight()+5, enemies[i].xCoor+5+enemies[i].enemyHealth, enemies[i].yCoor+enemies[i].image.getHeight()+5);
					}
				}
			shapeRen.end();
		}
		else if(GameStatus.Win == status){
			batch.begin();
				batch.draw(Assets.winScreen, 0, 0);
			batch.end();
		}
		else if(GameStatus.WinContinue == status){
			batch.begin();
				batch.draw(Assets.winContinueScreen, 0, 0);
			batch.end();
		}
		else if(GameStatus.WinExit == status){
			batch.begin();
				batch.draw(Assets.winExitScreen, 0, 0);
			batch.end();
		}
		else if(GameStatus.Lose == status){
			batch.begin();
				batch.draw(Assets.loseScreen, 0, 0);
			batch.end();
		}
		else if(GameStatus.LoseMenu == status){
			batch.begin();
				batch.draw(Assets.loseMenuScreen, 0, 0);
			batch.end();
		}
		else if(GameStatus.LoseReset == status){
			batch.begin();
				batch.draw(Assets.loseResetScreen, 0, 0);
			batch.end();
		}
		else if(GameStatus.LoseQuit == status){
			batch.begin();
				batch.draw(Assets.loseQuitScreen, 0, 0);
			batch.end();
		}
		else if(GameStatus.Quit == status){
			batch.begin();
				batch.draw(Assets.quitScreen, 0, 0);
			batch.end();
		}
		else if(GameStatus.QuitMenu == status){
			batch.begin();
				batch.draw(Assets.quitMenuScreen, 0, 0);
			batch.end();
		}
		else if(GameStatus.QuitQuit == status){
			batch.begin();
				batch.draw(Assets.quitQuitScreen, 0, 0);
			batch.end();
		}
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void show() {
		Assets.menuBackground.play();
	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {
		paused = true;
	}

	@Override
	public void resume() {
		paused = false;
	}

	@Override
	public void dispose() {
		Assets.dispose();
		batch.dispose();
		shapeRen.dispose();

		for(int i=0; i<4; i++){
			easyMaps[i].dispose();
			mediumMaps[i].dispose();
			hardMaps[i].dispose();
		}
	}
}