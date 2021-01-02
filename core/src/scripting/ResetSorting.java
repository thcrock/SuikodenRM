package scripting;

import gamestate.Scriptable;

public class ResetSorting extends Action {
    public ResetSorting() {
        needsWait = false;
    }

    public void perform(Scriptable scriptable) {
        scriptable.resetSorting();
    }
}

