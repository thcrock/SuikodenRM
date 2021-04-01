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


public class FurFur extends GameWorldCharacter {

	public FurFur(TextureRegion firstFrame, BoxWorld bw, float x, float y) {
		super(firstFrame, bw, x, y);
		
		name = "FurFur";
		
		this.downAnim = new GameAnimation(0.2f, new TextureRegion[]{
                ImageCache.getFrame("furfur", 1), 
				ImageCache.getFrame("furfur", 2), 
				});
		
		this.upAnim = new GameAnimation(0.2f, new TextureRegion[]{
                ImageCache.getFrame("furfur", 1), 
				ImageCache.getFrame("furfur", 2), 
				});

		this.rightAnim = new GameAnimation(0.2f, new TextureRegion[]{
                ImageCache.getFrame("furfur", 1), 
				ImageCache.getFrame("furfur", 2), 
				});
		
		TextureRegion left1 = new TextureRegion(ImageCache.getFrame("furfur", 1));
		TextureRegion left2 =  new TextureRegion(ImageCache.getFrame("furfur", 2));
		
		left1.flip(true, false);
		left2.flip(true, false);
		
		this.leftAnim = new GameAnimation(0.2f, new TextureRegion[]{
				left1, 
				left2});
		this.currentWalkAnim = this.upAnim;
        facePicture = ImageCache.getTexture("furfur_1");

		messages.add("Faboosh");
	}
}
