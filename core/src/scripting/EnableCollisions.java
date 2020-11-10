package scripting;

import java.lang.String;
import gamestate.Scriptable;

public class EnableCollisions extends Action {
    public EnableCollisions() {}

    public void perform(Scriptable scriptable) {
        scriptable.enableCollisions();
    }
}
