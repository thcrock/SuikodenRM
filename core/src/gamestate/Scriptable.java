package gamestate;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;


public interface Scriptable {
    public String getName();
	public List<String> getMessages();
	public TextureRegion getFacePicture();
    public void startScript();
    public void stopScript();
    public boolean hasFinishedAction();
	public boolean isLeft();
	public boolean isRight();
	public boolean isUp();
	public boolean isDown();
	public Vector2 getPosition();
	public float getSpeed();
    public void moveRight(float distance, float speed);
    public void moveLeft(float distance, float speed);
    public void moveUp(float distance, float speed);
    public void moveDown(float distance, float speed);
    public void setFaceRight();
    public void setFaceLeft();
    public void setFaceUp();
    public void setFaceDown();
    public void moveToX(Scriptable character, float xOffset, float speed);
    public void moveToY(Scriptable character, float yOffset, float speed);
    public void pauseFor(float seconds);
    public void animationFrame(String textureName, int index);
    public void sayMessage(String message, String speakerOverrideName, String speakerOverridePicture);
    public void decoupleMovementAndAnimation();
    public void coupleMovementAndAnimation();
    public void giveChoices(String[] choices);
    public int getCurrentChoice();
    public void setCurrentChoice(int choice);
    public void hasFinishedTalking();
    public void hide();
    public void unhide();
    public void disableCollisions();
    public void enableCollisions();
    public void detachFrom(Scriptable character);
    public void attachTo(Scriptable character);
}
