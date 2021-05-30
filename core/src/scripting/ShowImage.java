package scripting;

import gamestate.Scriptable;
import com.orangeegames.suikorm.SuikodenRM;

public class ShowImage extends Action {
    public String imageName;
    public ShowImage() {
        needsWait = false;
    }

    public void perform(Scriptable scriptable) {
        SuikodenRM.gsm.setImageState(this.imageName);
    }
}

