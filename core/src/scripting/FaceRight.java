package scripting;

import gamestate.Scriptable;

public class FaceRight extends Action {
    public FaceRight() {
        needsWait = false;
    }

    public void perform(Scriptable scriptable) {
        scriptable.setFaceRight();
    }
}

