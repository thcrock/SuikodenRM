package scripting;

import java.lang.String;
import gamestate.Scriptable;


public class UnHide extends Action {
    public UnHide() {}

    public void perform(Scriptable scriptable) {
        scriptable.unhide();
    }
}
