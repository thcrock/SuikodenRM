package entities.world.characters;

import entities.GameWorldCharacter;
import gamestate.BoxWorld;
import animations.GameAnimation;
import animations.ImageCache;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.orangeegames.suikorm.SuikodenRM;


public class Bird extends GameWorldCharacter {

	public Bird(TextureRegion firstFrame, BoxWorld bw, float x, float y, String sprite) {
		super(firstFrame, bw, x, y);
		
		name = "Bird";
		
		this.downAnim = new GameAnimation(0.2f, new TextureRegion[]{
                ImageCache.getFrame(sprite, 7), 
				ImageCache.getFrame(sprite, 8), 
                ImageCache.getFrame(sprite, 7), 
                ImageCache.getFrame(sprite, 9), 
				});
		
		this.leftAnim = new GameAnimation(0.2f, new TextureRegion[]{
                ImageCache.getFrame(sprite, 1), 
				ImageCache.getFrame(sprite, 2), 
                ImageCache.getFrame(sprite, 1), 
                ImageCache.getFrame(sprite, 3), 
				});
		
		this.upAnim = new GameAnimation(0.2f, new TextureRegion[]{
                ImageCache.getFrame(sprite, 4), 
				ImageCache.getFrame(sprite, 5), 
                ImageCache.getFrame(sprite, 4), 
                ImageCache.getFrame(sprite, 6), 
				});

		this.rightAnim = new GameAnimation(0.2f, new TextureRegion[]{
                ImageCache.getFrame(sprite, 10), 
				ImageCache.getFrame(sprite, 11), 
                ImageCache.getFrame(sprite, 10), 
                ImageCache.getFrame(sprite, 12), 
				});
		
		this.currentWalkAnim = this.upAnim;
		facePicture = ImageCache.getTexture("bird_red_8");
		
		messages.add("Squawk");
        for (Fixture fix : getBody().getFixtureList()) {
            fix.setSensor(true);
        }
	}
}
