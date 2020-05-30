package scripting;

import gamestate.Scriptable;

public class MoveDown extends Action {
    public float speed;
    public int distance;

    public MoveDown() {
        needsWait = true;
    }

    public void perform(Scriptable scriptable) {
        scriptable.moveDown(distance, speed);
    }
}

