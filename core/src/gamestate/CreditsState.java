package gamestate;

import java.util.ArrayList;

import animations.ImageCache;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.orangeegames.suikorm.SuikodenRM;
import com.kyper.yarn.DialogueData;

public class CreditsState extends GameState {

	private BitmapFont font;
	private Skin skin;
	Label credits;
    Image image1;
    Image image2;
    Image image3;
    Image[] images;
    int imageIndex = -1;
    boolean showingImage = false;
    int height = 0;
    ShapeRenderer shapeRenderer;

    float gapSeconds = 6f;
    float showSeconds = 22f;
    float currentSeconds = gapSeconds;
	
    String myText = String.join("\n\n", new String[]{
        "Kanakan Stories, Vol. 1",
        "STORY/MUSIC",
        "Tristan Crockett",
        "CHARACTER PORTRAITS",
        "Dyo",
        "GAME PROGRAMMING",
        "SuikodenRM game engine built by Orangee Games using LibGDX",
        "Maps built with Tiled",
        "Additional programming by Tristan Crockett",
        "2D ART",
        "24x32 characters with faces (big pack) by Svetlana Kushnariova (lana-chan@yandex.ru)",
        "[LPC] Tile Atlas by Lanea Zimmerman (Sharm), Stephen Challener (AKA Redshrike), Charles Sanchez (AKA CharlesGabriel), Manuel Riecke (AKA MrBeast), Daniel Armstrong (AKA HughSpectrum), Skyler Robert Colladay, Johann CHARLOT, Daniel Eddeland, Casper Nilsson, Adrix89, William.Thompsonj, Nushio, Matthew Nash",
        "[LPC] Flowers / Plants / Fungi / Wood, by bluecarrot16, Guido Bos, Ivan Voirol (Silver IV), SpiderDave, William.Thompsonj, Yar, Stephen Challener and the Open Surge team (http://opensnc.sourceforge.net), Gaurav Munjal, Johann Charlot, Casper Nilsson, Jetrel, Zabin, Hyptosis, Surt, Lanea Zimmerman, George Bailey, ansimuz, Buch, and the Open Pixel Project contributors (OpenPixelProject.com)",
        "LPC Weapons Pack by Michael Whitlock, Reemax, wulax, elmerenges, daneeklu, tskaufma, DarkwallLKE, William.Thompsonj, makrohn, bluecarrot16",
        "LPC Wooden Furniture by bluecarrot16, Baŝto, Lanea Zimmerman (Sharm), William Thompson, Tuomo Untinen (Reemax), Janna/Lilius/Jannax. https://opengameart.org/content/lpc-wooden-furniture",
        "Suikoden II Misc Tiles Rip by Davias",
        "Additional 2d assets by Edwin Diaz",
        "Additional 2d assets by Tristan Crockett",
        "CONSULTING/FEEDBACK",
        "Theodore Pastor",
        "Maldivir Dragonwitch",
        "Marcasite Vah",
        "Ryan McQuen",
        "Jocchan",
        "SPECIAL THANKS",
        "Konami and everyone responsible for the Suikoden series"
    });
	
	private Stage stage;
	
	
	public CreditsState(DialogueData data) {
        int secrets = data.getInt("$secrets");
        this.myText = "SECRETS FOUND: " + String.valueOf(secrets) + "/2\n\n\n\n" + this.myText;
        shapeRenderer = new ShapeRenderer();
	}

	@Override
	public void render(float delta) {
		SuikodenRM.gsm.update(delta);
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        credits.setY(credits.getY() + delta*37);
        credits.invalidate();
        currentSeconds -= delta;
        if(currentSeconds < 0) {
            if (showingImage) {
                images[imageIndex].remove();
                showingImage = false;
                if(imageIndex < images.length - 1) {
                    currentSeconds = gapSeconds;
                }
            } else if (imageIndex < images.length - 1) {
                imageIndex++;
                showingImage = true;
                currentSeconds = showSeconds;
                images[imageIndex].setY(this.height/4);
                images[imageIndex].setX(32);
                stage.addActor(images[imageIndex]);
            } else {
                images[imageIndex].remove();
                showingImage = false;
            }
        }
		stage.act(delta);
        stage.draw();
        if(showingImage && currentSeconds < 0.5) {
            Gdx.gl.glEnable(Gdx.gl.GL_BLEND);
            Gdx.gl.glBlendFunc(Gdx.gl.GL_SRC_ALPHA, Gdx.gl.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.setColor(0.0f, 0.0f, 0.0f, 1 - currentSeconds*2);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.rect(0, 0, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight());
            shapeRenderer.end();
		    Gdx.gl.glDisable(Gdx.gl.GL_BLEND);
        }
        if(showingImage && currentSeconds > showSeconds - 0.5) {
            Gdx.gl.glEnable(Gdx.gl.GL_BLEND);
            Gdx.gl.glBlendFunc(Gdx.gl.GL_SRC_ALPHA, Gdx.gl.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.setColor(0.0f, 0.0f, 0.0f, 0.5f - (showSeconds - currentSeconds));
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.rect(0, 0, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight());
            shapeRenderer.end();
		    Gdx.gl.glDisable(Gdx.gl.GL_BLEND);
        }
	}

	@Override
	public void resize(int width, int height) {
		if(stage == null) {
			stage = new Stage(new ExtendViewport(width, height));
		}
		stage.clear();
		
		LabelStyle style = new LabelStyle();
		
		style.font = font;
		
		TextButtonStyle ss = new TextButtonStyle();
		ss.font = font;
		
		credits = new Label(myText, style);
        credits.setWrap(true);
        credits.setWidth(width/2.8f);
		credits.setX(width/1.8f);
		credits.setY(credits.getHeight()*-1.40f);

        if(showingImage) {
            images[imageIndex].setX(16);
            images[imageIndex].setY(height/4);
        }
		
		stage.addActor(credits);
        this.height = height;
	}

	@Override
	public void show() {
		skin = new Skin();
		
		font = new BitmapFont(Gdx.files.internal("data/whiteFont.fnt"), false);
		
        SuikodenRM.gsm.setBackgroundMusic("ending.mp3");
		skin.addRegions(ImageCache.atlas);
        images = new Image[]{
            new Image(new Texture(Gdx.files.internal("images/credits_1.png"))),
            new Image(new Texture(Gdx.files.internal("images/credits_2.png"))),
            new Image(new Texture(Gdx.files.internal("images/credits_3.png"))),
        };
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
