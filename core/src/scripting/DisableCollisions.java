package scripting;

import java.lang.String;
import gamestate.Scriptable;


public class DisableCollisions extends Action {
    public DisableCollisions() {}

    public void perform(Scriptable scriptable) {
        scriptable.disableCollisions();
    }
}
