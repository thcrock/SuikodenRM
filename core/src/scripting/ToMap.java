package scripting;

import java.lang.String;
import gamestate.Scriptable;
import entities.Door;
import com.orangeegames.suikorm.SuikodenRM;


public class ToMap extends Action {
    public String toName;
    public int toSpawnNumber;
    public ToMap() {}


    public void perform(Scriptable scriptable) {
        Door newDoor = new Door(toName, toSpawnNumber);
        System.out.println("change world");
        SuikodenRM.gsm.changeWorld(newDoor);
    }
}
