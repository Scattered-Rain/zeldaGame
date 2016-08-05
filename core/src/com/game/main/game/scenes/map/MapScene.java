package com.game.main.game.scenes.map;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import lombok.Getter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.game.main.Constants;
import com.game.main.SoundEnum;
import com.game.main.SpriteEnum;
import com.game.main.game.events.Event;
import com.game.main.game.scenes.Scene;
import com.game.main.game.scenes.map.entity.Debug;
import com.game.main.game.scenes.map.entity.SpawnEntityEnum;
import com.game.main.game.scenes.map.entity.abstracts.Entity;
import com.game.main.game.scenes.map.entity.common.player.Prota;
import com.game.main.util.Point;

/** Scene representing actual Maps */
public class MapScene extends Scene{
	
	/** The Camera used for Rendering */
	private MapCamera camera;
	/** The Actual Map held */
	private TiledMap map;
	
	/** The Renderer used for the Map */
	private OrthogonalTiledMapRenderer renderer;
	/** The SpriteBatch used for rendering entities */
	private SpriteBatch batch;
	
	/** The width of the map in tiles*/
	@Getter private int width;
	/** The height of the map in tiles */
	@Getter private int height;
	
	/** Utilitiy to get quick access to the Player */
	@Getter private Entity player;
	
	/** Stores the collision type of each tile on the map */
	private CollisionType[][] collMap;
	
	/** Physical representation of the world in Box2D */
	@Getter private World world2d;
	
	/** List of all Entities on the map */
	@Getter private List<Entity> entities;
	
	
	/** Construct new Map Scene */
	public MapScene(MapReference mapReference){
		this.world2d = new World(new Vector2(0, 0), true);
		this.entities = new ArrayList<Entity>();
		initMap(mapReference);
		this.camera = new MapCamera(width, height);
		this.batch = new SpriteBatch();
		//TODO: Generalize this
		this.player = new Prota(this, new Point(120, 140), camera);
	}
	
	/** Initialize Tiled Map values */
	private void initMap(MapReference mapReference){
		TmxMapLoader mapLoader = new TmxMapLoader();
		this.map = mapLoader.load(mapReference.getPath());
		boolean setDimensions = false;
		Iterator<MapLayer> iterator = map.getLayers().iterator();
		while(iterator.hasNext()){
			MapLayer layer = iterator.next();
			if(layer.getProperties().containsKey(MapKeys.UNRENDERED.getKey())){
				layer.setVisible(false);
			}
			if(layer instanceof TiledMapTileLayer){
				TiledMapTileLayer tiledLayer = (TiledMapTileLayer)layer;
				if(!setDimensions){
					this.width = tiledLayer.getWidth();
					this.height = tiledLayer.getHeight();
					collMap = new CollisionType[this.height][this.width];
					for(int cy=0; cy<collMap.length; cy++){
						for(int cx=0; cx<collMap[0].length; cx++){
							collMap[cy][cx] = CollisionType.SOLID;
						}
					}
					setDimensions = true;
				}
				for(int cy=0; cy<collMap.length; cy++){
					for(int cx=0; cx<collMap[0].length; cx++){
						if(tiledLayer.getCell(cx, cy)!=null && tiledLayer.getCell(cx, cy).getTile()!=null){
							//Determine Collision
							int collY = this.height-1-cy;
							TiledMapTile tile = tiledLayer.getCell(cx, cy).getTile();
							if(tile.getProperties().get(MapKeys.GROUND.getKey(), String.class)!=null){
								collMap[collY][cx] = CollisionType.GROUND;
							}
							if(tile.getProperties().get(MapKeys.COLLISION.getKey(), String.class)!=null){
								collMap[collY][cx] = CollisionType.SOLID;
							}
							//Determine Spawn
							if(tile.getProperties().get(MapKeys.SPAWN.getKey(), String.class)!=null){
								String allSpawnData = tile.getProperties().get(MapKeys.SPAWN.getKey(), String.class);
								Scanner quickScanner = new Scanner(allSpawnData);
								String mainKey = quickScanner.next();
								for(SpawnEntityEnum sp : SpawnEntityEnum.values()){
									if(sp.getMainKey().equals(mainKey)){
										StringBuffer spawnData = new StringBuffer();
										while(quickScanner.hasNext()){
											spawnData.append(quickScanner.next()+" ");
										}
										sp.spawn(this, new Point(cx, collY), spawnData.toString());
									}
								}
							}
						}
					}
				}
			}
		}
		MapCollisionPolygonFactory.createAndAddMapCollision(world2d, collMap);
		this.renderer = new OrthogonalTiledMapRenderer(map);
	}
	
	/** Invokes update in all entities */
	@Override public void update(float delta){
		removeEntities();
		for(int c=0; c<entities.size(); c++){
			entities.get(c).move(delta);
		}
		this.world2d.step(delta, 8, 3);
		for(int c=0; c<entities.size(); c++){
			entities.get(c).update(delta);
		}
	}
	
	/** Removes disposable Entities */
	private void removeEntities(){
		for(int c=0; c<entities.size(); c++){
			if(entities.get(c).isDisposable()){
				entities.get(c).dispose();
				entities.remove(c);
				c--;
			}
		}
	}
	
	/** Spawns the given Entity on the Map (entities spawn themselves on the map already!) */
	public void spawnEntity(Entity entity){
		this.entities.add(entity);
	}
	
	/** Renders Map and invokes rendering in all entities */
	@Override public void render(){
		//Render Map
		camera.setRendererView(renderer).render();
		//Render Entities
		camera.setBatchView(batch);
		batch.begin();
		for(EntityRenderLayer currentLayer : EntityRenderLayer.values()){
			for(int c=0; c<entities.size(); c++){
				entities.get(c).render(batch, currentLayer);
			}
		}
		batch.end();
	}
	
	/** Returns the collision type of the given coordinate */
	public CollisionType getCollision(int x, int y){
		if(x<0 || y<0 || x>=collMap[0].length || y>=collMap.length){
			return CollisionType.SOLID;
		}
		return this.collMap[y][x];
	}
	
	@Override public void processEvent(Event event){}
	
	@Override public void init(){}
	
	/** Disposes of the Map */
	@Override public void dispose(){
		map.dispose();
		renderer.dispose();
		batch.dispose();
		for(int c=0; c<entities.size(); c++){
			entities.get(c).dispose();
		}
		world2d.dispose();
	}
	
}
