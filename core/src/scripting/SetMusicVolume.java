package scripting;

import gamestate.Scriptable;
import com.orangeegames.suikorm.SuikodenRM;

public class SetMusicVolume extends Action {
    public float volume;
    public SetMusicVolume() {
        needsWait = false;
    }

    public void perform(Scriptable scriptable) {
        SuikodenRM.gsm.setMusicVolume(volume);
    }
}

