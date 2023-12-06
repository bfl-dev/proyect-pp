package com.ballsteam.sokiduels.minigames.Cachipun;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.ballsteam.sokiduels.Screens.AbstractScreen;
import com.ballsteam.sokiduels.Screens.MenuScreen;
import com.ballsteam.sokiduels.SokiDuels;
import com.ballsteam.sokiduels.exceptions.NegativeScoreException;
import com.ballsteam.sokiduels.minigames.dance.DanceScreen;
import com.ballsteam.sokiduels.minigames.sokiDefense.SokiDefenseScreen;
import com.ballsteam.sokiduels.minigames.spaceinvaders.SokiInvadersScreen;
import com.ballsteam.sokiduels.player.ControllerInput;
import com.ballsteam.sokiduels.player.Player;

import java.util.HashMap;
import java.util.Objects;

public class CachipunScreen extends AbstractScreen {

    final float SCREEN_HEIGHT = this.getHeight();
    final float SCREEN_WIDTH = this.getWidth();

    final boolean[] P1_ACTION = new boolean[]{false,false,false,false,false};
    final boolean[] P2_ACTION = new boolean[]{false,false,false,false,false};

    final Music music_background;
    final Sprite background;

    final Sprite player1Sprite;
    final Sprite player2Sprite;

    final Player P1;
    final Player P2;

    final Duelist duelist1;
    final Duelist duelist2;

    boolean set = true;
    long timeout;

    final HashMap<Player, boolean[]> players = new HashMap<>();
    final HashMap<Duelist, String> choice = new HashMap<>(); //1 = Attack, 2 = Dance, 3 = Defend

    public CachipunScreen(SokiDuels main, Player P1, Player P2) {
        super(main);
        players.put(P1, P1_ACTION);
        players.put(P2, P2_ACTION);
        duelist1 = new Duelist(P1.isPlayerOne());
        duelist2 = new Duelist(P2.isPlayerOne());
        choice.put(duelist1,"NEUTRO");
        choice.put(duelist2,"NEUTRO");
        player1Sprite = new Sprite(new Texture("cachipun/player.png"));
        player2Sprite = new Sprite(new Texture("cachipun/player2.png"));
        background = new Sprite(new Texture("cachipun/cachipunBackground.png"));
        background.setSize(getWidth(),getHeight());
        music_background = Gdx.audio.newMusic(Gdx.files.internal("cachipun/background_music.mp3"));
        music_background.setLooping(true);
        this.P1 = P1;
        this.P2 = P2;
    }

    @Override
    public void buildStage() {
        main.font.getData().setScale(0.5f);
        player2Sprite.setScale(4f);
        player1Sprite.setScale(4f);
        music_background.setVolume(0.05f);
        music_background.play();
        player1Sprite.setPosition(SCREEN_WIDTH/3,SCREEN_HEIGHT-275);
        player2Sprite.setPosition((SCREEN_WIDTH/3)*2,SCREEN_HEIGHT-275);
        duelist1.setDuelistAction(P1.Input.getClass()==ControllerInput.class?"ControllerCachipun":"KeyboardCachipun");
        duelist2.setDuelistAction(P2.Input.getClass()==ControllerInput.class?"ControllerCachipun":"KeyboardCachipun");
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
        duelist1.winner = false;
        duelist2.winner = false;
        duelist1.random = false;
        duelist2.random = false;
    }
    @Override
    public void render(float delta) {//TODO: END THE GAME WHEN LIFE ENDS
        super.render(delta);
        main.batch.begin();
        background.draw(main.batch);
        player1Sprite.draw(main.batch);
        player2Sprite.draw(main.batch);
        if(duelist1.health > 0f && duelist2.health > 0f) {
            actionGame();
        }else {
            winnerGame();
            finishGame();
        }
        main.batch.end();
    }
    public void actionGame(){
        if (set) {
            action(P1,duelist1);
            action(P2,duelist2);
        }
        if (!choice.get(duelist1).equals("NEUTRO") && !choice.get(duelist2).equals("NEUTRO") && set){
            set = false;
            timeout = System.currentTimeMillis();
            duelist1.setDuelistAction(choice.get(duelist1).equals("Attack") ? "Sword" : choice.get(duelist1).equals("Defend")?"Shield":"Dance");
            duelist2.setDuelistAction(choice.get(duelist2).equals("Attack") ? "Sword" : choice.get(duelist2).equals("Defend")?"Shield":"Dance");
        }

        if (!set && System.currentTimeMillis()-timeout>1000){
            determineWinner(duelist1,duelist2);
            set = true;
        }
        drawLoads();
        animationDamage();
        duelist1.draw(main.batch,SCREEN_WIDTH,SCREEN_HEIGHT);
        duelist2.draw(main.batch,SCREEN_WIDTH,SCREEN_HEIGHT);
    }
    public void action(Player player,Duelist duelist){ //TODO: Explain this method
        player.Input.update();
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
            duelist.random = false;
        }
    }
    public void determineWinner(Duelist duelist1,Duelist duelist2){
        if(!Objects.equals(choice.get(duelist1), choice.get(duelist2))) {
            duelist1.winner = (
                    (checkChoice(duelist1, "Attack")&& checkChoice(duelist2, "Dance"))||
                    (checkChoice(duelist1, "Dance") && checkChoice(duelist2, "Defend"))||
                    (checkChoice(duelist1, "Defend")&& checkChoice(duelist2, "Attack")));
            duelist2.winner = !duelist1.winner;
        }
        setDuelistScreen();
    }
    private boolean checkChoice(Duelist duelist, String pick){
        return choice.get(duelist).equals(pick);
    }

    private void setDuelistScreen() {
        switch (choice.get(winDuelist())) {
            case "Attack" -> main.setScreen(new SokiInvadersScreen(main, P1, P2, duelist1, duelist2)); //Attack
            case "Dance" -> main.setScreen(new DanceScreen(main, P1, P2,duelist1,duelist2)); // Dance
            case "Defend" -> main.setScreen(new SokiDefenseScreen(main, P1, P2, duelist1, duelist2)); //Defend
        }
    }

    public void determineDamageWin() throws NegativeScoreException {
        if (winDuelist().score<0 || loseDuelist().score<0){
            throw new NegativeScoreException("player score out of bounds (score>0)");
        } else {
        if(winDuelist().score>loseDuelist().score){
            loseDuelist().healthDistance -= 30 * winDuelist().loads[loadsGet(choice.get(winDuelist()))];
        } else if (winDuelist().score==loseDuelist().score) {
            loseDuelist().healthDistance -= 30;
        } else {
            winDuelist().healthDistance -= 30;
        }
        }
    }

    public void determineDamageTie(){
        if(duelist1.score>duelist2.score){
            duelist2.healthDistance -= 30 * duelist1.loads[loadsGet(choice.get(duelist1))];
        } else if (duelist2.score>duelist1.score) {
            duelist1.healthDistance -= 30 * duelist2.loads[loadsGet(choice.get(duelist2))];
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
    public void winnerGame(){
        if (duelist1.health <= 0f) {
            player1Sprite.setTexture(new Texture("cachipun/lose.png"));
            player2Sprite.setTexture(new Texture("cachipun/win.png"));
        } else {
            player1Sprite.setTexture(new Texture("cachipun/win.png"));
            player2Sprite.setTexture(new Texture("cachipun/lose.png"));
        }
    }
    public void finishGame(){
        if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)){
            main.setScreen(new MenuScreen(P1, P2,main));
        }
    }
    public void animationDamage(){
        if (duelist1.health != duelist1.healthDistance) {
            duelist1.health -= 0.5f;
        }
        if (duelist2.health != duelist2.healthDistance) {
            duelist2.health -= 0.5f;
        }
    }
    public void drawLoads(){
        main.font.draw(main.batch, "x" + duelist1.loads[0],(SCREEN_WIDTH/3)-125, (SCREEN_HEIGHT-125));
        main.font.draw(main.batch, "x" + duelist1.loads[1],(SCREEN_WIDTH/3)-100, (SCREEN_HEIGHT-125));
        main.font.draw(main.batch, "x" + duelist1.loads[2],(SCREEN_WIDTH/3)-75, (SCREEN_HEIGHT-125));
        main.font.draw(main.batch, "x" + duelist2.loads[0],((SCREEN_WIDTH/3)*2)-125, (SCREEN_HEIGHT-125));
        main.font.draw(main.batch, "x" + duelist2.loads[1],((SCREEN_WIDTH/3)*2)-100, (SCREEN_HEIGHT-125));
        main.font.draw(main.batch, "x" + duelist2.loads[2],((SCREEN_WIDTH/3)*2)-75, (SCREEN_HEIGHT-125));
    }
    public void assignLoads(){
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
