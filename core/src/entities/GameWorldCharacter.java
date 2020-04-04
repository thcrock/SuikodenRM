package entities;

import gamestate.BoxWorld;

import java.util.ArrayList;
import java.util.List;

import animations.GameAnimation;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.orangeegames.suikorm.SuikodenRM;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.orangeegames.suikorm.SuikodenRM;

public abstract class GameWorldCharacter extends DrawableBox2D {
	
	private static int faceUP = 0;
	private static int faceDOWN = 1;
	private static int faceRIGHT = 2;
	private static int faceLEFT = 3;

	private float speed = 60f*SuikodenRM.scale;
	private float maxSpeed = 60f*SuikodenRM.scale;
	
	private boolean left;
	private boolean right;
	private boolean up;
	private boolean down;
	int currentDirection;
	
	protected float dx = 0;
	protected float dy = 0;
	protected float animTime = 0;
	
	protected GameAnimation currentWalkAnim;
	
	protected GameAnimation leftAnim;
	protected GameAnimation upAnim;
	protected GameAnimation rightAnim;
	protected GameAnimation downAnim;
	
	protected TextureRegion facePicture;
	
	protected String name;
	
	protected ArrayList<String> messages = new ArrayList<String>();
	
	protected int startMessage = 0;
	protected int stopMessage = 0;
	
	protected boolean moveable = true;
	private boolean fighting = false;
	
	public GameWorldCharacter(TextureRegion firstFrame, BoxWorld bw, float x, float y) {
		super(firstFrame);
		
		BodyDef gameCharacter = new BodyDef();
		gameCharacter.type = BodyDef.BodyType.StaticBody;
		gameCharacter.position.set(new Vector2(x, y));

		body = bw.getWorld().createBody(gameCharacter);
		body.setUserData(this);
        this.body = body;
		
		Vector2[] vec = new Vector2[4];
		vec[0] = new Vector2(7.8f*SuikodenRM.scale, 7.8f*SuikodenRM.scale);
		vec[1] = new Vector2(0f*SuikodenRM.scale, 3.9f*SuikodenRM.scale);
		vec[2] = new Vector2(7.8f*SuikodenRM.scale, 0f*SuikodenRM.scale);
		vec[3] = new Vector2(15.6f*SuikodenRM.scale, 3.9f*SuikodenRM.scale);
		
		PolygonShape shape = new PolygonShape();
		shape.set(vec);
		
		body.createFixture(shape, 0f);
		shape.dispose();

		this.setAdjustWidth(false);
		
		setHeight(firstFrame.getRegionHeight()*SuikodenRM.scale);
    	setWidth(firstFrame.getRegionWidth()*SuikodenRM.scale);

		this.setCenterX(this.getOriginX()*SuikodenRM.scale-4*SuikodenRM.scale);
		
	}
	
	public void draw(Batch spriteBatch) {
		this.draw(spriteBatch, body);
	}
	
	public void interact(Player player) {
		if(moveable) {
			TextureRegion tr = null;
			float diffX = this.getBody().getPosition().x - player.getBody().getPosition().x;
			float diffY = this.getBody().getPosition().y - player.getBody().getPosition().y;
			System.out.println("X: " + diffX + " , Y: " + diffY);
			if(diffX > 8*SuikodenRM.scale) {
				tr = this.leftAnim.getKeyFrame(0.3f, false);
			} else if(diffX < -8*SuikodenRM.scale) {
				tr = this.rightAnim.getKeyFrame(0.3f, false);
			} else if(diffY > 8*SuikodenRM.scale) {
				tr = this.downAnim.getKeyFrame(0.3f, false);
			} else if(diffY < -8*SuikodenRM.scale) {
				tr = this.upAnim.getKeyFrame(0.3f, false);
			}
			
			if(tr != null) {
				this.setRegion(tr);
				this.setHeight(tr.getRegionHeight()*SuikodenRM.scale);
				this.setWidth(tr.getRegionWidth()*SuikodenRM.scale);
			}
		}
		SuikodenRM.gsm.setMessage(this);
	}
	
	public String getName() {
		return name;
	}
	
	public TextureRegion getFacePicture() {
		return facePicture;
	}
	
	public List<String> getMessages() {
		return messages.subList(startMessage, stopMessage);
	}

	public void setMessage(int startMessage, int stopMessage) {
		this.startMessage = startMessage;
		this.stopMessage = stopMessage;
	}
	
	public void setMoveable(boolean moveable) {
		this.moveable = moveable;
	}
	
	public void update(float delta) {
		if(left) {
			dx -= speed;
			if(dx < -maxSpeed)
				dx = -maxSpeed;
		}
		else if(right) {
			dx += speed;
			if(dx > maxSpeed)
				dx = maxSpeed;
		}
		else {
			dx = 0;
		}
		
		if(up) {
			dy += speed;
			if(dy > speed)
				dy = speed;
		}
		else if(down) {
			dy -= speed;
			if(dy < -speed)
				dy = -speed;
		}
		else {
			dy = 0;
		}
		
		this.body.setLinearVelocity(new Vector2(dx, dy));
		
		animTime += Gdx.graphics.getDeltaTime();
		if(isUp()) {
			this.currentWalkAnim = upAnim;
			this.setRegion(upAnim.getKeyFrame(animTime, true));
			this.setWidth(upAnim.getKeyFrame(animTime, true).getRegionWidth()*SuikodenRM.scale*0.5f);
			this.setHeight(upAnim.getKeyFrame(animTime, true).getRegionHeight()*SuikodenRM.scale*0.5f);
			currentDirection = faceUP;
		}
		else if(isDown()) {
			this.currentWalkAnim = this.downAnim;
			this.setRegion(this.downAnim.getKeyFrame(animTime, true));
			this.setWidth(this.downAnim.getKeyFrame(animTime, true).getRegionWidth()*SuikodenRM.scale*0.5f);
			this.setHeight(this.downAnim.getKeyFrame(animTime, true).getRegionHeight()*SuikodenRM.scale*0.5f);
			currentDirection = faceDOWN;
		}
		else if(isLeft()) {
			this.currentWalkAnim = this.leftAnim;
			this.setRegion(this.leftAnim.getKeyFrame(animTime, true));
			this.setWidth(this.leftAnim.getKeyFrame(animTime, true).getRegionWidth()*SuikodenRM.scale*0.5f);
			this.setHeight(this.leftAnim.getKeyFrame(animTime, true).getRegionHeight()*SuikodenRM.scale*0.5f);
			currentDirection = faceLEFT;
		}
		else if(isRight()) {
			this.currentWalkAnim = this.rightAnim;
			this.setRegion(this.rightAnim.getKeyFrame(animTime, true));
			this.setWidth(this.rightAnim.getKeyFrame(animTime, true).getRegionWidth()*SuikodenRM.scale*0.5f);
			this.setHeight(this.rightAnim.getKeyFrame(animTime, true).getRegionHeight()*SuikodenRM.scale*0.5f);
			currentDirection = faceRIGHT;
		}
		else {
			this.setRegion(this.currentWalkAnim.getKeyFrame(animTime, false));
			this.setWidth(this.currentWalkAnim.getKeyFrame(animTime, false).getRegionWidth()*SuikodenRM.scale*0.5f);
			this.setHeight(this.currentWalkAnim.getKeyFrame(animTime, false).getRegionHeight()*SuikodenRM.scale*0.5f);
		}

	}

	public boolean isFighting() {
		return fighting;
	}

	public void setFighting(boolean fighting) {
		this.fighting = fighting;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
		if(!left && this.currentWalkAnim == leftAnim) {
			this.setTexture(this.leftAnim.getKeyFrame(animTime, false).getTexture());
		}
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
		if(!right && this.currentWalkAnim == rightAnim) {
			this.setTexture(this.rightAnim.getKeyFrame(animTime, false).getTexture());
		}
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
		if(!up && this.currentWalkAnim == upAnim) {
			this.setTexture(this.upAnim.getKeyFrame(animTime, false).getTexture());
		}
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
		if(!down && this.currentWalkAnim == this.downAnim) {
			this.setTexture(this.downAnim.getKeyFrame(animTime, false).getTexture());
		}
	}

	public boolean faceUp() {
		return currentDirection == faceUP;
	}
	
	public boolean faceDown() {
		return currentDirection == faceDOWN;
	}
	
	public boolean faceRight() {
		return currentDirection == faceRIGHT;
	}
	
	public boolean faceLeft() {
		return currentDirection == faceLEFT;
	}
}
