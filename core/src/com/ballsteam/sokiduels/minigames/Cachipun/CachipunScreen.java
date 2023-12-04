package com.ballsteam.sokiduels.minigames.Cachipun;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.ballsteam.sokiduels.Screens.AbstractScreen;
import com.ballsteam.sokiduels.SokiDuels;
import com.ballsteam.sokiduels.player.ControllerInput;
import com.ballsteam.sokiduels.player.KeyboardInput;
import com.ballsteam.sokiduels.player.Player;

import java.util.HashMap;

public class CachipunScreen extends AbstractScreen {

    float SCREEN_HEIGHT = this.getHeight();
    float SCREEN_WIDTH = this.getWidth();
    boolean[] J1_ACTION = new boolean[]{false,false,false,false,false};
    boolean[] J2_ACTION = new boolean[]{false,false,false,false,false};

    Sprite D_PAD_CONTROLLS;
    Sprite KEYBOARD_CONTROLS;
    Music music_background;
    Sprite fondo;
    Sprite player1Sprite;
    Sprite player2Sprite;
    Player J1;
    Player J2;
    HashMap<Player, boolean[]> players = new HashMap<>();
    HashMap<Player, Integer> eleccion = new HashMap<>();
    public CachipunScreen(SokiDuels main, Player J1, Player J2) {
        super(main);
        players.put(J1,J1_ACTION);
        players.put(J2,J2_ACTION);
        eleccion.put(J1,0);
        eleccion.put(J2,0);
        player1Sprite = new Sprite(new Texture("soki.png"));
        player2Sprite = new Sprite(new Texture("soki.png"));
        D_PAD_CONTROLLS = new Sprite(new Texture("ControllerCachipun.png"));
        KEYBOARD_CONTROLS = new Sprite(new Texture("KeyboardCachipun.png"));
        fondo = new Sprite(new Texture("cachipunBackground.png"));
        music_background = Gdx.audio.newMusic(Gdx.files.internal("background_music.mp3"));
        music_background.setLooping(true);
        this.J1=J1;
        this.J2=J2;
    }

    @Override
    public void buildStage() {
        player2Sprite.setScale(1.5f);
        player1Sprite.setScale(1.5f);
        music_background.setVolume(0.05f);
        music_background.play();
        player1Sprite.setPosition(SCREEN_WIDTH/3,SCREEN_HEIGHT-200);
        player2Sprite.setPosition((SCREEN_WIDTH/3)*2,SCREEN_HEIGHT-200);
        KEYBOARD_CONTROLS.setPosition(player1Sprite.getX()-175, player1Sprite.getY()-500);
    }
    @Override
    public void render(float delta) {
        super.render(delta);
        main.batch.begin();
        fondo.draw(main.batch);
        updatePlayerArrows(J1);
        updatePlayerArrows(J2);
        action(J1);
        action(J2);
        player1Sprite.draw(main.batch);
        player2Sprite.draw(main.batch);
        J2.draw(main.batch,SCREEN_WIDTH,SCREEN_HEIGHT);
        J1.draw(main.batch,SCREEN_WIDTH,SCREEN_HEIGHT);
        KEYBOARD_CONTROLS.draw(main.batch);
        main.batch.end();
    }
    public void action(Player player){
        if(eleccion.get(player)==0) {
            if (players.get(player)[0]) {
                KEYBOARD_CONTROLS.setTexture(new Texture("ready.png"));

            }
            if (players.get(player)[1]) {
                KEYBOARD_CONTROLS.setTexture(new Texture("ready.png"));

            }
            if (players.get(player)[2]) {
                KEYBOARD_CONTROLS.setTexture(new Texture("ready.png"));

            }
            if (players.get(player)[3]) {
                KEYBOARD_CONTROLS.setTexture(new Texture("ready.png"));
                System.out.println("eleccionPlayer1");
            }
        }else {if (players.get(player)[4])
            KEYBOARD_CONTROLS.setTexture(new Texture("KeyboardCachipun.png"));
        }
    }
    private void updatePlayerArrows(Player player){
        player.Input.update();
        if (player.Input.getClass()== ControllerInput.class){
            updateByController(player);
        } else if (player.Input.getClass()== KeyboardInput.class){
            updateByKeyboard(player);
        }
    }
    private void updateByController(Player player){
        players.get(player)[0] = ((ControllerInput)player.Input).LT;
        players.get(player)[1] = ((ControllerInput)player.Input).LB;
        players.get(player)[2] = ((ControllerInput)player.Input).RB;
        players.get(player)[3] = ((ControllerInput)player.Input).RT;
    }
    private void updateByKeyboard(Player player){
        players.get(player)[0] = player.Input.LEFT==1;
        players.get(player)[1] = player.Input.DOWN==1;
        players.get(player)[2] = player.Input.UP==1;
        players.get(player)[3] = player.Input.RIGHT==1;
        players.get(player)[4] = player.Input.B;
    }
}
