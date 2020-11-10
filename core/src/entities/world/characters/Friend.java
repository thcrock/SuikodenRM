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
		
		this.downAnim = new GameAnimation(0.2f, new TextureRegion[]{
				ImageCache.getFrame("friend", 7), 
                ImageCache.getFrame("friend", 8), 
				ImageCache.getFrame("friend", 9), 
				});
		
		this.leftAnim = new GameAnimation(0.2f, new TextureRegion[]{
				ImageCache.getFrame("friend", 10), 
                ImageCache.getFrame("friend", 11), 
				ImageCache.getFrame("friend", 12), 
				});
		
		this.upAnim = new GameAnimation(0.2f, new TextureRegion[]{
				ImageCache.getFrame("friend", 1), 
                ImageCache.getFrame("friend", 2), 
				ImageCache.getFrame("friend", 3), 
				});

		this.rightAnim = new GameAnimation(0.2f, new TextureRegion[]{
				ImageCache.getFrame("friend", 4), 
                ImageCache.getFrame("friend", 5), 
				ImageCache.getFrame("friend", 6), 
				});
		
		this.currentWalkAnim = this.downAnim;
		facePicture = ImageCache.getTexture("friend_7");
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
                this.setRight(this.attachedCharacter.isRight());
                this.setLeft(this.attachedCharacter.isLeft());
                this.setUp(this.attachedCharacter.isUp());
                this.setDown(this.attachedCharacter.isDown());
            } else if (distance < 250) {
                this.setSpeed(0);
            }
            distanceLastTime = distance;
        }
    }
}
