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


public class Townfolk1 extends GameWorldCharacter {

	public Townfolk1(TextureRegion firstFrame, BoxWorld bw, float x, float y) {
		super(firstFrame, bw, x, y);
		
		name = "Townfolk1";
		
		this.downAnim = new GameAnimation(0.2f, new TextureRegion[]{
				ImageCache.getFrame("townfolk0", 6), 
                ImageCache.getFrame("townfolk0", 7), 
				ImageCache.getFrame("townfolk0", 8), 
				});
		
		this.leftAnim = new GameAnimation(0.2f, new TextureRegion[]{
				ImageCache.getFrame("townfolk0", 9), 
                ImageCache.getFrame("townfolk0", 10), 
				ImageCache.getFrame("townfolk0", 11), 
				});
		
		this.upAnim = new GameAnimation(0.2f, new TextureRegion[]{
				ImageCache.getFrame("townfolk0", 0), 
                ImageCache.getFrame("townfolk0", 1), 
				ImageCache.getFrame("townfolk0", 2), 
				});

		this.rightAnim = new GameAnimation(0.2f, new TextureRegion[]{
				ImageCache.getFrame("townfolk0", 3), 
                ImageCache.getFrame("townfolk0", 4), 
				ImageCache.getFrame("townfolk0", 5), 
				});
        System.out.println(this.downAnim);
		
		this.currentWalkAnim = this.downAnim;
		facePicture = ImageCache.getTexture("townfolk0_07");
		
		messages.add("The mangos are great today!");
        messages.add("Can I interest you in some mangos?");
        messages.add("");
	}
}
