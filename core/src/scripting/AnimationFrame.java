package scripting;

import java.lang.String;
import gamestate.Scriptable;


public class AnimationFrame extends Action {
    public String textureName;
    public int index;

    public boolean needsWait = false;

    public void perform(Scriptable scriptable) {
        scriptable.animationFrame(textureName, index);
    }
}
