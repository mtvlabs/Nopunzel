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
	
	public static Texture title;
	public static Sprite titleScreen;
	
	public static Texture menu;
	public static Sprite menuScreen;
	
	public static Texture difficulty;
	public static Sprite difficultyScreen;
	
	public static Texture win;
	public static Sprite winScreen;
	
	public static Texture winContinuePressed;
	public static Sprite winContinuePressedScreen;
	
	public static Texture winExitPressed;
	public static Sprite winExitPressedScreen;
	
	public static Texture lose;
	public static Sprite loseScreen;
	
	public static Texture quit;
	public static Sprite quitScreen;
	
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
	
	public static TiledMap maps[] = new TiledMap[2];
	
	public static void load(){
		//This loads font
		font = new BitmapFont();
		font.setScale(3);
		
		currency = new Texture(Gdx.files.internal("Icons/Currency.png"));
		currencyIcon = new Sprite(currency);
		
		health = new Texture(Gdx.files.internal("Icons/Health.png"));
		healthIcon = new Sprite(health);
		
		upgrade = new Texture(Gdx.files.internal("Buttons/Upgrade.png"));
		upgradeButton = new Sprite(upgrade);
		
		title = new Texture(Gdx.files.internal("Menus/Title_Screen.png"));
		titleScreen = new Sprite(title);
		
		menu = new Texture(Gdx.files.internal("Menus/Menu_Screen.png"));
		menuScreen = new Sprite(menu);
		
		win = new Texture(Gdx.files.internal("Menus/Level_Completed.png"));
		winScreen = new Sprite(win);
		
		winContinuePressed = new Texture(Gdx.files.internal("Menus/Level_Completed_Continue_Pressed.png"));
		winContinuePressedScreen = new Sprite(winContinuePressed);
		
		winExitPressed = new Texture(Gdx.files.internal("Menus/Level_Completed_Exit_Pressed.png"));
		winExitPressedScreen = new Sprite(winExitPressed);
		
		difficulty = new Texture(Gdx.files.internal("Menus/Difficulty_Screen.png"));
		difficultyScreen = new Sprite(difficulty);
		
		lose = new Texture(Gdx.files.internal("Menus/Lose_Screen.png"));
		loseScreen = new Sprite(lose);
		
		quit = new Texture(Gdx.files.internal("Menus/Quit_Screen.png"));
		quitScreen = new Sprite(quit);
		
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
		maps[0] = loader.load("Maps/Map1.tmx");
		maps[1] = loader.load("Maps/Map2.tmx");
	}
	
	public static void dispose(){
		title.dispose();
		menu.dispose();
		win.dispose();
		lose.dispose();
		quit.dispose();
		
		menuBackground.dispose();
		background_1.dispose();
		buildTower.dispose();
		attackSound.dispose();
		deathSound.dispose();
		winResult.dispose();
		loseResult.dispose();
	}
}
