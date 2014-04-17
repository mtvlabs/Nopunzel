package com.me.mygdxgame;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class Map {
	public int numOfEnemies;
	public int healthOfPlayer;
	public int currency;
	
	public Music music;
	
	public TiledMap map;
	public TiledMapTileLayer path;
	public OrthogonalTiledMapRenderer render;
	
	public Map(int enemies, int health, int money, TiledMap m, Music music){
		numOfEnemies = enemies;
		healthOfPlayer = health;
		currency = money;
		this.music = music;
		
		map = m;
		path = (TiledMapTileLayer) map.getLayers().get(0);
		render = new OrthogonalTiledMapRenderer(map);
	}
	
	public void renderer(OrthographicCamera camera){
		render.setView(camera);
		render.render();
	}
	
	public void dispose(){
		map.dispose();
		render.dispose();
		music.dispose();
	}
}
