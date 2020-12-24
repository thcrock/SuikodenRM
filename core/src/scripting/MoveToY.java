package scripting;

import gamestate.Scriptable;

public class MoveToY extends Action {
    public Scriptable scriptable;
    public Scriptable target;
    public float yOffset;
    public float speed;

    public MoveToY() {
        needsWait = true;
    }

    public void perform(Scriptable scriptable) {
        scriptable.moveToY(target, yOffset, speed);
    }
}

