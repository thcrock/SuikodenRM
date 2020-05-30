package entities;

import gamestate.Scriptable;

import java.util.ArrayList;
import java.util.HashMap;
import scripting.Script;
import scripting.Action;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;


public class StoryScene {
    Scriptable character;
    boolean started = false;
    boolean over = false;
    Script script;
    int currentActionIndex = -1;
    boolean waiting = false;
    public StoryScene(Scriptable theScripted) {
        character = theScripted;
        Json json = new Json();
        script = json.fromJson(Script.class, Gdx.files.internal("scripts/testScript.json"));
    }

    public void update(float delta) {
        if(over) {
            return;
        }
        if(!started) {
            System.out.println("started script");
            character.startScript();
            started = true;
        }
        if(waiting) {
            if(character.hasFinishedAction()) {
                System.out.println("finished action");
                waiting = false;
            }
        }
        if(!waiting) {
            System.out.println("not waiting, going to next action");
            currentActionIndex++;
            if(currentActionIndex >= script.actions.size()) {
                System.out.println("stopping script");
                character.stopScript();
                over = true;
            } else {
                Action action = script.actions.get(currentActionIndex);
                System.out.println("performing" + action);
                action.perform(character);
                if(action.needsWait) {
                    System.out.println("beginning wait");
                    waiting = true;
                }
            }
        }
    }
}
