package scripting;

import gamestate.Scriptable;


public class MoveRight extends Action{
    public float speed;
    public int distance;

    public MoveRight() {
        needsWait = true;
    }

    public void perform(Scriptable scriptable) {
        scriptable.moveRight(distance, speed);
    }
}
