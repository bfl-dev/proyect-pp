package com.ballsteam.sokiduels.minigames.dance;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.ballsteam.sokiduels.Screens.Screens;
import com.ballsteam.sokiduels.SokiDuels;
import com.ballsteam.sokiduels.Screens.AbstractScreen;
import com.badlogic.gdx.utils.Array;
import com.ballsteam.sokiduels.minigames.Cachipun.Duelist;
import com.ballsteam.sokiduels.interfaces.GameState;
import com.ballsteam.sokiduels.player.ControllerInput;
import com.ballsteam.sokiduels.player.KeyboardInput;
import com.ballsteam.sokiduels.player.Player;
import java.util.HashMap;

/**
 * La clase Baile representa un minijuego de baile en el juego SokiDuels.
 * Los jugadores deben seguir las flechas que aparecen en la pantalla utilizando
 * sus respectivos controles para ganar puntos.
 */
public class DanceScreen extends AbstractScreen implements GameState {
    private final Sprite DOWN_ARROW = new Sprite(new Texture("baile/flechaAbajo.png"));
    private final Sprite UP_ARROW = new Sprite(new Texture("baile/flechaArriba.png"));
    private final Sprite RIGHT_ARROW = new Sprite(new Texture("baile/flechaDerecha.png"));
    private final Sprite LEFT_ARROW = new Sprite(new Texture("baile/flechaIzquierda.png"));
    private final Array<DanceArrow> upArrows;
    private final Array<DanceArrow> downArrows;
    private final Array<DanceArrow> leftArrows;
    private final Array<DanceArrow> rightArrows;
    private final Array<Array<DanceArrow>> P1Arrows;
    private final Array<Array<DanceArrow>> P2Arrows;

    private final Sound pointsSound;

    private final Sprite arrowsBackground;
    private final Sprite arrowsBackground2;
    private final Sprite background;
    private final boolean[] P1_ARROWS = new boolean[]{false,false,false,false};
    private final boolean[] P2_ARROWS = new boolean[]{false,false,false,false};
    private long lastDrop;
    private final Player P1;
    private final Player P2;
    long timeGame;

    private final Duelist duelist1;
    private final Duelist duelist2;
    private final HashMap<Player, boolean[]> players = new HashMap<>();

    public final Music song;

    /**
     * Constructor de la clase Baile.
     *
     * @param P1    Jugador 1.
     * @param P2    Jugador 2.
     * @param main  Instancia principal del juego SokiDuels.
     */
    public DanceScreen(SokiDuels main, Player P1, Player P2, Duelist duelist1,Duelist duelist2) {
        super(main);
        this.P1 = P1;
        this.P2 = P2;

        this.duelist1 = duelist1;
        this.duelist2 = duelist2;

        players.put(P1, P1_ARROWS);
        players.put(P2, P2_ARROWS);

        leftArrows = new Array<>();
        downArrows = new Array<>();
        upArrows = new Array<>();
        rightArrows = new Array<>();

        P1Arrows = new Array<>();
        P1Arrows.add(leftArrows);
        P1Arrows.add(downArrows);
        P1Arrows.add(upArrows);
        P1Arrows.add(rightArrows);

        P2Arrows = new Array<>();
        P2Arrows.add(leftArrows);
        P2Arrows.add(downArrows);
        P2Arrows.add(upArrows);
        P2Arrows.add(rightArrows);

        arrowsBackground = new Sprite(new Texture("baile/flechas.png"));
        arrowsBackground2 = new Sprite(new Texture("baile/flechas.png"));
        background = new Sprite(new Texture("baile/fondo.png"));

        pointsSound = Gdx.audio.newSound(Gdx.files.internal("baile/soundCoin.wav"));
        song = Gdx.audio.newMusic(Gdx.files.internal("baile/backgroundMusic.mp3"));
        song.setVolume(0.05f);
        song.play();


    }

    @Override
    public void buildStage() {
        spawnArrows();
        background.setSize(getWidth(),getHeight());
        timeGame = System.currentTimeMillis();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        main.batch.begin();
        background.draw(main.batch);

        action(30000);
        result(30000, 35000L);
        closure(35000);

        main.batch.end();
    }
    private void addPoints(Array<DanceArrow> arrows, boolean isPlayerOne){
        arrows.forEach(danceArrow -> {
            if (danceArrow.getPosition().y < 100 && danceArrow.getPosition().y > -5 && danceArrow.getPosition().x < getWidth()/2 && isPlayerOne){
                arrows.removeValue(danceArrow, true);
                duelist1.addScore();
                pointsSound.play(1,2,1);
            } else if (danceArrow.getPosition().y < 100 && danceArrow.getPosition().y > -5 && danceArrow.getPosition().x >= getWidth()/2 && !isPlayerOne){
                arrows.removeValue(danceArrow, true);
                duelist2.addScore();
                pointsSound.play();
            }
        });
    }

    private void minusPoints(Array<DanceArrow> arrows){
        arrows.forEach(danceArrow -> {
            if (danceArrow.getPosition().y< -20 && danceArrow.getPosition().y>-100 && danceArrow.getPosition().x<getWidth()/2){
                arrows.removeValue(danceArrow,true);
                duelist1.subtractScore();
            } else if (danceArrow.getPosition().y< -20 && danceArrow.getPosition().y>-100 && danceArrow.getPosition().x>getWidth()/2){
                arrows.removeValue(danceArrow,true);
                duelist2.subtractScore();
            }
        });
    }

    private void updatePlayerArrows(Player player){
        player.Input.update();
        if (player.Input.getClass() == ControllerInput.class){
            updateByController(player);
        } else if (player.Input.getClass() == KeyboardInput.class){
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
        players.get(player)[0] = player.Input.LEFT == 1 && player.leftFuse;
        players.get(player)[1] = player.Input.DOWN == 1 && player.downFuse;
        players.get(player)[2] = player.Input.UP == 1 && player.upFuse;
        players.get(player)[3] = player.Input.RIGHT == 1 && player.rightFuse;
        player.danceFuses();
    }
    public void spawnArrows() {
        int random = (int) (Math.random() * 4);
        switch (random) {
            case 0 -> {
                DanceArrow upDanceArrowP1 = new DanceArrow(UP_ARROW,new Vector2((256)+128, getHeight()));
                upArrows.add(upDanceArrowP1);
                DanceArrow upDanceArrowP2 = new DanceArrow(UP_ARROW,new Vector2((256)+(128+256*2), getHeight()));
                upArrows.add(upDanceArrowP2);
            }
            case 1 -> {
                DanceArrow downDanceArrowP1 = new DanceArrow(DOWN_ARROW,new Vector2((256)+64, getHeight()));
                downArrows.add(downDanceArrowP1);
                DanceArrow downDanceArrowP2 = new DanceArrow(DOWN_ARROW,new Vector2((256)+64+ (256*2), getHeight()));
                downArrows.add(downDanceArrowP2);
            }
            case 2 -> {
                DanceArrow leftDanceArrowP1 = new DanceArrow(LEFT_ARROW,new Vector2((256), getHeight()));
                leftArrows.add(leftDanceArrowP1);
                DanceArrow leftDanceArrowP2 = new DanceArrow(LEFT_ARROW,new Vector2(((256)+512), getHeight()));
                leftArrows.add(leftDanceArrowP2);
            }
            case 3 -> {
                DanceArrow rightDanceArrowP1 = new DanceArrow(RIGHT_ARROW,new Vector2((256)+192, getHeight()));
                rightArrows.add(rightDanceArrowP1);
                DanceArrow rightDanceArrowP2 = new DanceArrow(RIGHT_ARROW,new Vector2((256)+192+ (256*2), getHeight()));
                rightArrows.add(rightDanceArrowP2);
            }
        }
        lastDrop = TimeUtils.nanoTime();
    }
    public void dispose() {
        P1Arrows.forEach(arrows -> arrows.forEach(DanceArrow::dispose));
        P2Arrows.forEach(arrows -> arrows.forEach(DanceArrow::dispose));
    }
    private void drawOnscreenText() {
        main.font.draw(main.batch, "Score: " + duelist1.score, (256)+256, 20);
        main.font.draw(main.batch, "Score: " + duelist2.score, (256)+256*3, 20);
    }
    private void finalText() {
        main.font.getData().setScale(2f);
        main.font.draw(main.batch, "Score: " + duelist1.score, (getWidth()/3)-100, getHeight()/2);
        main.font.draw(main.batch, "Score: " + duelist2.score, (getWidth()/3)*2-100, getHeight()/2);
    }

    @Override
    public void action(long timeEnd) {
        if (timeGame + timeEnd > System.currentTimeMillis()) {

            updatePlayerArrows(P1);
            updatePlayerArrows(P2);

            arrowsBackground.draw(main.batch);
            arrowsBackground.setPosition(256,0);
            arrowsBackground2.draw(main.batch);
            arrowsBackground2.setPosition((256)+(256*2),0);

            if(TimeUtils.nanoTime() - lastDrop > 333333333) spawnArrows();
            drawOnscreenText();
            P1Arrows.forEach(flechas1 -> flechas1.forEach(danceArrow -> danceArrow.draw(main.batch)));
            P2Arrows.forEach(flechas2 -> flechas2.forEach(danceArrow -> danceArrow.draw(main.batch)));

            if (P1_ARROWS[0]) {
                addPoints(leftArrows, true);
            }
            if (P1_ARROWS[1]) {
                addPoints(downArrows, true);
            }
            if (P1_ARROWS[2]) {
                addPoints(upArrows, true);
            }
            if (P1_ARROWS[3]) {
                addPoints(rightArrows, true);
            }

            if (P2_ARROWS[0]) {
                addPoints(leftArrows, false);
            }
            if (P2_ARROWS[1]) {
                addPoints(downArrows, false);
            }
            if (P2_ARROWS[2]) {
                addPoints(upArrows, false);
            }
            if (P2_ARROWS[3]) {
                addPoints(rightArrows, false);
            }

            P1Arrows.forEach(this::minusPoints);
            P2Arrows.forEach(this::minusPoints);

        }
    }

    @Override
    public void result(long timeStart, Long timeEnd) {
        if (timeGame + timeStart < System.currentTimeMillis() && timeGame + timeEnd > System.currentTimeMillis()) {
            finalText();
        }
    }

    @Override
    public void closure(long timeEnd) {
        if (timeGame + timeEnd < System.currentTimeMillis()) {
            song.dispose();
            main.setScreen(Screens.cachipunScreen);
        }
    }
}
