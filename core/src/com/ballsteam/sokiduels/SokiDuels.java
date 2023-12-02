package com.ballsteam.sokiduels;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ballsteam.sokiduels.Screens.MenuScreen;
import com.ballsteam.sokiduels.Screens.Screens;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.github.strikerx3.jxinput.exceptions.XInputNotLoadedException;

public class SokiDuels extends Game {
	public OrthographicCamera camera;
	public SpriteBatch batch;
    public BitmapFont font;
	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
        font = new BitmapFont();
		camera.setToOrtho(false, 800, 480);
		camera.update();
		try {
			Screens.MENUSCREEN = new MenuScreen(this);
		} catch (XInputNotLoadedException e) {
			throw new RuntimeException(e);
		}
		setScreen(Screens.MENUSCREEN);

	}
}
