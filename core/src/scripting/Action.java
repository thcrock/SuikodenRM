package scripting;


import gamestate.Scriptable;

public abstract class Action {
    public boolean needsWait;
    public abstract void perform(Scriptable scriptable);
}
