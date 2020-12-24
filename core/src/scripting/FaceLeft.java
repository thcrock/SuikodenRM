package scripting;

import gamestate.Scriptable;

public class FaceLeft extends Action {
    public FaceLeft() {
        needsWait = false;
    }

    public void perform(Scriptable scriptable) {
        scriptable.setFaceLeft();
    }
}

