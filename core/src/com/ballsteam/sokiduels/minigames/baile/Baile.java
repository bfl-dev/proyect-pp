package com.ballsteam.sokiduels.minigames.baile;


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

public class Baile extends AbstractScreen  {
    Sprite FLECHA_ABAJO = new Sprite(new Texture("flechaAbajo.png"));
    Sprite FLECHA_ARRIBA = new Sprite(new Texture("flechaArriba.png"));
    Sprite FLECHA_DERECHA = new Sprite(new Texture("flechaDerecha.png"));
    Sprite FLECHA_IZQUIERDA = new Sprite(new Texture("flechaIzquierda.png"));
    Array<Flecha> flechasArriba;
    Array<Flecha> flechasAbajo;
    Array<Flecha> flechasIzquierda;
    Array<Flecha> flechasDerecha;
    Array<Array<Flecha>> flechas;
    Sprite fondoFlechas;
    //No se que es mejor esa wea u 8 bools pq si
    boolean[] J1_ARROWS = new boolean[]{false,false,false,false};
    boolean[] J2_ARROWS = new boolean[]{false,false,false,false};
    long lastDrop;
    int score;
    Player J1;
    Player J2;
    HashMap<Player, boolean[]> players = new HashMap<>();

    public Baile(Player J1, Player J2, SokiDuels main) {
        super(main);
        this.J1 = J1;
        this.J2 = J2;

        players.put(J1,J1_ARROWS);
        players.put(J2,J2_ARROWS);

        flechasIzquierda = new Array<>();
        flechasAbajo = new Array<>();
        flechasArriba = new Array<>();
        flechasDerecha = new Array<>();

        flechas = new Array<>();
        flechas.add(flechasIzquierda);
        flechas.add(flechasAbajo);
        flechas.add(flechasArriba);
        flechas.add(flechasDerecha);

        fondoFlechas = new Sprite(new Texture("flechas.png"));
        spawnFlechas();
        score = 0;
    }

    @Override
    public void buildStage() {

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        main.batch.begin();
        updatePlayerArrows(J1);
        fondoFlechas.draw(main.batch);
        if(TimeUtils.nanoTime() - lastDrop > 1000000000) spawnFlechas();
        drawOnscreenText();

        flechasArriba.forEach(flechaArriba -> flechaArriba.draw(main.batch));
        flechasAbajo.forEach(flechaAbajo -> flechaAbajo.draw(main.batch));
        flechasIzquierda.forEach(flechaIzquierda -> flechaIzquierda.draw(main.batch));
        flechasDerecha.forEach(flechaDerecha -> flechaDerecha.draw(main.batch));

        //TODO: Agregar J2 plssssss


        //Tiene que haber una forma de hacer esta wea con funcional
        if (J1_ARROWS[0]) {
            laCONCHADELALORA(flechasIzquierda);
        }
        if (J1_ARROWS[1]) {
            laCONCHADELALORA(flechasAbajo);
        }
        if (J1_ARROWS[2]) {
            laCONCHADELALORA(flechasArriba);
        }
        if (J1_ARROWS[3]) {
            laCONCHADELALORA(flechasDerecha);
        }

        flechas.forEach(this::repentSINNER);


        main.batch.end();

    }
    //TODO: refactor name of laCONCHADELALORA
    private void laCONCHADELALORA(Array<Flecha> flechas){
        flechas.forEach(flecha -> {
            if (flecha.getPosition().y<100&&flecha.getPosition().y>-5){
                flechas.removeValue(flecha, true);
                score++;
            }
        });
    }

    //TODO: refactor name of repentSINNER
    private void repentSINNER(Array<Flecha> flechas){
        flechas.forEach(flecha -> {
            if (flecha.getPosition().y< -20){
                flechas.removeValue(flecha,true);
            }
        });

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
    }
    public void spawnFlechas() {
        int random = (int) (Math.random() * 4);
        switch (random) {
            case 0 -> {
                Flecha flechaArriba = new Flecha(FLECHA_ARRIBA,new Vector2(128, 480));
                flechasArriba.add(flechaArriba);
            }
            case 1 -> {
                Flecha flechaAbajo = new Flecha(FLECHA_ABAJO,new Vector2(64, 480));
                flechasAbajo.add(flechaAbajo);
            }
            case 2 -> {
                Flecha flechaIzquierda = new Flecha(FLECHA_IZQUIERDA,new Vector2(0, 480));
                flechasIzquierda.add(flechaIzquierda);
            }
            case 3 -> {
                Flecha flechaDerecha = new Flecha(FLECHA_DERECHA,new Vector2(192, 480));
                flechasDerecha.add(flechaDerecha);
            }
        }
        lastDrop = TimeUtils.nanoTime();
    }
    public void dispose() {
        flechasArriba.forEach(Flecha::dispose);
        flechasAbajo.forEach(Flecha::dispose);
        flechasIzquierda.forEach(Flecha::dispose);
        flechasDerecha.forEach(Flecha::dispose);
    }
    private void drawOnscreenText() {
        main.font.draw(main.batch, "Score: " + score, 256, 20);
    }
}
