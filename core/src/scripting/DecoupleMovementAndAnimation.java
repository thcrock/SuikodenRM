package scripting;

import gamestate.Scriptable;


public class DecoupleMovementAndAnimation extends Action {
    public DecoupleMovementAndAnimation() {}

    public void perform(Scriptable scriptable) {
        scriptable.decoupleMovementAndAnimation();
    }
}

