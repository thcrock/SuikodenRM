package scripting;

import gamestate.Scriptable;
import com.orangeegames.suikorm.SuikodenRM;

public class ShowHelpPrompt extends Action {
    public ShowHelpPrompt() {
        needsWait = false;
    }

    public void perform(Scriptable scriptable) {
        scriptable.sayMessage(SuikodenRM.gsm.getHelpPrompt(), null, null);
    }
}
