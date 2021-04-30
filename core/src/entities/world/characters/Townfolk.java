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


public class Townfolk extends GameWorldCharacter {

	public Townfolk(TextureRegion firstFrame, BoxWorld bw, float x, float y, String name, String sprite) {
		super(firstFrame, bw, x, y);
		
		this.name = name;
		
		this.downAnim = new GameAnimation(0.2f, new TextureRegion[]{
				ImageCache.getFrame(sprite, 8), 
                ImageCache.getFrame(sprite, 7), 
				ImageCache.getFrame(sprite, 8), 
				ImageCache.getFrame(sprite, 9), 
				});
		
		this.leftAnim = new GameAnimation(0.2f, new TextureRegion[]{
				ImageCache.getFrame(sprite, 11), 
                ImageCache.getFrame(sprite, 10), 
				ImageCache.getFrame(sprite, 11), 
				ImageCache.getFrame(sprite, 12), 
				});
		
		this.upAnim = new GameAnimation(0.2f, new TextureRegion[]{
				ImageCache.getFrame(sprite, 2), 
                ImageCache.getFrame(sprite, 1), 
				ImageCache.getFrame(sprite, 2), 
				ImageCache.getFrame(sprite, 3), 
				});

		this.rightAnim = new GameAnimation(0.2f, new TextureRegion[]{
				ImageCache.getFrame(sprite, 5), 
                ImageCache.getFrame(sprite, 4), 
				ImageCache.getFrame(sprite, 5), 
				ImageCache.getFrame(sprite, 6), 
				});
		
		this.currentWalkAnim = this.downAnim;
		facePicture = ImageCache.getTexture(sprite + "_7");
	}
}
