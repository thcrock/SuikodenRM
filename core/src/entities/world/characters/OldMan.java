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


public class OldMan extends GameWorldCharacter {

	public OldMan(TextureRegion firstFrame, BoxWorld bw, float x, float y) {
		super(firstFrame, bw, x, y);
		
		name = "OldMan";
		
		this.downAnim = new GameAnimation(0.2f, new TextureRegion[]{
				ImageCache.getFrame("oldman", 3), 
                ImageCache.getFrame("oldman", 7), 
				ImageCache.getFrame("oldman", 11), 
				});
		
		this.leftAnim = new GameAnimation(0.2f, new TextureRegion[]{
				ImageCache.getFrame("oldman", 4), 
                ImageCache.getFrame("oldman", 8), 
				ImageCache.getFrame("oldman", 12), 
				});
		
		this.upAnim = new GameAnimation(0.2f, new TextureRegion[]{
				ImageCache.getFrame("oldman", 1), 
                ImageCache.getFrame("oldman", 5), 
				ImageCache.getFrame("oldman", 9), 
				});

		this.rightAnim = new GameAnimation(0.2f, new TextureRegion[]{
				ImageCache.getFrame("oldman", 2), 
                ImageCache.getFrame("oldman", 6), 
				ImageCache.getFrame("oldman", 10), 
				});
		
		this.currentWalkAnim = this.downAnim;
		facePicture = ImageCache.getTexture("oldman_7");
		
		messages.add("You've got it all wrong. The game is too weak these days. Belcoot would take them all.");
        messages.add("Fresh bread! Fresh bread!");
        messages.add("Mmmmm, barnfish..");
        messages.add("I saw a young lady in blue come by earlier today. She looked like she was up to no good. Watch out, kids!");
	}
}
