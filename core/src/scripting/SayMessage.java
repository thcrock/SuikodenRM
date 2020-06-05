package scripting;

import gamestate.Scriptable;
import java.lang.String;


public class SayMessage extends Action {
    public String message;
    public String speakerOverrideName;

    public SayMessage() {
        needsWait = true;
    }
    public void perform(Scriptable scriptable) {
        scriptable.sayMessage(message, speakerOverrideName);
    }
}
