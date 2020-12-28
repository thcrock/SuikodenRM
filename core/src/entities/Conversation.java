package entities;

import gamestate.Scriptable;

import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import scripting.Script;
import scripting.Action;
import scripting.MoveRight;
import scripting.MoveUp;
import scripting.MoveLeft;
import scripting.MoveDown;
import scripting.PauseFor;
import scripting.AnimationFrame;
import scripting.DecoupleMovementAndAnimation;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.kyper.yarn.Dialogue;
import com.kyper.yarn.Dialogue.CommandResult;
import com.kyper.yarn.Dialogue.LineResult;
import com.kyper.yarn.Dialogue.OptionResult;
import com.kyper.yarn.Dialogue.NodeCompleteResult;
import com.kyper.yarn.Dialogue.Options;
import com.kyper.yarn.DialogueData;
import com.orangeegames.suikorm.SuikodenRM;


public class Conversation {
    boolean started = false;
    boolean over = false;
    public String name;
    Script script;
    int currentActionIndex = -1;
    boolean waiting = false;
    HashSet<String> scriptTriggers;
    HashSet<String> objectTriggers;
    HashMap<String, Scriptable> characters;
    HashSet<Scriptable> usedCharacters;
    Dialogue dialogue;
    Action currentAction = null;
    //class variables
    LineResult line = null;
    OptionResult option = null;
    NodeCompleteResult node_complete = null;
    CommandResult command = null;

    public Conversation(String convoName, String onlyTriggerIfScript) {
        name = convoName;
        scriptTriggers = new HashSet();
        if(onlyTriggerIfScript != null) {
            String[] trigger = onlyTriggerIfScript.split("\\|");
            for(String t : trigger) {
                scriptTriggers.add(t);
            }
        }
        DialogueData data = new DialogueData(convoName);
        dialogue = new Dialogue(data);
        dialogue.loadFile("scripts/" + convoName + ".json",false,false,null);
    }

    public boolean isOver() {
        return over;
    }

    public HashSet<String> getTriggers() {
        return this.scriptTriggers;
    }

    public HashSet<String> getObjectTriggers() {
        return this.objectTriggers;
    }

    public void initialize(ArrayList<GameWorldCharacter> inputcharacters, Player player) {
        characters = new HashMap<String, Scriptable>();
        System.out.println("initializing");
        for (Scriptable s : inputcharacters) {
            System.out.println(s.getName());
            characters.put(s.getName(), s);
        }
        characters.put("Mia", player);
        usedCharacters = new HashSet<Scriptable>();
        usedCharacters.add(player);
    }

    public void update(float delta) {
        if(over) {
            return;
        }
        if(dialogue.isRunning() == false && !started){
            started = true;
            dialogue.start(); 
            for (Scriptable s : usedCharacters) {
                s.startScript();
            }
        }

        if(waiting && currentAction != null) {
            if(characters.get(currentAction.character).hasFinishedAction()) {
                waiting = false;
                currentAction = null;
                command = null;
            } else {
                return;
            }
        }

		//check if next result is a command
		if(command == null && dialogue.isNextCommand()){
            node_complete = null;
			command = dialogue.getNextAsCommand();
			//arbitrary code to execute command
            String params[] = command.getCommand().split("\\s+");
			for (int i = 0; i < params.length; i++) {
				params[i] = params[i].trim(); // just trim to make sure
			}
            String commandName = params[0];
            if(commandName.equals("moveRight")) {
                scripting.MoveRight action = new scripting.MoveRight();
                action.distance = Integer.parseInt(params[2]);
                action.speed = Float.parseFloat(params[3]);
                action.character = params[1];
                currentAction = action;
                Scriptable character = characters.get(action.character);
                if(!usedCharacters.contains(character)) {
                    usedCharacters.add(character);
                    character.startScript();
                }
                currentAction.perform(character);
            } else if(commandName.equals("moveUp")) {
                scripting.MoveUp action = new scripting.MoveUp();
                action.distance = Integer.parseInt(params[2]);
                action.speed = Float.parseFloat(params[3]);
                action.character = params[1];
                currentAction = action;
                Scriptable character = characters.get(action.character);
                if(!usedCharacters.contains(character)) {
                    usedCharacters.add(character);
                    character.startScript();
                }
                currentAction.perform(character);
            } else if(commandName.equals("moveLeft")) {
                scripting.MoveLeft action = new scripting.MoveLeft();
                action.distance = Integer.parseInt(params[2]);
                action.speed = Float.parseFloat(params[3]);
                action.character = params[1];
                currentAction = action;
                Scriptable character = characters.get(action.character);
                if(!usedCharacters.contains(character)) {
                    usedCharacters.add(character);
                    character.startScript();
                }
                currentAction.perform(character);
            } else if(commandName.equals("moveDown")) {
                scripting.MoveDown action = new scripting.MoveDown();
                action.distance = Integer.parseInt(params[2]);
                action.speed = Float.parseFloat(params[3]);
                action.character = params[1];
                currentAction = action;
                Scriptable character = characters.get(action.character);
                if(!usedCharacters.contains(character)) {
                    usedCharacters.add(character);
                    character.startScript();
                }
                currentAction.perform(character);
            } else if(commandName.equals("moveToX")) {
                scripting.MoveToX action = new scripting.MoveToX();
                String targetCharName = params[2];
                action.target = characters.get(targetCharName);
                action.xOffset = Float.parseFloat(params[3]);
                action.speed = Float.parseFloat(params[4]);
                action.character = params[1];
                currentAction = action;
                Scriptable character = characters.get(action.character);
                if(!usedCharacters.contains(character)) {
                    usedCharacters.add(character);
                    character.startScript();
                }
                currentAction.perform(character);
            } else if(commandName.equals("moveToY")) {
                scripting.MoveToY action = new scripting.MoveToY();
                String targetCharName = params[2];
                action.target = characters.get(targetCharName);
                action.yOffset = Float.parseFloat(params[3]);
                action.speed = Float.parseFloat(params[4]);
                action.character = params[1];
                currentAction = action;
                Scriptable character = characters.get(action.character);
                if(!usedCharacters.contains(character)) {
                    usedCharacters.add(character);
                    character.startScript();
                }
                currentAction.perform(character);
            } else if(commandName.equals("pauseFor")) {
                scripting.PauseFor action = new scripting.PauseFor();
                action.seconds = Float.parseFloat(params[2]);
                action.character = params[1];
                currentAction = action;
                Scriptable character = characters.get(action.character);
                if(!usedCharacters.contains(character)) {
                    usedCharacters.add(character);
                    character.startScript();
                }
                currentAction.perform(character);
            } else if(commandName.equals("animationFrame")) {
                scripting.AnimationFrame action = new scripting.AnimationFrame();
                action.textureName = params[2];
                action.index = Integer.parseInt(params[3]);
                action.character = params[1];
                currentAction = action;
                Scriptable character = characters.get(action.character);
                if(!usedCharacters.contains(character)) {
                    usedCharacters.add(character);
                    character.startScript();
                }
                currentAction.perform(character);
            } else if(commandName.equals("faceUp")) {
                scripting.FaceUp action = new scripting.FaceUp();
                action.character = params[1];
                currentAction = action;
                Scriptable character = characters.get(action.character);
                if(!usedCharacters.contains(character)) {
                    usedCharacters.add(character);
                    character.startScript();
                }
                currentAction.perform(character);
            } else if(commandName.equals("faceDown")) {
                scripting.FaceDown action = new scripting.FaceDown();
                action.character = params[1];
                currentAction = action;
                Scriptable character = characters.get(action.character);
                if(!usedCharacters.contains(character)) {
                    usedCharacters.add(character);
                    character.startScript();
                }
                currentAction.perform(character);
            } else if(commandName.equals("faceLeft")) {
                scripting.FaceLeft action = new scripting.FaceLeft();
                action.character = params[1];
                currentAction = action;
                Scriptable character = characters.get(action.character);
                if(!usedCharacters.contains(character)) {
                    usedCharacters.add(character);
                    character.startScript();
                }
                currentAction.perform(character);
            } else if(commandName.equals("faceRight")) {
                scripting.FaceRight action = new scripting.FaceRight();
                action.character = params[1];
                currentAction = action;
                Scriptable character = characters.get(action.character);
                if(!usedCharacters.contains(character)) {
                    usedCharacters.add(character);
                    character.startScript();
                }
                currentAction.perform(character);
            } else if(commandName.equals("decoupleMovementAndAnimation")) {
                scripting.DecoupleMovementAndAnimation action = new scripting.DecoupleMovementAndAnimation();
                action.character = params[1];
                currentAction = action;
                Scriptable character = characters.get(action.character);
                if(!usedCharacters.contains(character)) {
                    usedCharacters.add(character);
                    character.startScript();
                }
                currentAction.perform(character);
            } else if(commandName.equals("coupleMovementAndAnimation")) {
                scripting.CoupleMovementAndAnimation action = new scripting.CoupleMovementAndAnimation();
                action.character = params[1];
                currentAction = action;
                Scriptable character = characters.get(action.character);
                if(!usedCharacters.contains(character)) {
                    usedCharacters.add(character);
                    character.startScript();
                }
                currentAction.perform(character);
            } else if(commandName.equals("hide")) {
                scripting.Hide action = new scripting.Hide();
                action.character = params[1];
                currentAction = action;
                Scriptable character = characters.get(action.character);
                if(!usedCharacters.contains(character)) {
                    usedCharacters.add(character);
                    character.startScript();
                }
                currentAction.perform(character);
            } else if(commandName.equals("unhide")) {
                scripting.UnHide action = new scripting.UnHide();
                action.character = params[1];
                currentAction = action;
                Scriptable character = characters.get(action.character);
                if(!usedCharacters.contains(character)) {
                    usedCharacters.add(character);
                    character.startScript();
                }
                currentAction.perform(character);
            } else if(commandName.equals("disableCollisions")) {
                scripting.DisableCollisions action = new scripting.DisableCollisions();
                action.character = params[1];
                currentAction = action;
                Scriptable character = characters.get(action.character);
                if(!usedCharacters.contains(character)) {
                    usedCharacters.add(character);
                    character.startScript();
                }
                currentAction.perform(character);
            } else if(commandName.equals("enableCollisions")) {
                scripting.EnableCollisions action = new scripting.EnableCollisions();
                action.character = params[1];
                currentAction = action;
                Scriptable character = characters.get(action.character);
                if(!usedCharacters.contains(character)) {
                    usedCharacters.add(character);
                    character.startScript();
                }
                currentAction.perform(character);
            } else if(commandName.equals("attachTo")) {
                scripting.AttachTo action = new scripting.AttachTo();
                action.character = params[1];
                String followedCharName = params[2];
                action.attachedCharacter = characters.get(followedCharName);
                currentAction = action;
                Scriptable character = characters.get(action.character);
                if(!usedCharacters.contains(character)) {
                    usedCharacters.add(character);
                    character.startScript();
                }
                currentAction.perform(character);
            } else if(commandName.equals("detachFrom")) {
                scripting.DetachFrom action = new scripting.DetachFrom();
                action.character = params[1];
                String followedCharName = params[2];
                action.attachedCharacter = characters.get(followedCharName);
                currentAction = action;
                Scriptable character = characters.get(action.character);
                if(!usedCharacters.contains(character)) {
                    usedCharacters.add(character);
                    character.startScript();
                }
                currentAction.perform(character);
            } else if(commandName.equals("setHelpPrompt")) {
                scripting.SetHelpPrompt action = new scripting.SetHelpPrompt();
                action.helpPrompt = String.join(" ", java.util.Arrays.copyOfRange(params, 1, params.length));
                currentAction = action;
                currentAction.perform(null);
            } else if(commandName.equals("showHelpPrompt")) {
                scripting.ShowHelpPrompt action = new scripting.ShowHelpPrompt();
                action.character = params[1];
                currentAction = action;
                Scriptable character = characters.get(action.character);
                if(!usedCharacters.contains(character)) {
                    usedCharacters.add(character);
                    character.startScript();
                }
                currentAction.perform(character);
            } else {
                System.out.println("unknown command " + commandName);
            }
			Gdx.app.log("Command:",command.getCommand());
            if(currentAction.needsWait) {
                waiting = true;
            } else {
                command = null;
            }
			//here well just print it out to console
            return;
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

        Scriptable player = characters.get("Mia");
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
            String[] lineParts = line.getText().split(":");
            Scriptable speaker;
            String overrideName;
            String overridePicture = null;
            if(lineParts.length == 1) {
                speaker = player;
                overrideName = "Mia";
                speaker.sayMessage(lineParts[0], overrideName, null);
            } else {
                String[] nameParts = lineParts[0].split("-");
                if(nameParts.length == 1) {
                    // no custom face picture
                    if(characters.containsKey(lineParts[0])) {
                        speaker = characters.get(lineParts[0]);
                        overrideName = lineParts[0];
                    } else {
                        speaker = player;
                        overrideName = lineParts[0];
                    }
                } else {
                    // custom face picture
                    overridePicture = nameParts[1];

                    if(characters.containsKey(nameParts[0])) {
                        speaker = characters.get(nameParts[0]);
                        overrideName = nameParts[0];
                    } else {
                        speaker = player;
                        overrideName = nameParts[0];
                    }
                }
                speaker.sayMessage(lineParts[1], overrideName, overridePicture);
            } 
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
        if(line == null && option == null && command == null) {
            System.out.println("no line or option");
            if(node_complete != null) {
                System.out.println("over with dialogue");
                dialogue.stop(); //stop the dialogue if it is already not stopped

                for (Scriptable s : usedCharacters) {
                    s.stopScript();
                }
                over = true;
            } else if(dialogue.isNextComplete()) {
                System.out.println("node complete");
                node_complete = dialogue.getNextAsComplete();
            }
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
