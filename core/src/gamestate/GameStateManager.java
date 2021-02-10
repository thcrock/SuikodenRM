package gamestate;

import java.util.HashSet;
import menus.MenuTestState;
import animations.ImageCache;

import com.badlogic.gdx.InputProcessor;
import com.orangeegames.suikorm.SuikodenRM;

import entities.Door;
import entities.GameWorldCharacter;
import entities.Conversation;
import fighting.FightingState;
import fighting.FightingTestState;
import menus.ChoiceState;

public class GameStateManager implements InputProcessor{

	public static boolean PAUSED = false;
	
	GameState[] gameState;
	SuikodenRM relation;
    HashSet<String> completedScripts;
    MusicManager musicManager;
    String helpPrompt;
	
	public int currentState;
	
	public static final int NUMGAMESTATES = 3;
	public static final int LEVELSTATE = 0;
	public static final int MENUSTATE = 1;
	public static final int ATTACKSTATE = 2;
			
	public GameStateManager (SuikodenRM rel) {
        musicManager = new MusicManager();
		gameState = new GameState[NUMGAMESTATES];
		ImageCache.load();
		relation = rel;
        completedScripts = new HashSet<String>();
		
		currentState = LEVELSTATE;
		loadState(currentState, new Door("forest1", 1));
	}
	
	private void loadState (int state, Door door) {
		if(state == LEVELSTATE) {
            BoxWorld newWorld = new BoxWorld(door);
		    gameState[currentState] = newWorld; 
            this.musicManager.playTrack(newWorld.musicTrackName);
		}
		if(state == ATTACKSTATE) {
			//gameState[state] = new FightingState((BoxWorld) gameState[LEVELSTATE]);
		}
	}
	
	private void unloadState(int state) {
		gameState[state] = null;
	}
	
	//public void setState(int state) {
		//unloadState(state);
		//currentState = state;
		//loadState(state);
		//relation.changeScreen();
	//}
	
    public void setBackgroundMusic(String musicTrackName) {
        this.musicManager.playTrack(musicTrackName);
    }

    public void setHelpPrompt(String helpPrompt) {
        this.helpPrompt = helpPrompt;
    }

    public String getHelpPrompt() {
        return this.helpPrompt;
    }

	public void setPauseState() {
		BoxWorld oldState = (BoxWorld) gameState[currentState];
		int oldStateNumber = currentState;
		gameState[currentState].pause();
		currentState = MENUSTATE;
		PAUSED = true;
		//gameState[currentState] = new InfoState(oldState, oldStateNumber, "Paused");
		gameState[currentState] = new MenuTestState(oldState, oldStateNumber, "Paused");
		relation.changeScreen();
	}
	
	public void setFightState() {
		gameState[currentState].pause();
		currentState = ATTACKSTATE;
		gameState[currentState] = new FightingTestState();
		relation.changeScreen();
	}

    public void setChoiceState(Scriptable character, String[] choices) {
		BoxWorld oldState = (BoxWorld) gameState[currentState];
		int oldStateNumber = currentState;
		gameState[currentState].pause();
        System.out.println("paused!");
		PAUSED = true;
		currentState = MENUSTATE;
		gameState[currentState] = new ChoiceState(oldState, 0, choices, character);
		relation.changeScreen();
    }
	
	public void unpauseState(int state) {
        System.out.println("unloading state. before: " + currentState);
		unloadState(currentState);
		currentState = state;
        System.out.println("unloading state. after: " + currentState);
		PAUSED = false;
		gameState[currentState].resume();
		relation.changeScreen();
	}
	
	public void changeWorld(Door door) {
		unloadState(currentState);
        loadState(currentState, door);
		relation.changeScreen();
	}
	
	public void setInfo(String infoString) {
        GameState gs = gameState[currentState];
        if(gs instanceof BoxWorld) {
            BoxWorld oldState = (BoxWorld) gs;
            int oldStateNumber = currentState;
            gameState[currentState].pause();
		    currentState = MENUSTATE;
		    PAUSED = true;
		    gameState[currentState] = new InfoState(oldState, oldStateNumber, infoString);
		    relation.changeScreen();
        } else {
            System.out.println("current state not BoxWorld, skipping info");
        }
	}
	
	public void setMessage(Scriptable character, String speakerOverrideName, String speakerOverridePicture) {
        GameState gs = gameState[currentState];
        if(gs instanceof BoxWorld) {
            BoxWorld oldState = (BoxWorld) gs;
            int oldStateNumber = currentState;
            gameState[currentState].pause();
            currentState = MENUSTATE;
            PAUSED = true;
            gameState[currentState] = new ChatState(oldState, oldStateNumber, character, speakerOverrideName, speakerOverridePicture);
            relation.changeScreen();
        } else {
            System.out.println("current state not BoxWorld, skipping message");
        }
	}

    public void triggerConversation(Conversation conversation) {
        GameState gs = gameState[currentState];
        if(gs instanceof BoxWorld) {
            BoxWorld bw = (BoxWorld) gs;
            bw.triggerConversation(conversation);
            completedScripts.add(conversation.name);
            System.out.println("Added " + conversation.name);
        } else {
            System.out.println("current state not BoxWorld, skipping convo");
        }
    }

    public void showLayersWithTag(String tag) {
        GameState gs = gameState[currentState];
        if(gs instanceof BoxWorld) {
            BoxWorld bw = (BoxWorld) gs;
            bw.showLayersWithTag(tag);
        } else {
            System.out.println("current state not BoxWorld, can't show layers");
        }
    }

    public HashSet<String> getCompletedScripts() {
        return completedScripts;
    }

	public void update(float delta){
        System.out.println("updating " + gameState[currentState]);
		gameState[currentState].update(delta);
	}
	
	@Override
	public boolean keyDown(int keycode) {
		gameState[currentState].keyPressed(keycode);
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		gameState[currentState].keyReleased(keycode);
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		gameState[currentState].touchDown(screenX, screenY, pointer, button);
		System.out.println(screenX + " : " + screenY + " : " + pointer + " : " + button);
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		gameState[currentState].touchUp(screenX, screenY, pointer, button);
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		gameState[currentState].touchDragged(screenX, screenY, pointer);
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
	
	public GameState getScreen() {
		return gameState[currentState];
	}
}
