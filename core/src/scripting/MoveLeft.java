package scripting;

import gamestate.Scriptable;

public class MoveLeft extends Action {
    public float speed;
    public int distance;

    public MoveLeft() {
        needsWait = true;
    }

    public void perform(Scriptable scriptable) {
        scriptable.moveLeft(distance, speed);
    }
}
