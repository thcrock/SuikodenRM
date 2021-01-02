package scripting;

import gamestate.Scriptable;
import com.orangeegames.suikorm.SuikodenRM;

public class SetBgMusic extends Action {
    public String musicTrackName;
    public SetBgMusic() {
        needsWait = false;
    }

    public void perform(Scriptable scriptable) {
        SuikodenRM.gsm.setBackgroundMusic(musicTrackName);
    }
}

