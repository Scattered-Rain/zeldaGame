package com.game.main.game.scenes.map.entity;

import com.game.main.Constants;
import com.game.main.SpriteContainer;
import com.game.main.SpriteEnum;
import com.game.main.game.events.Event;
import com.game.main.game.scenes.map.MapScene;
import com.game.main.game.scenes.map.entity.abstracts.MobEntity;
import com.game.main.game.scenes.map.entity.abstracts.SpriteEntity;
import com.game.main.util.BoundingBox;
import com.game.main.util.Direction;
import com.game.main.util.Point;

/** Debug Entity to be used for whatever */
public class Debug extends MobEntity{

	public Debug(MapScene map, Point tile) {
		super(map, tile, SpriteEnum.GRASS);
		super.setLocationToTileLocation();
		super.getSprite().setDir(Direction.DOWN);
		super.setAdvanceSprite(false);
	}

}
