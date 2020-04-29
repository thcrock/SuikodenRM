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


public class Townfolk2 extends GameWorldCharacter {

	public Townfolk2(TextureRegion firstFrame, BoxWorld bw, float x, float y) {
		super(firstFrame, bw, x, y);
		
		name = "Townfolk2";
		
		this.downAnim = new GameAnimation(0.2f, new TextureRegion[]{
				ImageCache.getFrame("townfolk02", 3), 
                ImageCache.getFrame("townfolk02", 7), 
				ImageCache.getFrame("townfolk02", 11), 
				});
		
		this.leftAnim = new GameAnimation(0.2f, new TextureRegion[]{
				ImageCache.getFrame("townfolk02", 4), 
                ImageCache.getFrame("townfolk02", 8), 
				ImageCache.getFrame("townfolk02", 12), 
				});
		
		this.upAnim = new GameAnimation(0.2f, new TextureRegion[]{
				ImageCache.getFrame("townfolk02", 1), 
                ImageCache.getFrame("townfolk02", 5), 
				ImageCache.getFrame("townfolk02", 9), 
				});

		this.rightAnim = new GameAnimation(0.2f, new TextureRegion[]{
				ImageCache.getFrame("townfolk02", 2), 
                ImageCache.getFrame("townfolk02", 6), 
				ImageCache.getFrame("townfolk02", 10), 
				});
        System.out.println(this.downAnim);
		
		this.currentWalkAnim = this.downAnim;
		facePicture = ImageCache.getTexture("townfolk02_07");
		
		messages.add("How's your mom?");
        messages.add("I have a confession. I hate mangoes!?");
	}
}
