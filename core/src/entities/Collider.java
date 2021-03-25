package entities;

import com.orangeegames.suikorm.SuikodenRM;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.math.Vector2;
import gamestate.BoxWorld;


public class Collider {

	public String message;
    public String tag;
    BoxWorld bw;
    Body body;
	
	public Collider(BoxWorld bw, float x, float y, float height, float width, String message, String tag) {
		this.message = message;
        this.tag = tag;
        this.bw = bw;

        PolygonShape ps = new PolygonShape();
        ps.setAsBox((width/2)*SuikodenRM.scale, (height/2)*SuikodenRM.scale);
        FixtureDef fixture = new FixtureDef();
        fixture.shape = ps;
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(new Vector2((x + width/2)*SuikodenRM.scale, (y + height/2)*SuikodenRM.scale));
        Body body = bw.getWorld().createBody(bodyDef);
        
        body.createFixture(fixture);
        body.setUserData(this);
        this.body = body;
	}

	public void interact(Player player) {
		SuikodenRM.gsm.setInfo(this.message);
	}

    public void disableBody() {
        System.out.println("Disabling body");
        //bw.getWorld().destroyBody(body);
        body.setType(BodyDef.BodyType.KinematicBody);
    }
}
