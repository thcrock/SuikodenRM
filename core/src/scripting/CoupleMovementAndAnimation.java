package scripting;

import gamestate.Scriptable;


public class CoupleMovementAndAnimation extends Action {
    public CoupleMovementAndAnimation() {}

    public void perform(Scriptable scriptable) {
        scriptable.coupleMovementAndAnimation();
    }
}

