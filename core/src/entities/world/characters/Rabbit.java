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
import com.orangeegames.suikorm.SuikodenRM;


public class Rabbit extends GameWorldCharacter {

	public Rabbit(TextureRegion firstFrame, BoxWorld bw, float x, float y) {
		super(firstFrame, bw, x, y);
		
		name = "Rabbit";
		
		this.downAnim = new GameAnimation(0.2f, new TextureRegion[]{
                ImageCache.getFrame("rabbit", 1), 
				});
		
		this.leftAnim = new GameAnimation(0.2f, new TextureRegion[]{
                ImageCache.getFrame("rabbit", 1), 
				ImageCache.getFrame("rabbit", 2), 
                ImageCache.getFrame("rabbit", 3), 
                ImageCache.getFrame("rabbit", 4), 
                ImageCache.getFrame("rabbit", 5), 
                ImageCache.getFrame("rabbit", 6), 
                ImageCache.getFrame("rabbit", 7), 
                ImageCache.getFrame("rabbit", 8), 
                ImageCache.getFrame("rabbit", 9), 
                ImageCache.getFrame("rabbit", 10), 
				});
		
		this.upAnim = new GameAnimation(0.2f, new TextureRegion[]{
                ImageCache.getFrame("rabbit", 1), 
				});

		this.rightAnim = new GameAnimation(0.2f, new TextureRegion[]{
                ImageCache.getFrame("rabbit", 11), 
				ImageCache.getFrame("rabbit", 12), 
                ImageCache.getFrame("rabbit", 13), 
                ImageCache.getFrame("rabbit", 14), 
                ImageCache.getFrame("rabbit", 15), 
                ImageCache.getFrame("rabbit", 16), 
                ImageCache.getFrame("rabbit", 17), 
                ImageCache.getFrame("rabbit", 18), 
                ImageCache.getFrame("rabbit", 19), 
                ImageCache.getFrame("rabbit", 20), 
				});
		
		this.currentWalkAnim = this.upAnim;
		facePicture = ImageCache.getTexture("rabbit_1");
		
		messages.add("Hop");
	}
}
