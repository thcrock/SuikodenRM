package utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.orangeegames.suikorm.SuikodenRM;

public class Fade {
	private float alpha = 1;
	// true if fade in, false if fade out
	private boolean fadeDirection = false;

	public Fade() {
	}

	public void show() {

	}

    public void setDirection(boolean dir) {
        fadeDirection = dir;
        if (fadeDirection == true) {
            alpha = 0;
        } else {
            alpha = 1;
        }
    }

	public void render(float delta, ShapeRenderer shapeRenderer) {
		//Gdx.gl.glClearColor(0, 0, 0, 0);
		//Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);

        if(alpha < 0) {
            return;
        }
		Gdx.gl.glEnable(Gdx.gl.GL_BLEND);
		Gdx.gl.glBlendFunc(Gdx.gl.GL_SRC_ALPHA, Gdx.gl.GL_ONE_MINUS_SRC_ALPHA);
		shapeRenderer.setColor(0, 0, 0, alpha);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		shapeRenderer.end();
		Gdx.gl.glDisable(Gdx.gl.GL_BLEND);

		if (alpha > 1 && fadeDirection == true) {
			SuikodenRM.gsm.fadeFinished();
		}
		alpha += fadeDirection == true ? 0.02 : -0.02;
	}

	public void resize(int width, int height) {

	}

	public void pause() {

	}

	public void resume() {

	}

	public void hide() {

	}

	public void dispose() {

	}
}
