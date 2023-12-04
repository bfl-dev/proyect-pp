package com.ballsteam.sokiduels.minigames.Cachipun;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.ballsteam.sokiduels.Screens.AbstractScreen;
import com.ballsteam.sokiduels.SokiDuels;
import com.ballsteam.sokiduels.minigames.spaceinvaders.SokiInvadersScreen;
import com.ballsteam.sokiduels.player.ControllerInput;
import com.ballsteam.sokiduels.player.KeyboardInput;
import com.ballsteam.sokiduels.player.Player;

import java.util.HashMap;

public class CachipunScreen extends AbstractScreen {

    float SCREEN_HEIGHT = this.getHeight();
    float SCREEN_WIDTH = this.getWidth();
    boolean[] J1_ACTION = new boolean[]{false,false,false,false,false};
    boolean[] J2_ACTION = new boolean[]{false,false,false,false,false};

    Music music_background;
    Sprite fondo;
    Sprite player1Sprite;
    Sprite player2Sprite;
    Player J1;
    Player J2;
    Texture [] choicheTexture;
    long timeOut;
    HashMap<Player, boolean[]> players = new HashMap<>();
    HashMap<Player, Integer> choice = new HashMap<>();
    public CachipunScreen(SokiDuels main, Player J1, Player J2) {
        super(main);
        players.put(J1,J1_ACTION);
        players.put(J2,J2_ACTION);
        choice.put(J1,0);
        choice.put(J2,0);
        choicheTexture = new Texture[]{new Texture("sword.png"),new Texture("jojo.png"),new Texture("shield.png")};
        player1Sprite = new Sprite(new Texture("soki.png"));
        player2Sprite = new Sprite(new Texture("soki.png"));
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
        player1Sprite.setPosition(SCREEN_WIDTH/3,SCREEN_HEIGHT-300);
        player2Sprite.setPosition((SCREEN_WIDTH/3)*2,SCREEN_HEIGHT-300);
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
        determineWinner(J1,J2);
        J2.draw(main.batch,SCREEN_WIDTH,SCREEN_HEIGHT);
        J1.draw(main.batch,SCREEN_WIDTH,SCREEN_HEIGHT);
        main.batch.end();
    }
    public void action(Player player){
        if(choice.get(player)==0) {
            if (players.get(player)[0]) {
                player.setPlayerAction(new Texture("ready.png"));
                choice.replace(player, 1);
            }
            if (players.get(player)[1]) {
                player.setPlayerAction(new Texture("ready.png"));
                choice.replace(player, MathUtils.random(1, 3));
            }
            if (players.get(player)[2]) {
                player.setPlayerAction(new Texture("ready.png"));
                choice.replace(player, 2);
            }
            if (players.get(player)[3]) {
                player.setPlayerAction(new Texture("ready.png"));
                choice.replace(player, 3);
            }
        }else {if (players.get(player)[4]) {
            player.setPlayerAction(new Texture("KeyboardCachipun.png"));
            choice.put(player, 0);
            }
        }
    }
    public void determineWinner(Player J1,Player J2){
        if (choice.get(J1)!=0 && choice.get(J2)!=0) {
            if (choice.get(J1) == choice.get(J2)) {
                J1.setPlayerAction(choicheTexture[choice.get(J1) - 1]);
                J2.setPlayerAction(choicheTexture[choice.get(J2) - 1]);
            }
            if (choice.get(J1) == 1 && choice.get(J2) == 2) {
                J1.setPlayerAction(choicheTexture[choice.get(J1) - 1]);
                J2.setPlayerAction(choicheTexture[choice.get(J2) - 1]);
                    try {
                        Thread.sleep(10000);
                        enterGame(J1,J2,new SokiInvadersScreen(main, J1, J2));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    enterGame(J1,J2,new SokiInvadersScreen(main, J1, J2));
            }
            if (choice.get(J1) == 1 && choice.get(J2) == 3) {
                J1.setPlayerAction(choicheTexture[choice.get(J1) - 1]);
                J2.setPlayerAction(choicheTexture[choice.get(J2) - 1]);
                //main.setScreen(new sokidefense(main, J2, J1));
            }
            if (choice.get(J1) == 2 && choice.get(J2) == 1) {
                J1.setPlayerAction(choicheTexture[choice.get(J1) - 1]);
                J2.setPlayerAction(choicheTexture[choice.get(J2) - 1]);
                enterGame(J2,J1,new SokiInvadersScreen(main, J2, J1));
            }
            if (choice.get(J1) == 2 && choice.get(J2) == 3) {
                J1.setPlayerAction(choicheTexture[choice.get(J1) - 1]);
                J2.setPlayerAction(choicheTexture[choice.get(J2) - 1]);
                //main.setScreen(new baile(main, J2, J1));
            }
            if (choice.get(J1) == 3 && choice.get(J2) == 1) {
                J1.setPlayerAction(choicheTexture[choice.get(J1) - 1]);
                J2.setPlayerAction(choicheTexture[choice.get(J2) - 1]);
                //main.setScreen(new sokidefense(main, J2, J1));
            }
            if (choice.get(J1) == 3 && choice.get(J2) == 2) {
                J1.setPlayerAction(choicheTexture[choice.get(J1) - 1]);
                J2.setPlayerAction(choicheTexture[choice.get(J2) - 1]);
                //main.setScreen(new baile(main, J2, J1));
            }
        }
    }
    public void enterGame(Player win,Player lose,AbstractScreen screen){
        main.setScreen(screen);
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
        players.get(player)[4] = player.Input.A;
    }
}
