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


public class Townfolk4 extends GameWorldCharacter {

	public Townfolk4(TextureRegion firstFrame, BoxWorld bw, float x, float y) {
		super(firstFrame, bw, x, y);
		
		name = "Townfolk4";
		
		this.downAnim = new GameAnimation(0.2f, new TextureRegion[]{
                ImageCache.getFrame("townfolk04", 7), 
				ImageCache.getFrame("townfolk04", 3), 
                ImageCache.getFrame("townfolk04", 7), 
				ImageCache.getFrame("townfolk04", 11), 
				});
		
		this.leftAnim = new GameAnimation(0.2f, new TextureRegion[]{
                ImageCache.getFrame("townfolk04", 8), 
				ImageCache.getFrame("townfolk04", 4), 
                ImageCache.getFrame("townfolk04", 8), 
				ImageCache.getFrame("townfolk04", 12), 
				});
		
		this.upAnim = new GameAnimation(0.2f, new TextureRegion[]{
                ImageCache.getFrame("townfolk04", 5), 
				ImageCache.getFrame("townfolk04", 1), 
                ImageCache.getFrame("townfolk04", 5), 
				ImageCache.getFrame("townfolk04", 9), 
				});

		this.rightAnim = new GameAnimation(0.2f, new TextureRegion[]{
                ImageCache.getFrame("townfolk04", 6), 
				ImageCache.getFrame("townfolk04", 2), 
                ImageCache.getFrame("townfolk04", 6), 
				ImageCache.getFrame("townfolk04", 10), 
				});
		
		this.currentWalkAnim = this.upAnim;
		facePicture = ImageCache.getTexture("townfolk04_07");
		
		messages.add("Belcoot couldn't survive in today's game. Anita is the best!");
	}
}
