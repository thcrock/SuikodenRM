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


public class Fish extends GameWorldCharacter {

	public Fish(TextureRegion firstFrame, BoxWorld bw, float x, float y) {
		super(firstFrame, bw, x, y);
		
		name = "Fish";
		
		this.downAnim = new GameAnimation(0.2f, new TextureRegion[]{
                ImageCache.getFrame("fish", 1), 
				});
		
		this.leftAnim = new GameAnimation(0.2f, new TextureRegion[]{
                ImageCache.getFrame("fish", 1), 
				});
		
		this.upAnim = new GameAnimation(0.2f, new TextureRegion[]{
                ImageCache.getFrame("fish", 1), 
				});

		this.rightAnim = new GameAnimation(0.2f, new TextureRegion[]{
                ImageCache.getFrame("fish", 2), 
				});
		
		this.currentWalkAnim = this.upAnim;
		facePicture = ImageCache.getTexture("fish_1");
		
		messages.add("Glub");
	}
}
