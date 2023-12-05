package com.ballsteam.sokiduels.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.ballsteam.sokiduels.SokiDuels;
import com.ballsteam.sokiduels.minigames.Cachipun.CachipunScreen;
import com.ballsteam.sokiduels.minigames.dance.DanceScreen;
import com.ballsteam.sokiduels.player.Player;


public class MenuScreen extends AbstractScreen {
    private final Label text = new Label("Proyecto PP", new Skin(Gdx.files.internal("ui/uiskin.json")));
    private final Skin UI_SKIN = new Skin(Gdx.files.internal("ui/uiskin.json"));
    private final Player J1;
    private final Player J2;

    public MenuScreen(Player J1, Player J2, SokiDuels main) {
        super(main);
        this.J1=J1;
        this.J2=J2;
    }

    @Override
    public void buildStage() {

        text.setPosition(getWidth() / 2f, 440, Align.center);
        addActor(text);

        //buttonCachipunScreen
        TextButton buttonCachipunScreen = createButtonCachipunScreen();
        addActor(buttonCachipunScreen);

        //buttonBaile
        TextButton buttonBaile = createButtonBaile();
        addActor(buttonBaile);

        //buttonSokiInvaders
        TextButton buttonSokiInvaders = createButtonSokiInvaders();
        addActor(buttonSokiInvaders);

        TextButton buttonSokiDefense = createButtonSokiDefense();
        addActor(buttonSokiDefense);

    }

    private TextButton createButtonSokiDefense(){
        TextButton buttonSokiDefense = createTextButton("SokiDefenseScreen",
            (text.getX() - 50),(text.getY() - 250));
        buttonSokiDefense.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                dispose();
            }
        });
        return buttonSokiDefense;
    }

    private TextButton createButtonCachipunScreen(){
        TextButton buttonCachipun = createTextButton("CachipunScreen",
                (text.getX() - 50),(text.getY() - 100));
        buttonCachipun.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Screens.cachipunScreen = new CachipunScreen(main, J1, J2);
                main.setScreen(Screens.cachipunScreen);
                dispose();
            }
        });
        return buttonCachipun;
    }


    private TextButton createButtonBaile(){
        TextButton buttonBaile = createTextButton("BaileScreen",
                (text.getX() - 50), (text.getY() - 150));
        buttonBaile.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                main.setScreen(new DanceScreen(main,J1, J2));
                dispose();
            }
        });
        return buttonBaile;
    }

    private TextButton createButtonSokiInvaders(){
        TextButton buttonSokiInvaders = createTextButton("SokiInvadersScreen",
                (text.getX() - 50),(text.getY() - 200));
        buttonSokiInvaders.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                dispose();
            }
        });
        return buttonSokiInvaders;
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

