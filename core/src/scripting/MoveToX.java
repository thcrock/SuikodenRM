package scripting;

import gamestate.Scriptable;

public class MoveToX extends Action {
    public Scriptable scriptable;
    public Scriptable target;
    public float xOffset;
    public float speed;

    public MoveToX() {
        needsWait = true;
    }

    public void perform(Scriptable scriptable) {
        scriptable.moveToX(target, xOffset, speed);
    }
}

