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
import com.kyper.yarn.Dialogue;
import com.kyper.yarn.Dialogue.CommandResult;
import com.kyper.yarn.Dialogue.LineResult;
import com.kyper.yarn.Dialogue.OptionResult;
import com.kyper.yarn.Dialogue.NodeCompleteResult;
import com.kyper.yarn.Dialogue.Options;
import com.kyper.yarn.DialogueData;


public class Conversation {
    boolean started = false;
    boolean over = false;
    Script script;
    int currentActionIndex = -1;
    boolean waiting = false;
    HashMap<String, Scriptable> characters;
    HashSet<Scriptable> usedCharacters;
    Dialogue dialogue;
    //class variables
    LineResult line = null;
    OptionResult option = null;
    NodeCompleteResult node_complete = null;

    public Conversation(String convoName) {
        DialogueData data = new DialogueData(convoName);
        dialogue = new Dialogue(data);
        dialogue.loadFile("scripts/" + convoName + ".json",false,false,null);
    }

    public void initialize(ArrayList<GameWorldCharacter> inputcharacters, Player player) {
        characters = new HashMap<String, Scriptable>();
        for (Scriptable s : inputcharacters) {
            characters.put(s.getName(), s);
        }
        characters.put("Camila", player);
        usedCharacters = new HashSet<Scriptable>();
        /*for (Action a : script.actions) {
            usedCharacters.add(characters.get(a.character));
        }*/
    }

    public void update(float delta) {
        if(dialogue.isRunning() == false && !started){
            System.out.println("Starting");
            started = true;
            dialogue.start(); 
            for (Scriptable s : usedCharacters) {
                s.startScript();
            }
        }

		//in your update method-----

		//check if next result is a command
		if(dialogue.isNextCommand()){
			CommandResult command = dialogue.getNextAsCommand();
			//arbitrary code to execute command
			//here well just print it out to console
			Gdx.app.log("Command:",command.getCommand());
		}
        /*
        System.out.println("checking");
        if(dialogue.isNextLine()) {
            System.out.println("is line");
			 System.out.println(dialogue.getNextAsLine().getText()); 
        } else if (dialogue.isNextOptions()) {
            System.out.println("is options");
            OptionResult or = dialogue.getNextAsOptions();
            for(String s : or.getOptions()) {
                System.out.println(s);
            }
            or.choose(1);
        } else {
            System.out.println("is else");
            System.out.println(dialogue.getNext());
        }
        */

        Scriptable player = characters.get("Camila");
        if(waiting && option != null) {
            if(player.getCurrentChoice() != -1) {
                option.choose(player.getCurrentChoice());
                option = null;
                waiting = false;
            }
        }

		//check that the current line is null and that the next result is a line
		if(line == null && dialogue.isNextLine()){
            System.out.println("got line!");
			line = dialogue.getNextAsLine(); //get the next result as a line
            node_complete = null;
		}
        else if(option == null && dialogue.isNextOptions()){
	        //check that there currently is no options and next result is options
			option = dialogue.getNextAsOptions();
            node_complete = null;
            System.out.println(option);
	    }

	   //check that there is no line and next result is node compelte
	   //usually this means there is nothing executing
        if(line != null) {
            player.sayMessage(line.getText(), "Camila");            
            line = null;
        } else if(option != null) {
            System.out.println("choices");
            String[] items = new String[option.getOptions().size];
            int i = 0;
            for (String s: option.getOptions()) {
                items[i] = s;
                i++;
            }
            player.giveChoices(items);
            waiting = true;
        }
        if(line == null && option == null) {
            if(node_complete != null) {
                dialogue.stop(); //stop the dialogue if it is already not stopped

                for (Scriptable s : usedCharacters) {
                    s.stopScript();
                }
                over = true;
            } else if(dialogue.isNextComplete()) {
                node_complete = dialogue.getNextAsComplete();
            }
        }

        if(over) {
            return;
        }
        /*if(waiting) {
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
		*/
    }
}
