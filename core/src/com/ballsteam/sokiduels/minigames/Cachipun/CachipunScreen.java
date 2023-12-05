package com.ballsteam.sokiduels.minigames.Cachipun;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.ballsteam.sokiduels.Screens.AbstractScreen;
import com.ballsteam.sokiduels.SokiDuels;
import com.ballsteam.sokiduels.minigames.baile.DanceScreen;
import com.ballsteam.sokiduels.minigames.sokiDefense.SokiDefenseScreen;
import com.ballsteam.sokiduels.minigames.spaceinvaders.SokiInvadersScreen;
import com.ballsteam.sokiduels.player.ControllerInput;
import com.ballsteam.sokiduels.player.Player;

import java.util.HashMap;
import java.util.Objects;

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

    Duelist duelist1;
    Duelist duelist2;

    boolean set = true;
    long timeout;

    HashMap<Player, boolean[]> players = new HashMap<>();
    HashMap<Duelist, String> choice = new HashMap<>(); //1 = Attack, 2 = Dance, 3 = Defend

    //TODO: RENAME ALL THE VARIABLES AND METHODS TO ENGLISH
    public CachipunScreen(SokiDuels main, Player J1, Player J2) {
        super(main);
        players.put(J1,J1_ACTION);
        players.put(J2,J2_ACTION);
        duelist1 = new Duelist(J1.isPlayerOne());
        duelist2 = new Duelist(J2.isPlayerOne());
        choice.put(duelist1,"NEUTRO");
        choice.put(duelist2,"NEUTRO");
        player1Sprite = new Sprite(new Texture("soki.png"));
        player2Sprite = new Sprite(new Texture("soki.png"));
        fondo = new Sprite(new Texture("cachipun/cachipunBackground.png"));
        fondo.setSize(getWidth(),getHeight());
        music_background = Gdx.audio.newMusic(Gdx.files.internal("cachipun/background_music.mp3"));
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
        duelist1.setDuelistAction(J1.Input.getClass()==ControllerInput.class?"ControllerCachipun":"KeyboardCachipun");
        duelist2.setDuelistAction(J2.Input.getClass()==ControllerInput.class?"ControllerCachipun":"KeyboardCachipun");
        if (duelist1.winner || duelist2.winner) {
            determineDamageWin();
            assignLoads();
        } else {
            determineDamageTie();
        }
        choice.replace(duelist1, "NEUTRO");
        choice.replace(duelist2, "NEUTRO");
        duelist1.score = 0;
        duelist2.score = 0;
        duelist1.random = false;
        duelist2.random = false;
    }
    @Override
    public void render(float delta) {
        super.render(delta);
        main.batch.begin();
        fondo.draw(main.batch);
        action(J1,duelist1);
        action(J2,duelist2);
        player1Sprite.draw(main.batch);
        player2Sprite.draw(main.batch);
        if (!choice.get(duelist1).equals("NEUTRO") && !choice.get(duelist2).equals("NEUTRO") && set){
            set = false;
            timeout = System.currentTimeMillis();
            duelist1.setDuelistAction(choice.get(duelist1).equals("Attack") ? "Sword" : choice.get(duelist1).equals("Defend")?"Shield":"Dance");
            duelist2.setDuelistAction(choice.get(duelist2).equals("Attack") ? "Sword" : choice.get(duelist2).equals("Defend")?"Shield":"Dance");
        }

        if (!set && System.currentTimeMillis()-timeout>3000){
            determineWinner(duelist1,duelist2);
            set = true;
        }
        duelist1.draw(main.batch,SCREEN_WIDTH,SCREEN_HEIGHT);
        duelist2.draw(main.batch,SCREEN_WIDTH,SCREEN_HEIGHT);
        main.batch.end();
    }
    public void action(Player player,Duelist duelist){ //TODO: Explain this method
        player.Input.update();
        duelist.random = false;
        if(choice.get(duelist).equals("NEUTRO")) {
            if (player.Input.LEFT==1) {
                choice.replace(duelist, "Attack");
            }
            if (player.Input.DOWN == 1) {
                choice.replace(duelist, randomChoice());
                duelist.random = true;
            }
            if (player.Input.UP == 1) {
                choice.replace(duelist, "Dance");
            }
            if (player.Input.RIGHT==1) {
                choice.replace(duelist, "Defend");
            }
            if (!choice.get(duelist).equals("NEUTRO")){
                duelist.setDuelistAction("Ready");
            }
        }else if (player.Input.B) {
            duelist.setDuelistAction(player.Input.getClass() == ControllerInput.class ?
                "ControllerCachipun" : "KeyboardCachipun");
            choice.put(duelist, "NEUTRO");
        }
    }
    public void determineWinner(Duelist duelist1,Duelist duelist2){ // TODO: REFACTOR ALL THIS SHIT
        if(!Objects.equals(choice.get(duelist1), choice.get(duelist2))) {
            duelist1.winner = ((choice.get(duelist1).equals("Attack") && choice.get(duelist2).equals("Dance"))||
                (choice.get(duelist1).equals("Dance")&&choice.get(duelist2).equals("Defend"))||
                choice.get(duelist1).equals("Defend")&&choice.get(duelist2).equals("Attack"));
            duelist2.winner = !duelist1.winner;
        }
        setDuelistScreen();
    }

    private void setDuelistScreen() {
        switch (choice.get(winDuelist())) {
            case "Attack" -> main.setScreen(new SokiInvadersScreen(main, J1, J2, duelist1, duelist2)); //Attack
            case "Dance" -> main.setScreen(new DanceScreen(main, J1, J2)); // Dance
            case "Defend" -> main.setScreen(new SokiDefenseScreen(main, J1, J2, duelist1, duelist2)); //Defend
        }
    }

    public void determineDamageWin() { //TODO: REMAKE THIS SHIT
        if(winDuelist().score>loseDuelist().score){
            loseDuelist().health -= 30 * winDuelist().loads[loadsGet(choice.get(winDuelist()))];
        } else if (winDuelist().score==loseDuelist().score) {
            loseDuelist().health -= 30;
        } else {
            loseDuelist().health += 30 * loseDuelist().loads[loadsGet(choice.get(loseDuelist()))];
        }
    }

    public void determineDamageTie(){
        if(duelist1.score>duelist2.score){
            duelist2.health -= 30 * duelist1.loads[loadsGet(choice.get(duelist1))];
        } else if (duelist2.score>duelist1.score) {
            duelist1.health -= 30 * duelist2.loads[loadsGet(choice.get(duelist2))];
        }
    }

    public String randomChoice(){
        int random = MathUtils.random(1, 3);
        return (random == 1) ? "Attack" : (random == 2) ? "Dance" : "Defend";
    }

    private int loadsGet(String choice){
        return choice.equals("Attack") ?
            0 : choice.equals("Dance") ?
                1 : 2;
    }

    public void assignLoads(){ //TODO : ARREGLAR CRIMEN DE GUERRA
        if (winDuelist().random){
            winDuelist().addLoad(loadsGet(choice.get(winDuelist())));
            winDuelist().addLoad(loadsGet(choice.get(winDuelist())));
        }else {
            winDuelist().addLoad(loadsGet(choice.get(winDuelist())));
        }
        loseDuelist().subtractLoad(loadsGet(choice.get(loseDuelist())));
    }
    public Duelist winDuelist(){
        return duelist1.winner?duelist1:duelist2;
    }
    public Duelist loseDuelist(){
        return !duelist1.winner?duelist1:duelist2;
    }
    @Override
    public void hide() {
        music_background.pause();
    }
}
