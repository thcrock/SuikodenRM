package scripting;

import java.lang.String;
import gamestate.Scriptable;
import com.orangeegames.suikorm.SuikodenRM;


public class ShowLayer extends Action {
    public String tagToShow;
    public ShowLayer() {
        needsWait = false;
    }

    public void perform(Scriptable scriptable) {
        SuikodenRM.gsm.showLayersWithTag(tagToShow);
    }
}
