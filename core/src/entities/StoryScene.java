package entities;

import gamestate.Scriptable;

import java.util.ArrayList;
import java.util.HashMap;


public class StoryScene {
    Scriptable character;
    boolean firstOneTriggered = false;
    boolean secondOneTriggered = false;
    boolean thirdOneTriggered = false;
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
            character.animationFrame("townfolk03", 1);
            character.pauseFor(2.0f);
        }
        if(secondOneTriggered && !thirdOneTriggered && character.hasFinishedAction()) {
            thirdOneTriggered = true;
            character.moveLeft(50, 10);
        }
    }
}
