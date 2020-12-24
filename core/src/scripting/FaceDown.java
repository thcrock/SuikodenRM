package scripting;

import gamestate.Scriptable;

public class FaceDown extends Action {
    public FaceDown() {
        needsWait = false;
    }

    public void perform(Scriptable scriptable) {
        scriptable.setFaceDown();
    }
}

