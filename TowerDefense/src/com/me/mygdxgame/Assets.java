package com.me.mygdxgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class Assets {
	private static int height = 64, width = 64; 
	
	public static BitmapFont font;
	
	public static Texture currency;
	public static Sprite currencyIcon;
	
	public static Texture health;
	public static Sprite healthIcon;
	
	public static Texture upgrade;
	public static Sprite upgradeButton;
	
	public static Texture upgradePressed;
	public static Sprite upgradePressedButton;
	
	public static Texture title;
	public static Sprite titleScreen;
	
	public static Texture menu;
	public static Sprite menuScreen;
	
	public static Texture menuStart;
	public static Sprite menuStartScreen;
	
	public static Texture menuQuit;
	public static Sprite menuQuitScreen;
	
	public static Texture difficulty;
	public static Sprite difficultyScreen;
	
	public static Texture difficultyEasy;
	public static Sprite difficultyEasyScreen;
	
	public static Texture difficultyMedium;
	public static Sprite difficultyMediumScreen;
	
	public static Texture difficultyHard;
	public static Sprite difficultyHardScreen;
	
	public static Texture win;
	public static Sprite winScreen;
	
	public static Texture winContinue;
	public static Sprite winContinueScreen;
	
	public static Texture winExit;
	public static Sprite winExitScreen;
	
	public static Texture lose;
	public static Sprite loseScreen;
	
	public static Texture loseMenu;
	public static Sprite loseMenuScreen;
	
	public static Texture loseReset;
	public static Sprite loseResetScreen;
	
	public static Texture loseQuit;
	public static Sprite loseQuitScreen;
	
	public static Texture quit;
	public static Sprite quitScreen;
	
	public static Texture quitMenu;
	public static Sprite quitMenuScreen;
	
	public static Texture quitQuit;
	public static Sprite quitQuitScreen;
	
	public static Texture tower;
	public static Sprite towerSprite;
	
	public static Texture enemy;
	public static Sprite enemySprite [] = new Sprite[12];
	
	public static Music menuBackground;
	public static Music background_1;
	public static Sound buildTower;
	public static Sound attackSound;
	public static Sound deathSound;
	public static Sound winResult;
	public static Sound loseResult;
	
	public static TiledMap maps[] = new TiledMap[12];
	
	public static void load(){
		//This loads font
		font = new BitmapFont();
		font.setScale(3);

		
		currency = new Texture(Gdx.files.internal("Icons/Coin.png"));
		currencyIcon = new Sprite(currency);
		
		health = new Texture(Gdx.files.internal("Icons/Heart.png"));
		healthIcon = new Sprite(health);
		
		upgrade = new Texture(Gdx.files.internal("Buttons/Upgrade.png"));
		upgradeButton = new Sprite(upgrade);
		
		upgradePressed = new Texture(Gdx.files.internal("Buttons/UpgradePressed.png"));
		upgradePressedButton = new Sprite(upgrade);
		
		title = new Texture(Gdx.files.internal("Menus/Title_Screen.png"));
		titleScreen = new Sprite(title);
		
		menu = new Texture(Gdx.files.internal("Menus/Menu.png"));
		menuScreen = new Sprite(menu);
		
		menuStart = new Texture(Gdx.files.internal("Menus/MenuStartPressed.png"));
		menuStartScreen = new Sprite(menuStart);
		
		menuQuit = new Texture(Gdx.files.internal("Menus/MenuQuitPressed.png"));
		menuQuitScreen = new Sprite(menuQuit);
		
		difficulty = new Texture(Gdx.files.internal("Menus/DifficultyLevel.png"));
		difficultyScreen = new Sprite(difficulty);
		
		difficultyEasy = new Texture(Gdx.files.internal("Menus/DifficultyLevelEasyPressed.png"));
		difficultyEasyScreen = new Sprite(difficultyEasy);
		
		difficultyMedium = new Texture(Gdx.files.internal("Menus/DifficultyLevelMediumPressed.png"));
		difficultyMediumScreen = new Sprite(difficultyMedium);
		
		difficultyHard = new Texture(Gdx.files.internal("Menus/DifficultyLevelHardPressed.png"));
		difficultyHardScreen = new Sprite(difficultyHard);
		
		win = new Texture(Gdx.files.internal("Menus/Level_Completed.png"));
		winScreen = new Sprite(win);
		
		winContinue = new Texture(Gdx.files.internal("Menus/Level_Completed_Continue_Pressed.png"));
		winContinueScreen = new Sprite(winContinue);
		
		winExit = new Texture(Gdx.files.internal("Menus/Level_Completed_Exit_Pressed.png"));
		winExitScreen = new Sprite(winExit);
		
		lose = new Texture(Gdx.files.internal("Menus/GameOver.png"));
		loseScreen = new Sprite(lose);
		
		loseMenu = new Texture(Gdx.files.internal("Menus/GameOverMenuPressed.png"));
		loseMenuScreen = new Sprite(loseMenu);
		
		loseReset = new Texture(Gdx.files.internal("Menus/GameOverRestartPressed.png"));
		loseResetScreen = new Sprite(loseReset);
		
		loseQuit = new Texture(Gdx.files.internal("Menus/GameOverQuitPressed.png"));
		loseQuitScreen = new Sprite(loseQuit);
		
		quit = new Texture(Gdx.files.internal("Menus/GameCompleted.png"));
		quitScreen = new Sprite(quit);
		
		quitMenu = new Texture(Gdx.files.internal("Menus/GameCompletedMenuPressed.png"));
		quitMenuScreen = new Sprite(quit);
		
		quitQuit = new Texture(Gdx.files.internal("Menus/GameCompletedQuitPressed.png"));
		quitQuitScreen = new Sprite(quit);
		
		tower = new Texture(Gdx.files.internal("Sprites/SummerTower.png"));
		towerSprite = new Sprite(tower);
		
		enemy = new Texture(Gdx.files.internal("Sprites/GatorSpriteSheet.png"));
		for( int i = 0; i < enemySprite.length; i++)
			enemySprite[i] = new Sprite(enemy, 0 , i*height, height , width);
		
		
		menuBackground = Gdx.audio.newMusic(Gdx.files.internal("Music/Menu.mp3"));
		menuBackground.setLooping(true);
		
		background_1 = Gdx.audio.newMusic(Gdx.files.internal("Music/Background_1.wav"));
		background_1.setLooping(true);
		
		buildTower = Gdx.audio.newSound(Gdx.files.internal("Music/Construction.wav"));
		attackSound = Gdx.audio.newSound(Gdx.files.internal("Music/Attack.wav"));
		deathSound = Gdx.audio.newSound(Gdx.files.internal("Music/Death.wav"));
		winResult = Gdx.audio.newSound(Gdx.files.internal("Music/Result_Win.wav"));
		loseResult = Gdx.audio.newSound(Gdx.files.internal("Music/Result_Lose.wav"));
		
		TmxMapLoader loader = new TmxMapLoader();
		maps[0] = loader.load("Maps/Easy_Difficulty/Level1.tmx");
		maps[1] = loader.load("Maps/Easy_Difficulty/Level2.tmx");
		maps[2] = loader.load("Maps/Easy_Difficulty/Level3.tmx");
		maps[3] = loader.load("Maps/Easy_Difficulty/Level4.tmx");
		
		maps[4] = loader.load("Maps/Medium_Difficulty/Level1.tmx");
		maps[5] = loader.load("Maps/Medium_Difficulty/Level2.tmx");
		maps[6] = loader.load("Maps/Medium_Difficulty/Level3.tmx");
		maps[7] = loader.load("Maps/Medium_Difficulty/Level4.tmx");
		
		maps[8] = loader.load("Maps/Hard_Difficulty/Level1.tmx");
		maps[9] = loader.load("Maps/Hard_Difficulty/Level2.tmx");
		maps[10] = loader.load("Maps/Hard_Difficulty/Level3.tmx");
		maps[11] = loader.load("Maps/Hard_Difficulty/Level4.tmx");
	}
	
	public static void dispose(){
		title.dispose();
		menu.dispose();
		menuStart.dispose();
		menuQuit.dispose();
		difficulty.dispose();
		difficultyEasy.dispose();
		difficultyMedium.dispose();
		difficultyHard.dispose();
		win.dispose();
		winContinue.dispose();
		winExit.dispose();
		lose.dispose();
		loseMenu.dispose();
		loseReset.dispose();
		loseQuit.dispose();
		quit.dispose();
		quitMenu.dispose();
		quitQuit.dispose();
		
		tower.dispose();
		upgrade.dispose();
		upgradePressed.dispose();
		
		menuBackground.dispose();
		background_1.dispose();
		buildTower.dispose();
		attackSound.dispose();
		deathSound.dispose();
		winResult.dispose();
		loseResult.dispose();
		
		for(int i=0; i<12; i++){
			maps[i].dispose();
		}
	}
}
