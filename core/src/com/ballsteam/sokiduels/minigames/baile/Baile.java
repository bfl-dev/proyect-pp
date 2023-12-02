package com.ballsteam.sokiduels.minigames.baile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.TimeUtils;
import com.ballsteam.sokiduels.SokiDuels;
import com.ballsteam.sokiduels.Screens.AbstractScreen;
import com.badlogic.gdx.utils.Array;

public class Baile extends AbstractScreen  {
    Array<FlechaArriba> flechasArriba;
    Array<FlechaAbajo> flechasAbajo;
    Array<FlechaIzquierda> flechasIzquierda;
    Array<FlechaDerecha> flechasDerecha;
    Sprite fondoFlechas;
    long lastDrop;
    int score;
    public Baile(SokiDuels main) {
        super(main);
        flechasArriba = new Array<>();
        flechasAbajo = new Array<>();
        flechasIzquierda = new Array<>();
        flechasDerecha = new Array<>();
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
        fondoFlechas.draw(main.batch);
        if(TimeUtils.nanoTime() - lastDrop > 1000000000) spawnFlechas();
        drawOnscreenText();
        flechasArriba.forEach(flechaArriba -> flechaArriba.draw(main.batch));
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP) && flechasArriba.size > 0) {
            if (flechasArriba.get(0).getPosicionArriba().y >= 0 && flechasArriba.get(0).getPosicionArriba().y <= 64) score += 10;
            flechasArriba.removeValue(flechasArriba.get(0), true);
        }
        flechasAbajo.forEach(flechaAbajo -> flechaAbajo.draw(main.batch));
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) && flechasAbajo.size > 0) {
            if (flechasAbajo.get(0).getPosicionAbajo().y >= 0 && flechasAbajo.get(0).getPosicionAbajo().y <= 64) score += 10;
            flechasAbajo.removeValue(flechasAbajo.get(0), true);
        }
        flechasIzquierda.forEach(flechaIzquierda -> flechaIzquierda.draw(main.batch));
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) && flechasIzquierda.size > 0) {
            if (flechasIzquierda.get(0).getPosicionIzquierda().x >= 0 && flechasIzquierda.get(0).getPosicionIzquierda().x <= 64) score += 10;
            flechasIzquierda.removeValue(flechasIzquierda.get(0), true);
        }
        flechasDerecha.forEach(flechaDerecha -> flechaDerecha.draw(main.batch));
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) && flechasDerecha.size > 0) {
            if (flechasDerecha.get(0).getPosicionDerecha().x >= 0 && flechasDerecha.get(0).getPosicionDerecha().x <= 64) {
                score += 10;
            }
            flechasDerecha.removeValue(flechasDerecha.get(0), true);
        }
        main.batch.end();
    }
    public void spawnFlechas() {
        int random = (int) (Math.random() * 4);
        switch (random) {
            case 0 -> {
                FlechaArriba flechaArriba = new FlechaArriba();
                flechasArriba.add(flechaArriba);
            }
            case 1 -> {
                FlechaAbajo flechaAbajo = new FlechaAbajo();
                flechasAbajo.add(flechaAbajo);
            }
            case 2 -> {
                FlechaIzquierda flechaIzquierda = new FlechaIzquierda();
                flechasIzquierda.add(flechaIzquierda);
            }
            case 3 -> {
                FlechaDerecha flechaDerecha = new FlechaDerecha();
                flechasDerecha.add(flechaDerecha);
            }
        }
        lastDrop = TimeUtils.nanoTime();
    }
    public void dispose() {
        flechasArriba.forEach(FlechaArriba::dispose);
        flechasAbajo.forEach(FlechaAbajo::dispose);
        flechasIzquierda.forEach(FlechaIzquierda::dispose);
    }
    private void drawOnscreenText() {
        main.font.draw(main.batch, "Score: " + score, 256, 20);
    }
}
