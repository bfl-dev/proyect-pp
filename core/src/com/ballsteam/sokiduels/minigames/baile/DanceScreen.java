package com.ballsteam.sokiduels.minigames.baile;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.ballsteam.sokiduels.SokiDuels;
import com.ballsteam.sokiduels.Screens.AbstractScreen;
import com.badlogic.gdx.utils.Array;
import com.ballsteam.sokiduels.player.ControllerInput;
import com.ballsteam.sokiduels.player.KeyboardInput;
import com.ballsteam.sokiduels.player.Player;
import java.util.HashMap;

/**
 * La clase Baile representa un minijuego de baile en el juego SokiDuels.
 * Los jugadores deben seguir las flechas que aparecen en la pantalla utilizando
 * sus respectivos controles para ganar puntos.
 */
public class DanceScreen extends AbstractScreen  { //TODO: RENAME ALL THE VARIABLES AND METHODS TO ENGLISH
    private final Sprite FLECHA_ABAJO = new Sprite(new Texture("baile/flechaAbajo.png"));
    private final Sprite FLECHA_ARRIBA = new Sprite(new Texture("baile/flechaArriba.png"));
    private final Sprite FLECHA_DERECHA = new Sprite(new Texture("baile/flechaDerecha.png"));
    private final Sprite FLECHA_IZQUIERDA = new Sprite(new Texture("baile/flechaIzquierda.png"));
    private final Array<Flecha> flechasArriba;
    private final Array<Flecha> flechasAbajo;
    private final Array<Flecha> flechasIzquierda;
    private final Array<Flecha> flechasDerecha;
    private final Array<Array<Flecha>> flechasJ1;
    private final Array<Array<Flecha>> flechasJ2;

    private final Sound sonidoPuntos = Gdx.audio.newSound(Gdx.files.internal("baile/soundCoin.wav"));

    private final Sprite fondoFlechas;
    private final Sprite fondoFlechas2;
    //No se que es mejor esa wea u 8 bools pq si
    private final boolean[] J1_ARROWS = new boolean[]{false,false,false,false};
    private final boolean[] J2_ARROWS = new boolean[]{false,false,false,false};
    private long lastDrop;
    private int scoreJ1;
    private int scoreJ2;
    private final Player J1;
    private final Player J2;
    private final HashMap<Player, boolean[]> players = new HashMap<>();

    /**
     * Constructor de la clase Baile.
     *
     * @param J1    Jugador 1.
     * @param J2    Jugador 2.
     * @param main  Instancia principal del juego SokiDuels.
     */
    public DanceScreen(SokiDuels main,Player J1, Player J2) { //TODO: IMPLEMENT CACHIPUN SCREEN CONECTION
        super(main);
        this.J1 = J1;
        this.J2 = J2;

        players.put(J1,J1_ARROWS);
        players.put(J2,J2_ARROWS);

        flechasIzquierda = new Array<>();
        flechasAbajo = new Array<>();
        flechasArriba = new Array<>();
        flechasDerecha = new Array<>();

        flechasJ1 = new Array<>();
        flechasJ1.add(flechasIzquierda);
        flechasJ1.add(flechasAbajo);
        flechasJ1.add(flechasArriba);
        flechasJ1.add(flechasDerecha);

        flechasJ2 = new Array<>();
        flechasJ2.add(flechasIzquierda);
        flechasJ2.add(flechasAbajo);
        flechasJ2.add(flechasArriba);
        flechasJ2.add(flechasDerecha);

        fondoFlechas = new Sprite(new Texture("baile/flechas.png"));
        fondoFlechas2 = new Sprite(new Texture("baile/flechas.png"));

        spawnFlechas();

        scoreJ2 = 0;
        scoreJ1 = 0;
    }

    @Override
    public void buildStage() {
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        main.batch.begin();

        updatePlayerArrows(J1);
        updatePlayerArrows(J2);

        fondoFlechas.draw(main.batch);
        fondoFlechas.setPosition(256,0);
        fondoFlechas2.draw(main.batch);
        fondoFlechas2.setPosition((256)+(256*2),0);

        if(TimeUtils.nanoTime() - lastDrop > 500000000) spawnFlechas();
        drawOnscreenText();

        flechasJ1.forEach(flechas1 -> flechas1.forEach(flecha -> flecha.draw(main.batch)));
        flechasJ2.forEach(flechas2 -> flechas2.forEach(flecha -> flecha.draw(main.batch)));

        //Tiene que haber una forma de hacer esta wea con funcional
        if (J1_ARROWS[0]) {
            addPoints(flechasIzquierda, true);
        }
        if (J1_ARROWS[1]) {
            addPoints(flechasAbajo, true);
        }
        if (J1_ARROWS[2]) {
            addPoints(flechasArriba, true);
        }
        if (J1_ARROWS[3]) {
            addPoints(flechasDerecha, true);
        }

        if (J2_ARROWS[0]) {
            addPoints(flechasIzquierda, false);
        }
        if (J2_ARROWS[1]) {
            addPoints(flechasAbajo, false);
        }
        if (J2_ARROWS[2]) {
            addPoints(flechasArriba, false);
        }
        if (J2_ARROWS[3]) {
            addPoints(flechasDerecha, false);
        }

        flechasJ1.forEach(this::minusPoints);
        flechasJ2.forEach(this::minusPoints);

        main.batch.end();
    }
    private void addPoints(Array<Flecha> flechas, boolean isPlayerOne){
        flechas.forEach(flecha -> {
            if (flecha.getPosition().y < 100 && flecha.getPosition().y > -5 && flecha.getPosition().x < (256)+256 && isPlayerOne){
                flechas.removeValue(flecha, true);
                scoreJ1++;
                sonidoPuntos.play(1,2,1);
            } else if (flecha.getPosition().y < 100 && flecha.getPosition().y > -5 && flecha.getPosition().x >= (256)+256*2 && !isPlayerOne){
                flechas.removeValue(flecha, true);
                scoreJ2++;
                sonidoPuntos.play();
            }
        });
    }

    private void minusPoints(Array<Flecha> flechas){ // TODO: LEFT ARROW BUG, FIX IT!
        flechas.forEach(flecha -> {
            if (flecha.getPosition().y< -20 && flecha.getPosition().y>-100 && flecha.getPosition().x<(256)+256){
                flechas.removeValue(flecha,true);
                scoreJ1--;
            } else if (flecha.getPosition().y< -20 && flecha.getPosition().y>-100 && flecha.getPosition().x>(256)+256*2){
                flechas.removeValue(flecha,true);
                scoreJ2--;
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
                Flecha flechaArribaJ1 = new Flecha(FLECHA_ARRIBA,new Vector2((256)+128, getHeight()));
                flechasArriba.add(flechaArribaJ1);
                Flecha flechaArribaJ2 = new Flecha(FLECHA_ARRIBA,new Vector2((256)+(128+256*2), getHeight()));
                flechasArriba.add(flechaArribaJ2);
            }
            case 1 -> {
                Flecha flechaAbajoJ1 = new Flecha(FLECHA_ABAJO,new Vector2((256)+64, getHeight()));
                flechasAbajo.add(flechaAbajoJ1);
                Flecha flechaAbajoJ2 = new Flecha(FLECHA_ABAJO,new Vector2((256)+64+ (256*2), getHeight()));
                flechasAbajo.add(flechaAbajoJ2);
            }
            case 2 -> {
                Flecha flechaIzquierdaJ1 = new Flecha(FLECHA_IZQUIERDA,new Vector2((256), getHeight()));
                flechasIzquierda.add(flechaIzquierdaJ1);
                Flecha flechaIzquierdaJ2 = new Flecha(FLECHA_IZQUIERDA,new Vector2(((256)+512), getHeight()));
                flechasIzquierda.add(flechaIzquierdaJ2);
            }
            case 3 -> {
                Flecha flechaDerechaJ1 = new Flecha(FLECHA_DERECHA,new Vector2((256)+192, getHeight()));
                flechasDerecha.add(flechaDerechaJ1);
                Flecha flechaDerechaJ2 = new Flecha(FLECHA_DERECHA,new Vector2((256)+192+ (256*2), getHeight()));
                flechasDerecha.add(flechaDerechaJ2);
            }
        }
        lastDrop = TimeUtils.nanoTime();
    }
    public void dispose() {
        flechasJ1.forEach(flechas -> flechas.forEach(Flecha::dispose));
        flechasJ2.forEach(flechas -> flechas.forEach(Flecha::dispose));
    }
    private void drawOnscreenText() {
        main.font.draw(main.batch, "Score: " + scoreJ1, (256)+256, 20);
        main.font.draw(main.batch, "Score: " + scoreJ2, (256)+256*3, 20);
    }

    private void drawPointMessage(boolean isPlayerOne){
        main.font.draw(main.batch, "Punto!", isPlayerOne?(256):(256)+256, 20);
    }
}
