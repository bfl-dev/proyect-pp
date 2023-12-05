package com.ballsteam.sokiduels.minigames.baile;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.ballsteam.sokiduels.Screens.Screens;
import com.ballsteam.sokiduels.SokiDuels;
import com.ballsteam.sokiduels.Screens.AbstractScreen;
import com.badlogic.gdx.utils.Array;
import com.ballsteam.sokiduels.minigames.GameState;
import com.ballsteam.sokiduels.player.ControllerInput;
import com.ballsteam.sokiduels.player.KeyboardInput;
import com.ballsteam.sokiduels.player.Player;
import java.util.HashMap;

/**
 * La clase Baile representa un minijuego de baile en el juego SokiDuels.
 * Los jugadores deben seguir las flechas que aparecen en la pantalla utilizando
 * sus respectivos controles para ganar puntos.
 */
public class DanceScreen extends AbstractScreen implements GameState { //TODO: RENAME ALL THE VARIABLES AND METHODS TO ENGLISH
    private final Sprite DOWN_ARROW = new Sprite(new Texture("baile/flechaAbajo.png"));
    private final Sprite UP_ARROW = new Sprite(new Texture("baile/flechaArriba.png"));
    private final Sprite RIGHT_ARROW = new Sprite(new Texture("baile/flechaDerecha.png"));
    private final Sprite LEFT_ARROW = new Sprite(new Texture("baile/flechaIzquierda.png"));
    private final Array<Flecha> upArrows;
    private final Array<Flecha> downArrows;
    private final Array<Flecha> leftArrows;
    private final Array<Flecha> rightArrows;
    private final Array<Array<Flecha>> P1Arrows;
    private final Array<Array<Flecha>> P2Arrows;

    private final Sound pointsSound = Gdx.audio.newSound(Gdx.files.internal("baile/soundCoin.wav"));

    private final Sprite arrowsBackground;
    private final Sprite arrowsBackground2;
    //No se que es mejor esa wea u 8 bools pq si
    private final boolean[] P1_ARROWS = new boolean[]{false,false,false,false};
    private final boolean[] P2_ARROWS = new boolean[]{false,false,false,false};
    private long lastDrop;
    private int scoreP1;
    private int scoreP2;
    private final Player P1;
    private final Player P2;
    long timeGame;
    private final HashMap<Player, boolean[]> players = new HashMap<>();

    /**
     * Constructor de la clase Baile.
     *
     * @param P1    Jugador 1.
     * @param P2    Jugador 2.
     * @param main  Instancia principal del juego SokiDuels.
     */
    public DanceScreen(SokiDuels main, Player P1, Player P2) { //TODO: IMPLEMENT CACHIPUN SCREEN CONECTION
        super(main);
        this.P1 = P1;
        this.P2 = P2;

        timeGame = System.currentTimeMillis();

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

        spawnFlechas();

        scoreP2 = 0;
        scoreP1 = 0;
    }

    @Override
    public void buildStage() {
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        main.batch.begin();
        action(30000);
        result(30000, 35000L);
        closure(35000);
        main.batch.end();
    }
    private void addPoints(Array<Flecha> arrows, boolean isPlayerOne){
        arrows.forEach(arrow -> {
            if (arrow.getPosition().y < 100 && arrow.getPosition().y > -5 && arrow.getPosition().x < (256)+256 && isPlayerOne){
                arrows.removeValue(arrow, true);
                scoreP1++;
                pointsSound.play(1,2,1);
            } else if (arrow.getPosition().y < 100 && arrow.getPosition().y > -5 && arrow.getPosition().x >= (256)+256*2 && !isPlayerOne){
                arrows.removeValue(arrow, true);
                scoreP2++;
                pointsSound.play();
            }
        });
    }

    private void minusPoints(Array<Flecha> arrows){ // TODO: LEFT ARROW BUG, FIX IT!
        arrows.forEach(arrow -> {
            if (arrow.getPosition().y< -20 && arrow.getPosition().y>-100 && arrow.getPosition().x<(256)+256){
                arrows.removeValue(arrow,true);
                scoreP1--;
            } else if (arrow.getPosition().y< -20 && arrow.getPosition().y>-100 && arrow.getPosition().x>(256)+256*2){
                arrows.removeValue(arrow,true);
                scoreP2--;
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
    public void spawnFlechas() {
        int random = (int) (Math.random() * 4);
        switch (random) {
            case 0 -> {
                Flecha upArrowP1 = new Flecha(UP_ARROW,new Vector2((256)+128, getHeight()));
                upArrows.add(upArrowP1);
                Flecha upArrowP2 = new Flecha(UP_ARROW,new Vector2((256)+(128+256*2), getHeight()));
                upArrows.add(upArrowP2);
            }
            case 1 -> {
                Flecha downArrowP1 = new Flecha(DOWN_ARROW,new Vector2((256)+64, getHeight()));
                downArrows.add(downArrowP1);
                Flecha downArrowP2 = new Flecha(DOWN_ARROW,new Vector2((256)+64+ (256*2), getHeight()));
                downArrows.add(downArrowP2);
            }
            case 2 -> {
                Flecha flechaIzquierdaJ1 = new Flecha(LEFT_ARROW,new Vector2((256), getHeight()));
                leftArrows.add(flechaIzquierdaJ1);
                Flecha flechaIzquierdaJ2 = new Flecha(LEFT_ARROW,new Vector2(((256)+512), getHeight()));
                leftArrows.add(flechaIzquierdaJ2);
            }
            case 3 -> {
                Flecha flechaDerechaJ1 = new Flecha(RIGHT_ARROW,new Vector2((256)+192, getHeight()));
                rightArrows.add(flechaDerechaJ1);
                Flecha flechaDerechaJ2 = new Flecha(RIGHT_ARROW,new Vector2((256)+192+ (256*2), getHeight()));
                rightArrows.add(flechaDerechaJ2);
            }
        }
        lastDrop = TimeUtils.nanoTime();
    }
    public void dispose() {
        P1Arrows.forEach(flechas -> flechas.forEach(Flecha::dispose));
        P2Arrows.forEach(flechas -> flechas.forEach(Flecha::dispose));
    }
    private void drawOnscreenText() {
        main.font.draw(main.batch, "Score: " + scoreP1, (256)+256, 20);
        main.font.draw(main.batch, "Score: " + scoreP2, (256)+256*3, 20);
    }

    private void drawPointMessage(boolean isPlayerOne){
        main.font.draw(main.batch, "Punto!", isPlayerOne?(256):(256)+256, 20);
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

            if(TimeUtils.nanoTime() - lastDrop > 500000000) spawnFlechas();
            drawOnscreenText();

            P1Arrows.forEach(flechas1 -> flechas1.forEach(flecha -> flecha.draw(main.batch)));
            P2Arrows.forEach(flechas2 -> flechas2.forEach(flecha -> flecha.draw(main.batch)));

            //Tiene que haber una forma de hacer esta wea con funcional
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
        }
    }

    @Override
    public void closure(long timeEnd) {
        if (timeGame + timeEnd < System.currentTimeMillis()) {
            main.setScreen(Screens.cachipunScreen);
        }
    }
}
