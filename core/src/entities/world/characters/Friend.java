package entities.world.characters;

import entities.GameWorldCharacter;
import entities.Player;
import gamestate.BoxWorld;
import animations.GameAnimation;
import animations.ImageCache;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.orangeegames.suikorm.SuikodenRM;


public class Friend extends GameWorldCharacter {
    BoxWorld boxWorld;
    float distanceLastTime;
    boolean initiallyAttached = false;

	public Friend(TextureRegion firstFrame, BoxWorld bw, float x, float y) {
		super(firstFrame, bw, x, y);
        boxWorld = bw;
        distanceLastTime = 0;
		
		name = "Renata";
		
        this.facePicture = new TextureRegion(ImageCache.getFrame("renataface", 1));
		this.downAnim = new GameAnimation(0.2f, new TextureRegion[]{
				ImageCache.getFrame("renata", 7), 
                ImageCache.getFrame("renata", 8), 
				ImageCache.getFrame("renata", 7), 
				ImageCache.getFrame("renata", 9), 
				});
		
		this.leftAnim = new GameAnimation(0.2f, new TextureRegion[]{
				ImageCache.getFrame("renata", 10), 
                ImageCache.getFrame("renata", 11), 
				ImageCache.getFrame("renata", 10), 
				ImageCache.getFrame("renata", 12), 
				});
		
		this.upAnim = new GameAnimation(0.2f, new TextureRegion[]{
				ImageCache.getFrame("renata", 1), 
                ImageCache.getFrame("renata", 2), 
				ImageCache.getFrame("renata", 1), 
				ImageCache.getFrame("renata", 3), 
				});

		this.rightAnim = new GameAnimation(0.2f, new TextureRegion[]{
				ImageCache.getFrame("renata", 4), 
				ImageCache.getFrame("renata", 5), 
				ImageCache.getFrame("renata", 4), 
				ImageCache.getFrame("renata", 6), 
				});
		
		this.currentWalkAnim = this.downAnim;
		messages.add("Hey M! Great match!");
	}

	public void update(float delta) {
        super.update(delta);
        if(!initiallyAttached) {
            this.attachTo(boxWorld.getPlayer());
            initiallyAttached = true;
        }
        if(this.attachedCharacter != null && !isInScript) {
            float distance = this.attachedCharacter.getPosition().dst2(this.getPosition());

            if (distance > distanceLastTime && distance > 1000) {
                this.setSpeed(this.attachedCharacter.getSpeed());
                this.setRight(this.attachedCharacter.movingRight());
                this.setLeft(this.attachedCharacter.movingLeft());
                this.setUp(this.attachedCharacter.movingUp());
                this.setDown(this.attachedCharacter.movingDown());
            } else if (distance < 250) {
                boolean isAboveAttached = this.attachedCharacter.getPosition().y < this.getPosition().y;
                boolean isLeftFromAttached = this.attachedCharacter.getPosition().x > this.getPosition().x;
                if(this.attachedCharacter.isUp()) {
                    if(isAboveAttached && isLeftFromAttached) {
                        this.setRight(false);
                        this.setLeft(true);
                        this.setUp(false);
                        this.setDown(false);
                        this.setSpeed(60);
                    } else if(isAboveAttached) {
                        this.setRight(true);
                        this.setLeft(false);
                        this.setUp(false);
                        this.setDown(false);
                        this.setSpeed(60);
                    } else {
                        this.setSpeed(0);
                    }
                } else if (this.attachedCharacter.isRight()) {
                    if(!isLeftFromAttached && isAboveAttached) {
                        this.setRight(false);
                        this.setLeft(false);
                        this.setUp(true);
                        this.setDown(false);
                        this.setSpeed(60);
                    } else if(!isLeftFromAttached) {
                        this.setRight(false);
                        this.setLeft(false);
                        this.setUp(false);
                        this.setDown(true);
                        this.setSpeed(60);
                    } else {
                        this.setSpeed(0);
                    }
                } else if (this.attachedCharacter.isDown()) {
                    if(!isAboveAttached && !isLeftFromAttached) {
                        this.setRight(true);
                        this.setLeft(false);
                        this.setUp(false);
                        this.setDown(false);
                        this.setSpeed(60);
                    } else if(!isAboveAttached) {
                        this.setRight(false);
                        this.setLeft(true);
                        this.setUp(false);
                        this.setDown(false);
                        this.setSpeed(60);
                    } else {
                        this.setSpeed(0);
                    }
                } else if (this.attachedCharacter.isLeft()) {
                    if(isLeftFromAttached && isAboveAttached) {
                        this.setRight(false);
                        this.setLeft(false);
                        this.setUp(true);
                        this.setDown(false);
                        this.setSpeed(60);
                    } else if(isLeftFromAttached) {
                        this.setRight(false);
                        this.setLeft(false);
                        this.setUp(false);
                        this.setDown(true);
                        this.setSpeed(60);
                    } else {
                        this.setSpeed(0);
                    }
                }
            }
            distanceLastTime = distance;
        }
    }
}
