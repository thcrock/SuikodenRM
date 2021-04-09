
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


public class Lorelai extends GameWorldCharacter {

	public Lorelai(TextureRegion firstFrame, BoxWorld bw, float x, float y) {
		super(firstFrame, bw, x, y);
		
		name = "Lorelai";
		
		this.downAnim = new GameAnimation(0.2f, new TextureRegion[]{
				ImageCache.getFrame("lorelai", 2), 
                ImageCache.getFrame("lorelai", 1), 
				ImageCache.getFrame("lorelai", 2), 
				ImageCache.getFrame("lorelai", 3), 
				});
		
		this.leftAnim = new GameAnimation(0.2f, new TextureRegion[]{
				ImageCache.getFrame("lorelai", 8), 
                ImageCache.getFrame("lorelai", 7), 
				ImageCache.getFrame("lorelai", 8), 
				ImageCache.getFrame("lorelai", 9), 
				});
		
		this.upAnim = new GameAnimation(0.2f, new TextureRegion[]{
				ImageCache.getFrame("lorelai", 5), 
                ImageCache.getFrame("lorelai", 4), 
				ImageCache.getFrame("lorelai", 5), 
				ImageCache.getFrame("lorelai", 6), 
				});

		TextureRegion walkRight1 = 	new TextureRegion(ImageCache.getFrame("lorelai", 1));
		TextureRegion walkRight2 =  new TextureRegion(ImageCache.getFrame("lorelai", 2));
		TextureRegion walkRight3 =  new TextureRegion(ImageCache.getFrame("lorelai", 3));
		
		walkRight1.flip(true, false);
		walkRight2.flip(true, false);
		walkRight3.flip(true, false);
		
		this.rightAnim = new GameAnimation(0.2f, new TextureRegion[]{
				walkRight2, 
				walkRight1, 
				walkRight2, 
				walkRight3});
        System.out.println(this.downAnim);
		
		this.currentWalkAnim = this.downAnim;
		facePicture = ImageCache.getTexture("lorelai_1");
		
		messages.add("...");
	}
}
