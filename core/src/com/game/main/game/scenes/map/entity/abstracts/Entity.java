package com.game.main.game.scenes.map.entity.abstracts;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.game.main.Constants;
import com.game.main.Core;
import com.game.main.SpriteContainer;
import com.game.main.SpriteEnum;
import com.game.main.game.events.Event;
import com.game.main.game.events.EventProcessor;
import com.game.main.game.scenes.map.AnimationFactory;
import com.game.main.game.scenes.map.CollisionType;
import com.game.main.game.scenes.map.EntityRenderLayer;
import com.game.main.game.scenes.map.MapScene;
import com.game.main.util.BoundingBox;
import com.game.main.util.Direction;
import com.game.main.util.Point;

/** Abstract base for all Entities populating the Map */
public abstract class Entity implements EventProcessor{
	
	/** Holds the next available id */
	private static long idCounter = 0;
	
	
	/** Reference to the map this Entity sits on */
	@Getter private MapScene map;
	
	/** Holds whether this Entity is supposed to get disposed of or not */
	@Getter private boolean disposable;
	
	/** The unique id this Entity can be identified with */
	@Getter private long id;
	
	/** The Bounding box describing this entity (Note that manipulating the bounding box directly can lead to issues because of box2d async) */
	@Getter private BoundingBox boundingBox;
	
	/** The layer on which this Entity is rendered (If not changed set to DEFAULT) */
	@Getter @Setter private EntityRenderLayer renderLayer;
	
	/** The box2d body this Entity uses */
	protected Body body2d;
	
	/** Whether this Entity has collision */
	@Getter private boolean collision;
	
	/** The trajectory onto which this Entity is supposed to be moved by */
	@Getter private Point trajectory;
	
	/** The speed this entity moves with (pixels/second) */
	@Getter @Setter private float baseSpeed;
	
	/** The age of this Entity */
	@Getter private float age;
	
	/** Mob is unable to move/act as long as this variable >0 */
	@Getter private float freeze;
	
	
	/** Constructs new Entity */
	public Entity(MapScene map, BoundingBox boundingBox){
		this.id = idCounter;
		idCounter = idCounter + 1;
		this.disposable = false;
		this.map = map;
		this.boundingBox = boundingBox;
		this.baseSpeed = 0;
		this.trajectory = new Point(0, 0);
		this.freeze = 0;
		//Entities spawn themselves onto the map!
		this.map.spawnEntity(this);
		this.collision = false;
		this.renderLayer = EntityRenderLayer.DEFAULT;
		initBox2d(true);
	}
	
	/** Sets up Box2d (dynamic implies capability to move, anything that never moves is !dynamic, also dynamic is a circle !dynamic a rectangle) */
	private void initBox2d(boolean dynamic){
		//Body Def
		BodyDef bodyDef = new BodyDef();
		if(dynamic){
			bodyDef.type = BodyDef.BodyType.DynamicBody;
		}else{
			bodyDef.type = BodyDef.BodyType.StaticBody;
		}
        bodyDef.position.set(boundingBox.getLocation().getX(), boundingBox.getLocation().getY());
		//Fixture
		Shape shape;
		if(dynamic){
			shape = new CircleShape();
			shape.setRadius(boundingBox.getWidth()/2);
		}
		else{
			shape = new PolygonShape();
			((PolygonShape)shape).setAsBox(boundingBox.getWidth()/2, boundingBox.getHeight()/2);
		}
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0;
        fixtureDef.restitution = 0;
        //Body
        this.body2d = map.getWorld2d().createBody(bodyDef);
        this.body2d.createFixture(fixtureDef);
        this.body2d.setFixedRotation(true);
        //Sets Collision (default is off)
        this.setCollision(collision);
        //Dispose Shape
        shape.dispose();
	}
	
	/** Constructs new Entity at given location and size */
	public Entity(MapScene map, Point location, float size){
		this(map, new BoundingBox(location.clone(), size, size));
	}
	
	/** Constructs new Entity at given location with the size of exactly one Tile */
	public Entity(MapScene map, Point location){
		this(map, new BoundingBox(location.clone(), Constants.TILE, Constants.TILE));
	}
	
	
	/** Updates Entity */
	public final void update(float delta){
		//Update (if not already tagged for disposing)
		if(!disposable){
			this.age += delta;
			this.boundingBox.setLocation(new Point(this.body2d.getPosition().x, this.body2d.getPosition().y));
			if(isFrozen()){
				this.freeze -= delta;
				if(this.freeze<0){
					this.freeze = 0;
				}
			}
			updateEntity(delta);
		}
	}
	
	/** Renders Entity */
	public final void render(SpriteBatch batch, EntityRenderLayer currentLayer){
		if(!disposable){
			if(currentLayer==this.renderLayer){
				renderEntity(batch);
			}
		}
	}
	
	/** Moves this entity by the given velocity */
	public final void move(float delta){
		if(!isFrozen()){
			final float multiplier = 60;
			this.body2d.setLinearVelocity(trajectory.getX()*multiplier, trajectory.getY()*multiplier);
		}
		else{
			this.body2d.setLinearVelocity(0, 0);
		}
	}
	
	/** Processing of Events */
	@Override public final void processEvent(Event event){
		if(!disposable){
			processEventEntity(event);
		}
	}
	
	
	/** Updates Entity */
	public void updateEntity(float delta){}
	
	/** Renders Entity */
	public void renderEntity(SpriteBatch batch){}
	
	/** Processing of Events in Entity */
	public void processEventEntity(Event event){}
	
	
	/** Called when the Entity is disposed */
	public void dispose(){}
	
	
	//Utility methods ---
	/** Sets this Entity to be disposable, i.e. to be removed by the next sweep */
	public final void killEntity(){
		this.disposable = true;
		removeBody2d();
	}
	
	/** Get rid of Box2d body */
	protected void removeBody2d(){
		this.map.getWorld2d().destroyBody(this.body2d);
	}
	
	/** Changes this Entities Location in a way that it directly translates into centered tile location, i.e. location*16+8 */
	protected final void setLocationToTileLocation(){
		this.boundingBox.setLocation(this.boundingBox.getLocation().clone().multiply(Constants.TILE).add(Constants.TILE/2));
		this.body2d.setTransform(new Vector2(boundingBox.getLocation().getX(), boundingBox.getLocation().getY()), 0);
	}
	
	/** Sets the location of this Entity */
	public final void setLocation(Point location){
		this.boundingBox.setLocation(location.clone());
		this.body2d.setTransform(new Vector2(boundingBox.getLocation().getX(), boundingBox.getLocation().getY()), 0);
	}
	
	/** Returns a clone of the Point defining this entities location */
	public final Point getLocation(){
		return this.boundingBox.getLocation().clone();
	}
	
	/** Returns whether this entity is frozen */
	public boolean isFrozen(){
		return this.freeze>0;
	}
	
	/** Spawns in given animation at the location of this entity */
	public void spawnAnimation(AnimationFactory ani){
		ani.spawnAni(map, this.getLocation());
	}
	
	/** Freezes the mob for the given amount of time, if already frozen, freeze time is set to the higher value */
	public void setFreeze(float freezeTime){
		if(isFrozen()){
			this.freeze = Math.max(this.freeze, freezeTime);
		}
		else{
			this.freeze = freezeTime;
		}
	}
	
	/** Sets trajectory */
	public void setTrajectory(Point trajectory){
		trajectory.normalize();
		trajectory.multiply(baseSpeed);
		this.trajectory = trajectory;
	}
	
	/** Activates or deactivates collision */
	public void setCollision(boolean collision){
		this.body2d.setActive(collision);
		this.collision = collision;
	}
	
	/** Reinitialize Box2d body */
	public void reInitBox2d(boolean dynamic){
		removeBody2d();
		this.initBox2d(dynamic);
	}
	
	/** Returns whether the given ID belongs to this Entity */
	public final boolean hasId(long id){
		return this.getId() == id;
	}
	
	/** Returns whether the given Entity is this entity */
	public final boolean isEntity(Entity e){
		return hasId(e.getId());
	}
	
}
