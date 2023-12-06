package com.ballsteam.sokiduels;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ballsteam.sokiduels.Screens.PlayerSetScreen;
import com.ballsteam.sokiduels.exceptions.ImportantAssetNotFoundedException;
import com.github.strikerx3.jxinput.exceptions.XInputNotLoadedException;

public class SokiDuels extends Game {

	public OrthographicCamera camera;

    public SpriteBatch batch;
    public BitmapFont font;

	@Override
	public void create () {
        checkForSoki();
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

    private Sprite checkForSoki() throws ImportantAssetNotFoundedException {
        try {
            return new Sprite(new Texture("soki.png"));
        } catch (Exception e){
            throw new ImportantAssetNotFoundedException("There's a vital file missing...");
        }
    }
}
