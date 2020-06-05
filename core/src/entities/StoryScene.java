package entities;

import gamestate.Scriptable;

import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import scripting.Script;
import scripting.Action;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;


public class StoryScene {
    boolean started = false;
    boolean over = false;
    Script script;
    int currentActionIndex = -1;
    boolean waiting = false;
    HashMap<String, Scriptable> characters;
    HashSet<Scriptable> usedCharacters;

    public StoryScene(String scriptName) {
        Json json = new Json();
        script = json.fromJson(Script.class, Gdx.files.internal("scripts/" + scriptName + ".json"));
    }

    public void initialize(ArrayList<GameWorldCharacter> inputcharacters, Player player) {
        characters = new HashMap<String, Scriptable>();
        for (Scriptable s : inputcharacters) {
            characters.put(s.getName(), s);
        }
        characters.put("Camila", player);
        usedCharacters = new HashSet<Scriptable>();
        for (Action a : script.actions) {
            usedCharacters.add(characters.get(a.character));
        }
    }

    public void update(float delta) {
        if(over) {
            return;
        }
        if(!started) {
            started = true;
            for (Scriptable s : usedCharacters) {
                s.startScript();
            }
        }
        if(waiting) {
            if(characters.get(script.actions.get(currentActionIndex).character).hasFinishedAction()) {
                waiting = false;
            } else {
            }
        }
        if(!waiting) {
            currentActionIndex++;
            if(currentActionIndex >= script.actions.size()) {
                for (Scriptable s : usedCharacters) {
                    s.stopScript();
                }
                over = true;
            } else {
                Action action = script.actions.get(currentActionIndex);
                System.out.println("Starting new action" + action);
                action.perform(characters.get(action.character));
                if(action.needsWait) {
                    waiting = true;
                }
            }
        }
    }
}
