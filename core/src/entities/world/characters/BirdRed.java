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


public class BirdRed extends GameWorldCharacter {

	public BirdRed(TextureRegion firstFrame, BoxWorld bw, float x, float y) {
		super(firstFrame, bw, x, y);
		
		name = "BirdRed";
		
		this.downAnim = new GameAnimation(0.2f, new TextureRegion[]{
                ImageCache.getFrame("bird_red", 7), 
				ImageCache.getFrame("bird_red", 8), 
                ImageCache.getFrame("bird_red", 7), 
                ImageCache.getFrame("bird_red", 9), 
				});
		
		this.leftAnim = new GameAnimation(0.2f, new TextureRegion[]{
                ImageCache.getFrame("bird_red", 1), 
				ImageCache.getFrame("bird_red", 2), 
                ImageCache.getFrame("bird_red", 1), 
                ImageCache.getFrame("bird_red", 3), 
				});
		
		this.upAnim = new GameAnimation(0.2f, new TextureRegion[]{
                ImageCache.getFrame("bird_red", 4), 
				ImageCache.getFrame("bird_red", 5), 
                ImageCache.getFrame("bird_red", 4), 
                ImageCache.getFrame("bird_red", 6), 
				});

		this.rightAnim = new GameAnimation(0.2f, new TextureRegion[]{
                ImageCache.getFrame("bird_red", 10), 
				ImageCache.getFrame("bird_red", 11), 
                ImageCache.getFrame("bird_red", 10), 
                ImageCache.getFrame("bird_red", 12), 
				});
		
		this.currentWalkAnim = this.upAnim;
		facePicture = ImageCache.getTexture("bird_red_8");
		
		messages.add("Squawk");
	}
}
