package com.ballsteam.sokiduels;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ballsteam.sokiduels.Screens.MenuScreen;
import com.ballsteam.sokiduels.player.Player;
import com.github.strikerx3.jxinput.exceptions.XInputNotLoadedException;

public class SokiDuels extends Game {
	public OrthographicCamera camera;
	public SpriteBatch batch;
    public BitmapFont font;
    public Player player1;
    public Player player2;
	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
        font = new BitmapFont();
        player1 = new Player(true);
        player2 = new Player(false);
		camera.setToOrtho(false, 800, 480);
		camera.update();
        try {
            setScreen(new MenuScreen(this));
        } catch (XInputNotLoadedException e) {
            throw new RuntimeException(e);
        }

    }
}
