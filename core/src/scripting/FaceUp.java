package scripting;

import gamestate.Scriptable;

public class FaceUp extends Action {
    public FaceUp() {
        needsWait = false;
    }

    public void perform(Scriptable scriptable) {
        scriptable.setFaceUp();
    }
}

