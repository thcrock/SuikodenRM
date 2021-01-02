package scripting;

import gamestate.Scriptable;

public class MoveToForeground extends Action {
    public MoveToForeground() {
        needsWait = false;
    }

    public void perform(Scriptable scriptable) {
        scriptable.moveToForeground();
    }
}

