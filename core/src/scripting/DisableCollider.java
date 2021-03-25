package scripting;

import java.lang.String;
import gamestate.Scriptable;
import com.orangeegames.suikorm.SuikodenRM;


public class DisableCollider extends Action {
    public String tagToDisable;
    public DisableCollider() {
        needsWait = false;
    }

    public void perform(Scriptable scriptable) {
        SuikodenRM.gsm.disableCollidersWithTag(tagToDisable);
    }
}
