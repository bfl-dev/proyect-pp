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
    private final Music menu_music = Gdx.audio.newMusic(Gdx.files.internal("A STEP FORWARD INTO TERROR.mp3"));
    private final Sprite background = new Sprite(new Texture("TitleScreen.png"));
    private final Player J1;
    private final Player J2;

    public MenuScreen(Player J1, Player J2, SokiDuels main) {
        super(main);
        this.J1=J1;
        this.J2=J2;
    }

    @Override
    public void buildStage() {
        menu_music.setVolume(0.05f);
        menu_music.setLooping(true);
        menu_music.play();

        TextButton buttonCachipunScreen = createQuickPlayButton();
        addActor(buttonCachipunScreen);
    }
    @Override
    public void render(float delta) {
        super.render(delta);
        main.batch.begin();
        background.draw(main.batch);
        main.batch.end();
    }

    private TextButton createQuickPlayButton(){
        TextButton quickPlayButton = new TextButton("Quick Play", UI_SKIN);
        quickPlayButton.setPosition((getWidth()/2)-100, getHeight()/2);
        quickPlayButton.setWidth(200);
        quickPlayButton.setHeight(50);
        quickPlayButton.addListener(new InputListener() {
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
        return quickPlayButton;
    }
}

