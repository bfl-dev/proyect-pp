package com.ballsteam.sokiduels.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.ballsteam.sokiduels.SokiDuels;
import com.ballsteam.sokiduels.minigames.Cachipun.CachipunScreen;
import com.ballsteam.sokiduels.player.Player;


public class MenuScreen extends AbstractScreen {
    private final Skin UI_SKIN = new Skin(Gdx.files.internal("ui/uiskin.json"));
    private Music menu_music;
    private Sprite background;
    private final Player J1;
    private final Player J2;

    public MenuScreen(Player J1, Player J2, SokiDuels main) {
        super(main);
        this.J1=J1;
        this.J2=J2;
        background = new Sprite(new Texture("TitleScreen.png"));
        menu_music = Gdx.audio.newMusic(Gdx.files.internal("A STEP FORWARD INTO TERROR.mp3"));
    }

    @Override
    public void buildStage() {
        menu_music.setVolume(0.05f);
        menu_music.setLooping(true);
        menu_music.play();

        TextButton buttonCachipunScreen = createButtonCachipunScreen();
        addActor(buttonCachipunScreen);


    }

    @Override
    public void render(float delta) {
        super.render(delta);
        main.batch.begin();
        background.draw(main.batch);
        main.batch.end();
    }

    private TextButton createButtonCachipunScreen(){
        TextButton buttonCachipun = createTextButton("Quick Play",
            (getWidth()/2)-100,getHeight()/2);
        buttonCachipun.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Screens.cachipunScreen = new CachipunScreen(main, J1, J2);
                menu_music.dispose();
                main.setScreen(Screens.cachipunScreen);
                dispose();
            }
        });
        return buttonCachipun;
    }


    private TextButton createTextButton(String title, float posX, float posY){
        TextButton textButton = new TextButton(title, UI_SKIN);
        textButton.setPosition(posX, posY);
        int BUTTON_WIDTH = 200;
        int BUTTON_HEIGHT = 50;
        textButton.setWidth(BUTTON_WIDTH);
        textButton.setHeight(BUTTON_HEIGHT);
        return textButton;
    }
}

