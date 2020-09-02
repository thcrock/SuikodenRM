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


public class Haia extends GameWorldCharacter {

	public Haia(TextureRegion firstFrame, BoxWorld bw, float x, float y) {
		super(firstFrame, bw, x, y);
		
		name = "Haia";
		
		this.downAnim = new GameAnimation(0.2f, new TextureRegion[]{
				ImageCache.getFrame("haia", 7), 
                ImageCache.getFrame("haia", 8), 
				ImageCache.getFrame("haia", 9), 
				});
		
		this.leftAnim = new GameAnimation(0.2f, new TextureRegion[]{
				ImageCache.getFrame("haia", 10), 
                ImageCache.getFrame("haia", 11), 
				ImageCache.getFrame("haia", 12), 
				});
		
		this.upAnim = new GameAnimation(0.2f, new TextureRegion[]{
				ImageCache.getFrame("haia", 1), 
                ImageCache.getFrame("haia", 2), 
				ImageCache.getFrame("haia", 3), 
				});

		this.rightAnim = new GameAnimation(0.2f, new TextureRegion[]{
				ImageCache.getFrame("haia", 4), 
                ImageCache.getFrame("haia", 5), 
				ImageCache.getFrame("haia", 6), 
				});
        System.out.println(this.downAnim);
		
		this.currentWalkAnim = this.downAnim;
		facePicture = ImageCache.getTexture("haia_07");
		
		messages.add("...");
	}
}
