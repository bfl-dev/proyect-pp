package com.ballsteam.sokiduels.minigames.spaceinvaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.AbstractScreen;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Screens;

import java.util.stream.IntStream;

public class  GameScreen extends AbstractScreen {
    Spaceship spaceShip;
    Sprite fondo;
    Array<Alien> aliens;
    Array<Alien> soki;
    int direccion;
    long lastDropTime;

    public GameScreen(MyGdxGame main) {
        super(main);
        spaceShip = new Spaceship();
        aliens = new Array<>();
        direccion = 1;
        fondo = new Sprite(new Texture("fondo.png"));
        crearMatriz(aliens);
        soki = new Array<>();
        spawnSoki();
    }
    @Override
    public void buildStage() {

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        main.batch.begin();
        fondo.draw(main.batch);
        caidaAliens();
        if(TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnSoki();
        drawOnscreenText();
        spaceShip.draw(main.batch);
        main.batch.end();
    }

    @Override
    public void dispose() {
        spaceShip.dispose();
        soki.forEach(drop -> drop.dispose());
        fondo.getTexture().dispose();
    }

    public static void crearMatriz(Array<Alien> aliens) {
        IntStream.range(0, 5).forEach(i -> IntStream.range(0, 10).forEach(j -> aliens.add(new Alien(new Vector2(120 + (j * 57), 180 + (i * 57))))));
    }
    public void caidaAliens(){
        soki.forEach(drop -> {
            drop.draw(main.batch);
            drop.posAlien.y -= 150 * Gdx.graphics.getDeltaTime();
            if (drop.posAlien.y + 64 < 0) soki.removeValue(drop, true);
            spaceShip.bullets.forEach(bullet -> {
                if (bullet.bulletSprite.getBoundingRectangle().overlaps(drop.alienSprite.getBoundingRectangle())) {
                    soki.removeValue(drop, true);
                    drop.dispose();
                    spaceShip.bullets.removeValue(bullet, true);
                    spaceShip.score++;
                }
            });
            if (drop.alienSprite.getBoundingRectangle().overlaps(spaceShip.shipSprite.getBoundingRectangle())) {
                soki.removeValue(drop, true);
                drop.dispose();
                if (spaceShip.score > 0){
                    spaceShip.score--;
                } else {
                    main.setScreen(Screens.MENUSCREEN);
                }
            }
        });
    }
    private void drawOnscreenText() {
        main.font.draw(main.batch, "Score: " + spaceShip.score, 15, 20);
    }
    private void spawnSoki() {
        Alien enemy = new Alien(new Vector2(MathUtils.random(0, 800 - 64), 480));
        soki.add(enemy);
        lastDropTime = TimeUtils.nanoTime();
    }
}
