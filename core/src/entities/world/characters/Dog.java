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


public class Dog extends GameWorldCharacter {

	public Dog(TextureRegion firstFrame, BoxWorld bw, float x, float y) {
		super(firstFrame, bw, x, y);
		
		name = "Dog";
		
		this.downAnim = new GameAnimation(0.2f, new TextureRegion[]{
				ImageCache.getFrame("dog_run", 1), 
				});
		
		this.leftAnim = new GameAnimation(0.2f, new TextureRegion[]{
				ImageCache.getFrame("dog_run", 1), 
				ImageCache.getFrame("dog_run", 2), 
				ImageCache.getFrame("dog_run", 3), 
				ImageCache.getFrame("dog_run", 5), 
				});
		
		this.upAnim = new GameAnimation(0.2f, new TextureRegion[]{
				ImageCache.getFrame("dog_run", 1), 
				});

		TextureRegion walkRight1 = 	new TextureRegion(ImageCache.getFrame("dog_run", 1));
		TextureRegion walkRight2 =  new TextureRegion(ImageCache.getFrame("dog_run", 2));
		TextureRegion walkRight3 =  new TextureRegion(ImageCache.getFrame("dog_run", 3));
		TextureRegion walkRight4 =  new TextureRegion(ImageCache.getFrame("dog_run", 5));
		
		walkRight1.flip(true, false);
		walkRight2.flip(true, false);
		walkRight3.flip(true, false);
		walkRight4.flip(true, false);
		
		this.rightAnim = new GameAnimation(0.2f, new TextureRegion[]{
				walkRight1, 
				walkRight2, 
				walkRight3, 
				walkRight4});
		
		this.currentWalkAnim = this.leftAnim;
		facePicture = ImageCache.getTexture("dog_run_4");
		
		messages.add("Ruff!");
	}
}
