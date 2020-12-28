package scripting;

import gamestate.Scriptable;
import com.orangeegames.suikorm.SuikodenRM;

public class SetHelpPrompt extends Action {
    public String helpPrompt;
    public SetHelpPrompt() {
        needsWait = false;
    }

    public void perform(Scriptable scriptable) {
        SuikodenRM.gsm.setHelpPrompt(helpPrompt);
    }
}

