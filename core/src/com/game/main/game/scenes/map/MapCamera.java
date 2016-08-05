package com.game.main.game.scenes.map;

import lombok.Getter;
import lombok.Setter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.game.main.Constants;
import com.game.main.util.Point;

/** Camera Wrapper used for rendering the Map */
public class MapCamera {
	
	/** Actual Map */
	private OrthographicCamera camera;
	
	/** The Height this Camera is visualizing */
	@Getter private int mapHeight;
	/** The Width this Camera is visualizing */
	@Getter private int mapWidth;
	
	/** The location of the Camera (The position is equivalent to the center of the tile at x|y)*/
	@Getter @Setter private Point location;
	
	/** The Zoom Level of the Camera */
	@Getter @Setter private float zoom;
	
	/** Constructs new Map Camera */
	public MapCamera(int width, int height){
		this.mapHeight = height;
		this.mapWidth = width;
		centerOnTile(new Point(0, 0));
		reset();
	}
	
	/** Resets this Camera */
	public void reset(){
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		this.camera = new OrthographicCamera(1, h/w);
		setDefaultZoom();
	}
	
	/** Centers on the tile of the given point */
	public void centerOnTile(Point tile){
		this.location = new Point(tile.getX()*Constants.TILE, tile.getY()*Constants.TILE).add(Constants.TILE/2);
	}
	
	/** Sets the Camera's default zoom */
	public void setDefaultZoom(){
		this.zoom = Constants.TILE*Constants.TILES_ON_SCREEN_WIDTH;
	}
	
	/** Sets the given TiledMapRenderer's view to this Camera (invokes camera update) */
	public OrthogonalTiledMapRenderer setRendererView(OrthogonalTiledMapRenderer renderer){
		camera.position.x = location.getX();
		camera.position.y = mapHeight*Constants.TILE-location.getY();
		camera.zoom = zoom;
		camera.update();
		renderer.setView(camera);
		return renderer;
	}
	
	/** Sets given SpriteBatch's view to this camera */
	public SpriteBatch setBatchView(SpriteBatch batch){
		camera.position.x = location.getX();
		camera.position.y = -location.getY();
		camera.zoom = zoom;
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		return batch;
	}
	
}
