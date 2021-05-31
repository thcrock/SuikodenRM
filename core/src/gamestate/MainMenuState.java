package gamestate;

import java.util.ArrayList;

import animations.ImageCache;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.orangeegames.suikorm.SuikodenRM;

public class MainMenuState extends GameState {

	private BitmapFont font;
	private Skin skin;
	private TextButton button;
	TextButton welcomeLabel;
	
    String headerText = "Kanakan Stories Vol 1";
	String buttonText = "Start Game";
	
	private Stage stage;
	
	
	public MainMenuState() {
	}

	@Override
	public void render(float delta) {
		stage.act(delta);
        stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		if(stage == null) {
			stage = new Stage(new ExtendViewport(width, height));
		}
		stage.clear();
		
		TextButtonStyle style = new TextButtonStyle();
		
		style.up = skin.getDrawable("neat9patch");
		style.down = skin.getDrawable("neat9patch");
		style.font = font;
		
		TextButtonStyle ss = new TextButtonStyle();
		ss.font = font;
		
		welcomeLabel = new TextButton(headerText, ss);
		welcomeLabel.addListener( new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
                System.out.println("Clicked");
			}
		
		});
		welcomeLabel.setHeight(height/4);
		welcomeLabel.setWidth(width - welcomeLabel.getHeight()/2);
		welcomeLabel.setX(width/2 - welcomeLabel.getWidth()/2);
		welcomeLabel.setY(height - welcomeLabel.getHeight()*1.5f);
		
		button = new TextButton(buttonText, style);
		button.setHeight(height/4);
		button.setWidth(width - button.getHeight()/2);
		button.setX(width/2 - button.getWidth()/2);
		button.setY(0 + button.getHeight()/4);
		
		button.addListener( new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
                button.remove();
                SuikodenRM.gsm.mainMenuStart();
			}
		
		});
	    stage.addActor(button);	
		stage.addActor(welcomeLabel);
	}

	@Override
	public void show() {
		skin = new Skin();
		
		font = new BitmapFont(Gdx.files.internal("data/whiteFont.fnt"), false);
		
		skin.addRegions(ImageCache.atlas);
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
		skin.dispose();
		font.dispose();
		stage.dispose();
	}

	@Override
	public void init() {
		
	}

	@Override
	public void update(float delta) {
		
	}

	@Override
	public void keyPressed(int k) {
        SuikodenRM.gsm.mainMenuStart();
	}

	@Override
	public void keyReleased(int k) {
	}

	@Override
	public void touchDown(int screenX, int screenY, int pointer, int button) {
		stage.touchDown(screenX, screenY, pointer, button);
	}

	@Override
	public void touchUp(int screenX, int screenY, int pointer, int button) {
		stage.touchUp(screenX, screenY, pointer, button);
	}

	@Override
	public void touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		
	}
}
