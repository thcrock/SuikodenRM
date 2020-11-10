package scripting;

import gamestate.Scriptable;

public class AttachTo extends Action {
    public Scriptable attachedCharacter;

    public AttachTo() {
        needsWait = false;
    }

    public void perform(Scriptable scriptable) {
        scriptable.attachTo(attachedCharacter);
    }
}

