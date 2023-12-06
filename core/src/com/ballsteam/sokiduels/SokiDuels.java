package com.ballsteam.sokiduels;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ballsteam.sokiduels.Screens.PlayerSetScreen;
import com.github.strikerx3.jxinput.exceptions.XInputNotLoadedException;

public class SokiDuels extends Game {
	public OrthographicCamera camera;

	public SpriteBatch batch;
    public BitmapFont font;
	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
        font = new BitmapFont(Gdx.files.internal("font.fnt"));
		camera.setToOrtho(false, 1366, 768);
		camera.update();
        try {
            setScreen(new PlayerSetScreen(this));
        } catch (XInputNotLoadedException e) {
            throw new RuntimeException(e);
        }

    }


}
