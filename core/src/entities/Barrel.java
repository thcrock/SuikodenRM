package entities;

import gamestate.BoxWorld;
import animations.ImageCache;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.orangeegames.suikorm.SuikodenRM;

public class Barrel extends DrawableBox2D {

	public static TextureRegion barrelTexture = ImageCache.getFrame("barrel", 0);
    private boolean currentlyMoving = false;
    private float movingSeconds = 0f;
	
	public Barrel(BoxWorld bw, float x, float y) {
		super(barrelTexture);
		BodyDef barrelBody = new BodyDef();
		barrelBody.type = BodyDef.BodyType.DynamicBody;
		barrelBody.position.set(new Vector2(x, y));

		body = bw.getWorld().createBody(barrelBody);
		body.setUserData(this);
        body.setFixedRotation(true);
        body.setLinearDamping(2);
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(10*SuikodenRM.scale, 20*SuikodenRM.scale);
        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        fdef.density = 0.2f;
        fdef.friction = 0.9f;
        fdef.restitution = 0.5f;
        fdef.filter.categoryBits = 0x0004;
		body.createFixture(fdef);
		this.setHeight(barrelTexture.getRegionHeight()*SuikodenRM.scale);
		this.setAdjustWidth(false);
		this.setWidth(barrelTexture.getRegionWidth()*SuikodenRM.scale);
	}
	
	public void update(float delta) {
    }
	public void interact(Player player) {
		System.out.println("Pushing");
	}

}
