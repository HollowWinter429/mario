package com.ismail.mario.Sprites;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.ismail.mario.Mario;
import com.ismail.mario.Screens.PlayScreen;

public abstract class InteractiveTileObject {
	protected World world;
	protected TiledMap map;
	protected TiledMapTile tile;
	protected Rectangle bounds;
	protected Body body;
	protected Fixture fixture;
	protected PlayScreen screen;
	protected MapObject object;
	
	public InteractiveTileObject(PlayScreen screen, MapObject object) {
		this.screen = screen;
		this.world = screen.getWorld();
		this.map = screen.getMap();
		this.object = object;
		this.bounds = ((RectangleMapObject) object).getRectangle();  
		
		
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		
		bdef.type = BodyDef.BodyType.StaticBody;
		bdef.position.set((bounds.getX() + bounds.getWidth()/2)/ Mario.PPM, (bounds.getY() + bounds.getHeight()/2)/ Mario.PPM);
		
		body = world.createBody(bdef);
		
		shape.setAsBox(bounds.getWidth()/2/ Mario.PPM, bounds.getHeight()/2/ Mario.PPM);
		fdef.shape = shape;
		fixture = body.createFixture(fdef);
	}
	
	public abstract void onHeadHit(MarioSprite mario);
	
	public void setCategoryFilter(short filterBit) {
		Filter filter = new Filter();
		filter.categoryBits = filterBit;
		fixture.setFilterData(filter); 
	}
	
	public Cell getCell() {
		TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);
		return layer.getCell((int)(body.getPosition().x * Mario.PPM / 16),(int)(body.getPosition().y * Mario.PPM / 16));
	}
}
