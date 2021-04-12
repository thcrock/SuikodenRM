package gamestate;

import java.util.ArrayList;

import animations.ImageCache;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.orangeegames.suikorm.SuikodenRM;

public class Popup {

	private BoxWorld levelState;
	private BitmapFont font;
	private Skin skin;
	private SpriteBatch spriteBatch;
	private int returnState;
	
	private String stringName;
    private float ttl;
    private boolean shouldShow = true;
	
	Label underChatWindow;
	Label leftUpperChatWindow;
	Label rightUpperChatWindow;
	
	// Character
	private Stage stage;
    private Scriptable scriptable;
	
	
	public Popup(BoxWorld ls, String text) {
		this.levelState = ls;
		spriteBatch = (SpriteBatch) ls.mapRenderer.getBatch();
        stringName = text;
        ttl = 2.0f;
		skin = new Skin();
		
		font = new BitmapFont(Gdx.files.internal("data/whiteFont.fnt"), false);
		
		skin.addRegions(ImageCache.atlas);
	}

	public void render(float delta) {
        if (shouldShow == false) {
            return;
        }
		spriteBatch.setProjectionMatrix(levelState.camera.combined);
		stage.act(delta);
		spriteBatch.begin();
		stage.draw();
		spriteBatch.end();
        if(!(underChatWindow.getHeight() < underChatWindow.getMaxHeight())) {
            underChatWindow.setText(stringName);
        }
        GlyphLayout layout = new GlyphLayout();
        layout.setText(
            font,
            stringName,
            Color.WHITE,
            (underChatWindow.getWidth() - underChatWindow.getStyle().background.getLeftWidth()*2 - 40),
            0,
            true
        );
	}

	public void resize(int width, int height) {
		if(stage == null) {
			stage = new Stage(new ExtendViewport(width, height));
		}
		//levelState.resize(width, height);
		stage.clear();
		
		TextButtonStyle style = new TextButtonStyle();
		
		style.up = skin.getDrawable("neat9patch");
		style.down = skin.getDrawable("neat9patch");
		style.font = font;
			
        LabelStyle chat = new LabelStyle();
        chat.background = skin.getDrawable("neat9patch");
        chat.font = font;
        chat.fontColor = new Color(Color.WHITE);
        
        int chatWindowWidth = width / 4;
        int chatWindowHeight = height/10;
        underChatWindow = new Label(stringName, chat);
        
        underChatWindow.setHeight(chatWindowHeight);
        underChatWindow.setWidth(chatWindowWidth);
        underChatWindow.setX(0 + chatWindowWidth / 12);
        underChatWindow.setY(height - chatWindowHeight - chatWindowHeight / 12);
        underChatWindow.setAlignment(Align.center);
        
        stage.addActor(underChatWindow);
	}

	public void show() {
		skin = new Skin();
		
		font = new BitmapFont(Gdx.files.internal("data/whiteFont.fnt"), false);
		
		skin.addRegions(ImageCache.atlas);
	}

	public void dispose() {
		skin.dispose();
		font.dispose();
		stage.dispose();
	}

	public void init() {
		
	}

	public void update(float delta) {
        if (ttl > 0) {
            ttl -= delta;
        } else {
            shouldShow = false;
        }
	}
}
