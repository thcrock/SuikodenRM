package scripting;

import gamestate.Scriptable;
import java.lang.String;


public class SayMessage extends Action {
    public String message;

    public void perform(Scriptable scriptable) {
        scriptable.sayMessage(message);
    }
}
