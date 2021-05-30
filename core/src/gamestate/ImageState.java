package gamestate;

import java.util.ArrayList;

import animations.ImageCache;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
import com.badlogic.gdx.utils.ScreenUtils;
import com.orangeegames.suikorm.SuikodenRM;

public class ImageState extends GameState {

	private Skin skin;
    Image i;
	private int returnState;
    ShapeRenderer shapeRenderer;
	
	
	private Stage stage;
	
	
	public ImageState(String imageName) {
        i = new Image(new TextureRegion(ImageCache.getFrame(imageName, 1)));
        shapeRenderer = new ShapeRenderer();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glEnable(Gdx.gl.GL_BLEND);
		Gdx.gl.glBlendFunc(Gdx.gl.GL_SRC_ALPHA, Gdx.gl.GL_ONE_MINUS_SRC_ALPHA);
		shapeRenderer.setColor(0.5f, 0.5f, 0.5f, 0.2f);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		shapeRenderer.end();
		Gdx.gl.glDisable(Gdx.gl.GL_BLEND);
		stage.act(delta);
        stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		if(stage == null) {
			stage = new Stage(new ExtendViewport(width, height));
		}
		stage.clear();
		
        if(i != null) {
            i.setX(width/2 - i.getImageWidth());
            stage.addActor(i);
        }
	}

	@Override
	public void show() {
		skin = new Skin();
		
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
		if(k == Keys.ENTER) SuikodenRM.gsm.unpauseState(returnState);
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
