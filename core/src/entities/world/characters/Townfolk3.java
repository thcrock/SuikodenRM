
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


public class Townfolk3 extends GameWorldCharacter {

	public Townfolk3(TextureRegion firstFrame, BoxWorld bw, float x, float y) {
		super(firstFrame, bw, x, y);
		
		name = "Townfolk3";
		
		this.downAnim = new GameAnimation(0.2f, new TextureRegion[]{
                ImageCache.getFrame("townfolk03", 7), 
				ImageCache.getFrame("townfolk03", 3), 
				ImageCache.getFrame("townfolk03", 11), 
				});
		
		this.leftAnim = new GameAnimation(0.2f, new TextureRegion[]{
                ImageCache.getFrame("townfolk03", 8), 
				ImageCache.getFrame("townfolk03", 4), 
				ImageCache.getFrame("townfolk03", 12), 
				});
		
		this.upAnim = new GameAnimation(0.2f, new TextureRegion[]{
                ImageCache.getFrame("townfolk03", 5), 
				ImageCache.getFrame("townfolk03", 1), 
				ImageCache.getFrame("townfolk03", 9), 
				});

		this.rightAnim = new GameAnimation(0.2f, new TextureRegion[]{
                ImageCache.getFrame("townfolk03", 6), 
				ImageCache.getFrame("townfolk03", 2), 
				ImageCache.getFrame("townfolk03", 10), 
				});
		
		this.currentWalkAnim = this.rightAnim;
		facePicture = ImageCache.getTexture("townfolk03_07");
		
		messages.add("Valeria is the best! She can smoke all of them");
	}
}
