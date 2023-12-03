package com.ballsteam.sokiduels.minigames.Cachipun;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.ballsteam.sokiduels.Screens.AbstractScreen;
import com.ballsteam.sokiduels.SokiDuels;

public class CachipunScreen extends AbstractScreen {

    float SCREEN_HEIGHT = this.getHeight();
    float SCREEN_WIDTH = this.getWidth();

    Sprite D_PAD_CONTROLLS;
    Sprite KEYBOARD_CONTROLS;
    Music music_background;
    Sprite fondo;
    Sprite player1Sprite;
    Sprite player2Sprite;
    public CachipunScreen(SokiDuels main) {
        super(main);
        player1Sprite = new Sprite(new Texture("soki.png"));
        player2Sprite = new Sprite(new Texture("soki.png"));
        D_PAD_CONTROLLS = new Sprite(new Texture("ControllerCachipun.png"));
        KEYBOARD_CONTROLS = new Sprite(new Texture("KeyboardCachipun.png"));
        fondo = new Sprite(new Texture("cachipunBackground.png"));
        music_background = Gdx.audio.newMusic(Gdx.files.internal("background_music.mp3"));
        music_background.setLooping(true);

    }

    @Override
    public void buildStage() {
        player2Sprite.setScale(1.5f);
        player1Sprite.setScale(1.5f);
        music_background.setVolume(0.05f);
        music_background.play();
    }
    @Override
    public void render(float delta) {
        super.render(delta);
        main.batch.begin();
        fondo.draw(main.batch);
        player1Sprite.draw(main.batch);
        player2Sprite.draw(main.batch);
        player1Sprite.setPosition(SCREEN_WIDTH/3,SCREEN_HEIGHT/2);
        player2Sprite.setPosition((SCREEN_WIDTH/3)*2,SCREEN_HEIGHT/2);
        KEYBOARD_CONTROLS.draw(main.batch);
        KEYBOARD_CONTROLS.setPosition(player1Sprite.getX(), player1Sprite.getY()+50);
        main.batch.end();
    }
}
