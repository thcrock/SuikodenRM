package gamestate;


public interface Scriptable {
    public void startScript();
    public void stopScript();
    public boolean hasFinishedAction();
    public void moveRight(int distance, float speed);
    public void moveLeft(int distance, float speed);
    public void moveUp(int distance, float speed);
    public void moveDown(int distance, float speed);
    public void pauseFor(float seconds);
    public void animationFrame(String textureName, int index);
    public void sayMessage(String message);
}
