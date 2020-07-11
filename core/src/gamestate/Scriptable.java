package gamestate;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.TextureRegion;


public interface Scriptable {
    public String getName();
	public List<String> getMessages();
	public TextureRegion getFacePicture();
    public void startScript();
    public void stopScript();
    public boolean hasFinishedAction();
    public void moveRight(int distance, float speed);
    public void moveLeft(int distance, float speed);
    public void moveUp(int distance, float speed);
    public void moveDown(int distance, float speed);
    public void pauseFor(float seconds);
    public void animationFrame(String textureName, int index);
    public void sayMessage(String message, String speakerOverrideName);
    public void decoupleMovementAndAnimation();
    public void giveChoices(String[] choices);
    public int getCurrentChoice();
    public void setCurrentChoice(int choice);
    public void hasFinishedTalking();
    public void hide();
    public void unhide();
}
