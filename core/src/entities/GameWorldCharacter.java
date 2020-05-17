package entities;

import gamestate.BoxWorld;

import java.util.ArrayList;
import java.util.List;
import java.lang.Float;

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

enum Direction { Right, Left, Down, Up, Pause };

class Phase {
    public Direction direction;
    public int distance;
    public float secondsPause;
    public Float speed;

    public Phase(Direction direction, int distance, float secondsPause, Float speed) {
        this.direction = direction;
        this.distance = distance;
        this.secondsPause = secondsPause;
        this.speed = speed;
    }
}
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
	
	protected boolean moveable = false;
	private boolean fighting = false;
	
    private float startX;
    private float startY;
    private float checkpointX;
    private float checkpointY;
    private float targetX;
    private float targetY;
    private Phase[] phases;
    private int phaseIndex = -1;
    private float pauseSeconds = 0;

	public GameWorldCharacter(TextureRegion firstFrame, BoxWorld bw, float x, float y) {
		super(firstFrame);
		
		BodyDef gameCharacter = new BodyDef();
		gameCharacter.type = BodyDef.BodyType.DynamicBody;
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
	
    public void setPhases(String phaseText) {
        this.phases = this.phaseListFromText(phaseText);
        this.startX = this.getPosition().x;
        this.startY = this.getPosition().y;
        this.checkpointX = this.startX;
        this.checkpointY = this.startY;
        this.targetX = this.checkpointX;
        this.targetY = this.checkpointY;
        this.nextPhase();
    }
	public void draw(Batch spriteBatch) {
		this.draw(spriteBatch, body);
	}

    protected Vector2 getPosition() {
        return this.body.getPosition();
    }
	
	public void interact(Player player) {
		if(moveable) {
			TextureRegion tr = null;
			float diffX = this.getBody().getPosition().x - player.getBody().getPosition().x;
			float diffY = this.getBody().getPosition().y - player.getBody().getPosition().y;
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
			dx = -speed;
			if(dx < -maxSpeed) {
				dx = -maxSpeed;
            }
		}
		else if(right) {
			dx = speed;
			if(dx > maxSpeed) {
				dx = maxSpeed;
            }
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

        if(this.isRight()) {
            if(this.getPosition().x >= this.targetX) {
               this.nextPhase(); 
            }
        }
        if(this.isLeft()) {
            if(this.getPosition().x <= this.targetX) {
                this.nextPhase();
            }
        }
        if(this.isUp()) {
            if(this.getPosition().y >= this.targetY) {
               this.nextPhase(); 
            }
        }
        if(this.isDown()) {
            if(this.getPosition().y <= this.targetY) {
                this.nextPhase();
            }
        }
        if(this.phases != null && this.phases[this.phaseIndex].direction == Direction.Pause) {
            this.pauseSeconds += delta;
            if(this.pauseSeconds > this.phases[this.phaseIndex].secondsPause) {
                this.nextPhase();
            }
        }
	}

    protected void setFaceDown() {
        this.currentWalkAnim = this.downAnim;
        this.setRegion(this.downAnim.getKeyFrame(animTime, true));
        this.setWidth(this.downAnim.getKeyFrame(animTime, true).getRegionWidth()*SuikodenRM.scale*0.5f);
        this.setHeight(this.downAnim.getKeyFrame(animTime, true).getRegionHeight()*SuikodenRM.scale*0.5f);
        currentDirection = faceDOWN;
    }
    protected void setFaceUp() {
        this.currentWalkAnim = this.upAnim;
        this.setRegion(this.upAnim.getKeyFrame(animTime, true));
        this.setWidth(this.upAnim.getKeyFrame(animTime, true).getRegionWidth()*SuikodenRM.scale*0.5f);
        this.setHeight(this.upAnim.getKeyFrame(animTime, true).getRegionHeight()*SuikodenRM.scale*0.5f);
        currentDirection = faceUP;
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
	
	public void setMaxSpeed(float speed) {
		this.maxSpeed = speed;
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

    public void setDirection(String direction) {
        if(direction.equals("d")) {
            setFaceDown();
        } else if(direction.equals("u")) {
            setFaceUp();
        }
        //setSpeed(0.0f);
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
    private Phase[] phaseListFromText(String text) {
        if (text.length() == 0) {
            return null;
        }
        String[] phaseStrings = text.split(";");
        Phase[] phases = new Phase[phaseStrings.length];
        for (int i = 0; i < phaseStrings.length; i++) {
            String[] parts = phaseStrings[i].split("-");
            Float speed;
            if(parts.length == 3) {
                speed = Float.parseFloat(parts[2]);
            } else {
                speed = null;
            }
            if(parts[0].equals("d")) {
                phases[i] = new Phase(Direction.Down, Integer.parseInt(parts[1]), 0, speed);
            } else if(parts[0].equals("u")) {
                phases[i] = new Phase(Direction.Up, Integer.parseInt(parts[1]), 0, speed);
            } else if(parts[0].equals("l")) {
                phases[i] = new Phase(Direction.Left, Integer.parseInt(parts[1]), 0, speed);
            } else if(parts[0].equals("r")) {
                phases[i] = new Phase(Direction.Right, Integer.parseInt(parts[1]), 0, speed);
            } else if(parts[0].equals("p")) {
                phases[i] = new Phase(Direction.Pause, 0, Float.parseFloat(parts[1]), speed);
            } else {
            }
        }
        return phases;
    }

    public void nextPhase() {
        if (this.phases == null || this.phases.length == 0) {
            return;
        }
        if (phaseIndex+1 >= this.phases.length) {
            phaseIndex = 0;
        } else {
            phaseIndex++;
        }

        Phase phase = this.phases[phaseIndex];
        if(phase.direction == Direction.Left) {
            this.setLeft(true);
            this.setRight(false);
            this.setUp(false);
            this.setDown(false);
            if(phase.speed != null) {
                System.out.println("custom speed!" + phase.speed.toString());
                this.setSpeed(phase.speed);
            } else {
                this.setSpeed(maxSpeed);
            }
            this.checkpointX = this.targetX;
            this.checkpointY = this.targetY;
            this.targetX = this.checkpointX - phase.distance;
        } else if(phase.direction == Direction.Right) {
            this.setRight(true);
            this.setLeft(false);
            this.setUp(false);
            this.setDown(false);
            if(phase.speed != null) {
                this.setSpeed(phase.speed);
            } else {
                this.setSpeed(maxSpeed);
            }
            this.checkpointX = this.targetX;
            this.targetX = this.checkpointX + phase.distance;
        } else if(phase.direction == Direction.Up) {
            this.setRight(false);
            this.setLeft(false);
            this.setUp(true);
            this.setDown(false);
            if(phase.speed != null) {
                this.setSpeed(phase.speed);
            } else {
                this.setSpeed(maxSpeed);
            }
            this.checkpointY = this.targetY;
            this.targetY = this.checkpointY + phase.distance;
        } else if(phase.direction == Direction.Down) {
            this.setRight(false);
            this.setLeft(false);
            this.setUp(false);
            this.setDown(true);
            if(phase.speed != null) {
                this.setSpeed(phase.speed);
            } else {
                this.setSpeed(maxSpeed);
            }
            this.checkpointY = this.targetY;
            this.targetY = this.checkpointY - phase.distance;
        } else if(phase.direction == Direction.Pause) {
            this.setRight(false);
            this.setLeft(false);
            this.setUp(false);
            this.setDown(false);
            //this.setFaceDown();
            this.setSpeed(0.0f);
            this.pauseSeconds = 0;
            this.checkpointX = this.targetX;
            this.targetX = this.checkpointX;
        }
    }
}
