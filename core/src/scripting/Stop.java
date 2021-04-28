package scripting;

import java.lang.String;
import gamestate.Scriptable;


public class Stop extends Action {
    public Stop() {
        needsWait = false;
    }

    public void perform(Scriptable scriptable) {
        scriptable.stop();
    }
}
