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
		Title, Menu, Select, Game, Win, WinContPressed, WinExitPressed, Lose, Quit;
	}
	
	enum Difficulty{
		None, Easy, Medium, Hard;
	}
	
	public TowerDefense towerDefense;
	public OrthographicCamera camera;
	public ShapeRenderer shapeRen;
	public SpriteBatch batch;
	
	public Map maps[] = new Map[2];
	public int currentMap;

	public ArrayList<Tower> towers;
	
	public Enemy enemies[];
	public int numOfEnemies;
	public int time;
	
	public int timeDelay;
	
	public int playerHealth;
	public int playerGold;

	public String health;
	public String currency;
	
	public GameStatus status;
	public Difficulty difficulty;
	
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
		
		maps[0] = new Map(15, 15, 100, Assets.maps[0], Assets.background_1);
		maps[1] = new Map(25, 25, 100, Assets.maps[1], Assets.background_1);
		
		towers = new ArrayList<Tower>();
		
		timeDelay = 0;
		
		health = "";
		currency = "";
		
		status = GameStatus.Title;
		difficulty = Difficulty.None;
		
		paused = false;
		
		towerButton = new Button(Assets.towerSprite, 1389, 710);
		upgradeButton = new Button(Assets.upgradeButton, 1389, 400);
		
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
					status = GameStatus.Lose;
				}
				else{
					if(!enemiesOnMap()){
						timeDelay++;
						if(timeDelay == 10){
							timeDelay = 0;
							status = GameStatus.Win;
						}
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
		else if(GameStatus.Game == status){	
			maps[currentMap].renderer(camera);
			
			batch.begin();
				//This display health of player
				batch.draw(Assets.healthIcon, 1305, 860);
				health = String.valueOf(playerHealth);
		
				Assets.font.draw(batch, health, 1390, 900);
				//End of user Health
		
				//This display currency of player
				batch.draw(Assets.currencyIcon, 1305, 660);
				currency = String.valueOf(playerGold);
		
				Assets.font.draw(batch,currency, 1390,700);
				//End of user Currency
		
				//Draw Tower Button
				batch.draw(towerButton.image, towerButton.xCoor, towerButton.yCoor);
				batch.draw(upgradeButton.image, upgradeButton.xCoor, upgradeButton.yCoor);
			
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
		else if(GameStatus.WinContPressed == status){
			batch.begin();
				batch.draw(Assets.winContinuePressedScreen, 0, 0);
			batch.end();
		}
		else if(GameStatus.WinExitPressed == status){
			batch.begin();
				batch.draw(Assets.winExitPressedScreen, 0, 0);
			batch.end();
		}
		else if(GameStatus.Lose == status){
			batch.begin();
				batch.draw(Assets.loseScreen, 0, 0);
			batch.end();
		}
		else if(GameStatus.Quit == status){
			batch.begin();
				batch.draw(Assets.quitScreen, 0, 0);
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
		for(int i=0; i<maps.length; i++){
			maps[i].dispose();
		}
	}
}