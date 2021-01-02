package entities;


import net.dermetfan.gdx.graphics.g2d.Box2DSprite;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;



public abstract class DrawableBox2D extends Box2DSprite implements Comparable<DrawableBox2D> {
 
        Body body;
        boolean hidden = false;
        boolean inBackground = false;
        boolean inForeground = false;
       
        public abstract void interact(Player player);
	    public void update(float delta) {}
       
        public DrawableBox2D(TextureRegion initialSprite) {
        	super(initialSprite);
        	setAdjustHeight(false);
        }
       
        public void draw(Batch spriteBatch) {
            if(hidden) {
                System.out.println("Character is hidden, not rendering");
                return;
            }
        	this.draw(spriteBatch, body);
        }

        public void moveToBackground() {
            this.inBackground = true;
            this.inForeground = false;
        }

        public void moveToForeground() {
            this.inForeground = true;
            this.inBackground = false;
        }

        public void resetSorting() {
            this.inForeground = false;
            this.inBackground = false;
        }

        public void hide() {
            hidden = true;
        }

        public void unhide() {
            hidden = false;
        }
       
        @Override
        public int compareTo(DrawableBox2D arg0) {
            if (inBackground) {
                return -1;
            } else if (inForeground) {
                return 1;
            } else {
                return Float.compare(arg0.body.getPosition().y, this.body.getPosition().y);
            }
        }
        
        public Body getBody() {
        	return body;
        }

}

