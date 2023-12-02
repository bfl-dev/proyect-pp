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
        flechasAbajo.forEach(flechaAbajo -> flechaAbajo.draw(main.batch));
        flechasIzquierda.forEach(flechaIzquierda -> flechaIzquierda.draw(main.batch));
        flechasDerecha.forEach(flechaDerecha -> flechaDerecha.draw(main.batch));

        if (Gdx.input.isKeyPressed(Input.Keys.J)) {
            flechasArriba.forEach(flechaArriba -> {
                if (flechaArriba.getPosicionArriba().y < 100 && flechaArriba.getPosicionArriba().y > 0) {
                    flechasArriba.removeValue(flechaArriba, true);
                    score++;
                }
            });
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            flechasAbajo.forEach(flechaAbajo -> {
                if (flechaAbajo.getPosicionAbajo().y < 100 && flechaAbajo.getPosicionAbajo().y > 0) {
                    flechasAbajo.removeValue(flechaAbajo, true);
                    score++;
                }
            });
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            flechasIzquierda.forEach(flechaIzquierda -> {
                if (flechaIzquierda.getPosicionIzquierda().y < 100 && flechaIzquierda.getPosicionIzquierda().y > 0) {
                    flechasIzquierda.removeValue(flechaIzquierda, true);
                    score++;
                }
            });
        }

        if (Gdx.input.isKeyPressed(Input.Keys.K)) {
            flechasDerecha.forEach(flechaDerecha -> {
                if (flechaDerecha.getPosicionDerecha().y < 100 && flechaDerecha.getPosicionDerecha().y > 0) {
                    flechasDerecha.removeValue(flechaDerecha, true);
                    score++;
                }
            });
        }

        flechasAbajo.forEach(flechaAbajo -> {
            if (flechaAbajo.getPosicionAbajo().y < -20) {
                flechasAbajo.removeValue(flechaAbajo, true);
            }
        });

        flechasArriba.forEach(flechaArriba -> {
            if (flechaArriba.getPosicionArriba().y < -20) {
                flechasArriba.removeValue(flechaArriba, true);
            }
        });

        flechasIzquierda.forEach(flechaIzquierda -> {
            if (flechaIzquierda.getPosicionIzquierda().y < -20) {
                flechasIzquierda.removeValue(flechaIzquierda, true);
            }
        });

        flechasDerecha.forEach(flechaDerecha -> {
            if (flechaDerecha.getPosicionDerecha().y < -20) {
                flechasDerecha.removeValue(flechaDerecha, true);
            }
        });

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
        flechasDerecha.forEach(FlechaDerecha::dispose);
    }
    private void drawOnscreenText() {
        main.font.draw(main.batch, "Score: " + score, 256, 20);
    }
}
