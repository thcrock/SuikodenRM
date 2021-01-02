package scripting;

import gamestate.Scriptable;

public class MoveToBackground extends Action {
    public MoveToBackground() {
        needsWait = false;
    }

    public void perform(Scriptable scriptable) {
        scriptable.moveToBackground();
    }
}

