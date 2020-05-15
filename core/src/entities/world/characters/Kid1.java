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


public class Kid1 extends GameWorldCharacter {

	public Kid1(TextureRegion firstFrame, BoxWorld bw, float x, float y) {
		super(firstFrame, bw, x, y);
		
		name = "Kid1";
		
		this.downAnim = new GameAnimation(0.2f, new TextureRegion[]{
				ImageCache.getFrame("kid1", 3), 
                ImageCache.getFrame("kid1", 7), 
				ImageCache.getFrame("kid1", 11), 
				});
		
		this.leftAnim = new GameAnimation(0.2f, new TextureRegion[]{
				ImageCache.getFrame("kid1", 4), 
                ImageCache.getFrame("kid1", 8), 
				ImageCache.getFrame("kid1", 12), 
				});
		
		this.upAnim = new GameAnimation(0.2f, new TextureRegion[]{
				ImageCache.getFrame("kid1", 1), 
                ImageCache.getFrame("kid1", 5), 
				ImageCache.getFrame("kid1", 9), 
				});

		this.rightAnim = new GameAnimation(0.2f, new TextureRegion[]{
				ImageCache.getFrame("kid1", 2), 
                ImageCache.getFrame("kid1", 6), 
				ImageCache.getFrame("kid1", 10), 
				});
        System.out.println(this.downAnim);
		
		this.currentWalkAnim = this.downAnim;
		facePicture = ImageCache.getTexture("kid1_07");
		
		messages.add("The mangos are great today!");
        messages.add("Can I interest you in some mangos?");
        messages.add("");
	}
}
