package scripting;

import gamestate.Scriptable;

public class DetachFrom extends Action {
    public Scriptable attachedCharacter;

    public DetachFrom() {
        needsWait = false;
    }

    public void perform(Scriptable scriptable) {
        scriptable.detachFrom(attachedCharacter);
    }
}

