package scripting;

import java.lang.String;
import gamestate.Scriptable;
import com.orangeegames.suikorm.SuikodenRM;


public class HideLayer extends Action {
    public String tagToHide;
    public HideLayer() {
        needsWait = false;
    }

    public void perform(Scriptable scriptable) {
        SuikodenRM.gsm.hideLayersWithTag(tagToHide);
    }
}
