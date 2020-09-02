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
				ImageCache.getFrame("townfolk01", 7), 
                ImageCache.getFrame("townfolk01", 8), 
				ImageCache.getFrame("townfolk01", 9), 
				});
		
		this.leftAnim = new GameAnimation(0.2f, new TextureRegion[]{
				ImageCache.getFrame("townfolk01", 10), 
                ImageCache.getFrame("townfolk01", 11), 
				ImageCache.getFrame("townfolk01", 12), 
				});
		
		this.upAnim = new GameAnimation(0.2f, new TextureRegion[]{
				ImageCache.getFrame("townfolk01", 1), 
                ImageCache.getFrame("townfolk01", 2), 
				ImageCache.getFrame("townfolk01", 3), 
				});

		this.rightAnim = new GameAnimation(0.2f, new TextureRegion[]{
				ImageCache.getFrame("townfolk01", 4), 
                ImageCache.getFrame("townfolk01", 5), 
				ImageCache.getFrame("townfolk01", 6), 
				});
        System.out.println(this.downAnim);
		
		this.currentWalkAnim = this.downAnim;
		facePicture = ImageCache.getTexture("townfolk01_07");
		
		messages.add("The mangos are great today!");
        messages.add("Can I interest you in some mangos?");
        messages.add("The roses are in bloom! Have you seen them?");
        messages.add("Everyone keeps trying to take our fruit without paying! Freeloaders!");
	}
}
