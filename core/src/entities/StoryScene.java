package entities;

import gamestate.Scriptable;

import java.util.ArrayList;
import java.util.HashMap;


public class StoryScene {
    Scriptable character;
    boolean firstOneTriggered = false;
    boolean secondOneTriggered = false;
    boolean thirdOneTriggered = false;
    boolean over = false;
    public StoryScene(Scriptable theScripted) {
        character = theScripted;
    }
    public void update(float delta) {
        if(!firstOneTriggered) {
            character.startScript();
            firstOneTriggered = true;
            character.moveRight(50, 10);
        }
        if(firstOneTriggered && !secondOneTriggered && character.hasFinishedAction()) {
            secondOneTriggered = true;
            character.sayMessage("howdy!");
        }
        if(secondOneTriggered && !thirdOneTriggered && character.hasFinishedAction()) {
            thirdOneTriggered = true;
            character.animationFrame("townfolk03", 1);
            character.pauseFor(1.0f);
        }
        if(thirdOneTriggered && character.hasFinishedAction() && !over) {
            System.out.println("done with script!");
            over = true;
            character.stopScript();
        }
    }
}
