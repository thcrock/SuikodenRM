package entities;

import animations.GameAnimation;
import animations.ImageCache;
import gamestate.Scriptable;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.orangeegames.suikorm.SuikodenRM;

public class Player extends DrawableBox2D implements Scriptable {
	
	private static int faceUP = 0;
	private static int faceDOWN = 1;
	private static int faceRIGHT = 2;
	private static int faceLEFT = 3;
	
	/** the movement velocity **/
	
	private float speed = 90f*SuikodenRM.scale;
	private float maxSpeed = 90f*SuikodenRM.scale;
	
	private boolean left;
	private boolean right;
	private boolean up;
	private boolean down;

	int currentDirection;
	
	private float dx = 0;
	private float dy = 0;
	private float animTime = 0;
	private GameAnimation currentWalkAnim;

    /** To enable scripting **/
    private float checkpointX;
    private float checkpointY;
    private float targetX;
    private float targetY;
    private boolean currentlyPaused = false;
    private boolean currentlyTalking = false;
    private boolean isInScript;
	protected ArrayList<String> messages = new ArrayList<String>();
	protected int startMessage = 0;
	protected int stopMessage = 0;
    private float pauseSeconds = 0;
	protected TextureRegion facePicture;
	protected String name;
    private boolean coupleMovementAndAnimation = true;
    private int currentChoice = -1;
	
	private static GameAnimation leftAnim = new GameAnimation(0.2f, new TextureRegion[]{
			ImageCache.getFrame("mia", 10), 
			ImageCache.getFrame("mia", 11), 
			ImageCache.getFrame("mia", 12), 
			});
	
	private static GameAnimation upAnim = new GameAnimation(0.2f, new TextureRegion[]{
			ImageCache.getFrame("mia", 1), 
			ImageCache.getFrame("mia", 2), 
			ImageCache.getFrame("mia", 3), 
			});

	private static GameAnimation rightAnim = new GameAnimation(0.2f, new TextureRegion[]{
			ImageCache.getFrame("mia", 4), 
			ImageCache.getFrame("mia", 5), 
			ImageCache.getFrame("mia", 6), 
	        });
	
	private static GameAnimation downAnim = new GameAnimation(0.2f, new TextureRegion[]{
			ImageCache.getFrame("mia", 7), 
			ImageCache.getFrame("mia", 8), 
			ImageCache.getFrame("mia", 9), 
			});
	
	
	public Player(Body body) {
		super(new TextureRegion(ImageCache.getFrame("mia", 7)));
		
		//sprite = new TextureRegion(ImageCache.getFrame("riouWalkDown", 2));
		currentWalkAnim = downAnim;
		this.body = body;
        this.name = "Mia";
		
		setAdjustWidth(false);
        TextureRegion exampleSprite = new TextureRegion(ImageCache.getFrame("mia", 7));
    	setHeight(exampleSprite.getRegionHeight()*SuikodenRM.scale*0.5f);
    	setWidth(exampleSprite.getRegionWidth()*SuikodenRM.scale*0.5f);
    	this.setCenterX(10);
    	this.setCenterY(10);
	}
	
	public void draw(Batch spriteBatch) {
		this.draw(spriteBatch, body);
	}
	
    public Vector2 getLinearVelocity() {
        return body.getLinearVelocity();
    }

	public void update2(float delta) {
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
		
		body.setLinearVelocity(new Vector2(dx, dy));
		
		animTime += Gdx.graphics.getDeltaTime();
        if(coupleMovementAndAnimation) {
            if(isUp()) {
                this.setSpeed(maxSpeed);
                currentWalkAnim = upAnim;
                this.setRegion(upAnim.getKeyFrame(animTime, true));
                this.setWidth(upAnim.getKeyFrame(animTime, true).getRegionWidth()*SuikodenRM.scale*0.5f);
                this.setHeight(upAnim.getKeyFrame(animTime, true).getRegionHeight()*SuikodenRM.scale*0.5f);
                currentDirection = faceUP;
            }
            else if(isDown()) {
                this.setSpeed(maxSpeed);
                currentWalkAnim = downAnim;
                this.setRegion(downAnim.getKeyFrame(animTime, true));
                this.setWidth(downAnim.getKeyFrame(animTime, true).getRegionWidth()*SuikodenRM.scale*0.5f);
                this.setHeight(downAnim.getKeyFrame(animTime, true).getRegionHeight()*SuikodenRM.scale*0.5f);
                currentDirection = faceDOWN;
            }
            else if(isLeft()) {
                this.setSpeed(maxSpeed);
                currentWalkAnim = leftAnim;
                this.setRegion(leftAnim.getKeyFrame(animTime, true));
                this.setWidth(leftAnim.getKeyFrame(animTime, true).getRegionWidth()*SuikodenRM.scale*0.5f);
                this.setHeight(leftAnim.getKeyFrame(animTime, true).getRegionHeight()*SuikodenRM.scale*0.5f);
                currentDirection = faceLEFT;
            }
            else if(isRight()) {
                this.setSpeed(maxSpeed);
                currentWalkAnim = rightAnim;
                this.setRegion(rightAnim.getKeyFrame(animTime, true));
                this.setWidth(rightAnim.getKeyFrame(animTime, true).getRegionWidth()*SuikodenRM.scale*0.5f);
                this.setHeight(rightAnim.getKeyFrame(animTime, true).getRegionHeight()*SuikodenRM.scale*0.5f);
                currentDirection = faceRIGHT;
            }
            else {
                this.setRegion(currentWalkAnim.getKeyFrame(animTime, false));
                this.setWidth(currentWalkAnim.getKeyFrame(animTime, false).getRegionWidth()*SuikodenRM.scale*0.5f);
                this.setHeight(currentWalkAnim.getKeyFrame(animTime, false).getRegionHeight()*SuikodenRM.scale*0.5f);
            }
        }

        if(isInScript && this.hasReachedTarget()) {
            this.setRight(false);
            this.setLeft(false);
            this.setUp(false);
            this.setDown(false);
            this.setSpeed(0.0f);
        }
        if(this.pauseSeconds > 0) {
            this.pauseSeconds -= delta;
        }
		
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
		if(!left && currentWalkAnim == leftAnim) {
			this.setTexture(leftAnim.getKeyFrame(animTime, false).getTexture());
		}
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
		if(!right && currentWalkAnim == rightAnim) {
			this.setTexture(rightAnim.getKeyFrame(animTime, false).getTexture());
		}
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
		if(!up && currentWalkAnim == upAnim) {
			this.setTexture(upAnim.getKeyFrame(animTime, false).getTexture());
		}
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
		if(!down && currentWalkAnim == downAnim) {
			this.setTexture(downAnim.getKeyFrame(animTime, false).getTexture());
		}
	}
	
	public void interact() {
		
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

	@Override
	public void interact(Player player) {
		
	}

    public Vector2 getPosition() {
        return body.getPosition();
    }
	
	public void dispose() {
		getTexture().dispose();
	}

    public void moveRight(int distance, float speed) {
        this.setRight(true);
        this.setLeft(false);
        this.setUp(false);
        this.setDown(false);
        this.setSpeed(speed);
        if(this.targetX != 0f) {
            this.checkpointX = this.targetX;
        } else {
            this.checkpointX = this.getPosition().x;
        }
        this.targetX = this.checkpointX + distance;
    }
    public void moveLeft(int distance, float speed) {
        this.setLeft(true);
        this.setRight(false);
        this.setUp(false);
        this.setDown(false);
        this.setSpeed(speed);
        if(this.targetX != 0f) {
            this.checkpointX = this.targetX;
        } else {
            this.checkpointX = this.getPosition().x;
        }
        this.targetX = this.checkpointX - distance;
    }
    public void moveUp(int distance, float speed) {
        this.setRight(false);
        this.setLeft(false);
        this.setUp(true);
        this.setDown(false);
        this.setSpeed(speed);
        if(this.targetY != 0f) {
            this.checkpointY = this.targetY;
        } else {
            this.checkpointY = this.getPosition().y;
        }
        this.targetY = this.checkpointY + distance;
    }
    public void moveDown(int distance, float speed) {
        this.setRight(false);
        this.setLeft(false);
        this.setUp(false);
        this.setDown(true);
        this.setSpeed(speed);
        if(this.targetY != 0f) {
            this.checkpointY = this.targetY;
        } else {
            this.checkpointY = this.getPosition().y;
        }
        this.targetY = this.checkpointY - distance;
    }
    public void pauseFor(float seconds) {
        this.setRight(false);
        this.setLeft(false);
        this.setUp(false);
        this.setDown(false);
        this.setSpeed(0.0f);
        this.currentlyPaused = true;
        this.pauseSeconds = seconds;
    }
    public void animationFrame(String textureName, int index) {
		this.setRegion(ImageCache.getFrame(textureName, index));
    }
    public void sayMessage(String message, String speakerOverrideName) {
        messages.add(message);
        this.startMessage = messages.size() - 1;
        this.stopMessage = messages.size();
        currentlyTalking = true;
		SuikodenRM.gsm.setMessage(this, speakerOverrideName);
    }
    public void giveChoices(String[] choices) {
        currentlyTalking = true;
		SuikodenRM.gsm.setChoiceState(this, choices);
    }

    public int getCurrentChoice() {
        return currentChoice;
    }

    public void setCurrentChoice(int choice) {
        currentChoice = choice;
        this.currentlyTalking = false;
    }
    public boolean hasFinishedAction() {
        if(this.currentlyPaused == true) {
            return this.pauseSeconds <= 0;
        }
        if(this.currentlyTalking == true) {
            if(SuikodenRM.gsm.PAUSED) {
                return false;
            } else {
                this.currentlyTalking = false;
                return true;
            }
        }
        if(!right && !left && !up && !down) {
            return true;
        }
        return hasReachedTarget();
    }
    private boolean hasReachedTarget() {
        if(this.isRight()) {
            if(this.getPosition().x >= this.targetX) {
               return true;
            }
        }
        if(this.isLeft()) {
            if(this.getPosition().x <= this.targetX) {
                return true;
            }
        }
        if(this.isUp()) {
            if(this.getPosition().y >= this.targetY) {
               return true;
            }
        }
        if(this.isDown()) {
            if(this.getPosition().y <= this.targetY) {
                return true;
            }
        }
        return false;
    }
    public void startScript() {
        this.isInScript = true;
    }
    public void stopScript() {
        this.isInScript = false;
        this.setRight(false);
        this.setLeft(false);
        this.setUp(false);
        this.setDown(false);
        this.setSpeed(0.0f);
        this.coupleMovementAndAnimation = true;
    }
	public TextureRegion getFacePicture() {
		return facePicture;
	}
	
	public List<String> getMessages() {
		return messages.subList(startMessage, stopMessage);
	}
	
	public String getName() {
		return name;
	}
    public void decoupleMovementAndAnimation() {
        this.coupleMovementAndAnimation = false;
    }
    public void coupleMovementAndAnimation() {
        this.coupleMovementAndAnimation = true;
    }
    public void hasFinishedTalking() {
        this.currentlyTalking = false;
    }
    public void disableCollisions() {
        body.setType(BodyDef.BodyType.KinematicBody);
        for (Fixture f : body.getFixtureList()) {
            Filter filterData = f.getFilterData();
            filterData.categoryBits = 0x0000;
            f.setFilterData(filterData);
        }
        body.setAwake(false);
    }
    public void enableCollisions() {
        body.setType(BodyDef.BodyType.DynamicBody);
        for (Fixture f : body.getFixtureList()) {
            Filter filterData = f.getFilterData();
            filterData.categoryBits = 0x0001;
            f.setFilterData(filterData);
        }
        body.setAwake(true);
    }
    public void attachTo(Scriptable character) {
    }
    public void detachFrom(Scriptable character) {
    }
}
