package scripting;

import java.lang.String;
import gamestate.Scriptable;


public class Hide extends Action {
    public Hide() {}

    public void perform(Scriptable scriptable) {
        scriptable.hide();
    }
}
