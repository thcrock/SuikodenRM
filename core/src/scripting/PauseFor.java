package scripting;

import java.lang.String;
import gamestate.Scriptable;


public class PauseFor extends Action {
    public float seconds;

    public PauseFor() {
        needsWait = true;
    }

    public void perform(Scriptable scriptable) {
        scriptable.pauseFor(seconds);
    }
}
