package scripting;

import gamestate.Scriptable;


public class MoveUp extends Action {
    public float speed;
    public int distance;

    public MoveUp() {
        needsWait = true;
    }

    public void perform(Scriptable scriptable) {
        scriptable.moveUp(distance, speed);
    }
}

