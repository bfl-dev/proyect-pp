package com.ballsteam.sokiduels.minigames.spaceinvaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.ballsteam.sokiduels.Screens.AbstractScreen;
import com.ballsteam.sokiduels.SokiDuels;
import com.ballsteam.sokiduels.player.Player;
import com.ballsteam.sokiduels.player.PlayerInput;

import java.util.HashMap;
import java.util.stream.IntStream;

public class SokiInvadersScreen extends AbstractScreen {
    Spaceship spaceShip;
    Spaceship spaceShip2;
    Sprite fondo;
    Array<Alien> soki;
    int direccion;
    long lastDropTime;

    HashMap<Player, Spaceship> players = new HashMap<>();
public SokiInvadersScreen(SokiDuels main, Player J1, Player J2) {
    super(main);
    spaceShip = new Spaceship(J1.isPlayerOne());
    spaceShip2 = new Spaceship(J2.isPlayerOne());
    players.put(J1,spaceShip);
    players.put(J2, spaceShip2);


    direccion = 1;
    fondo = new Sprite(new Texture("sokiInvaders/fondo.png"));
    fondo.setSize(getWidth(),getHeight());

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
        for (Player player : players.keySet()) {
            updateSpaceship(player.Input,players.get(player));
        }
        caidaAliens();
        colisionBullet();
        if(TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnSoki();
        drawOnscreenText();
        spaceShip.draw(main.batch);
        spaceShip2.draw(main.batch);
        main.batch.end();
    }

    @Override
    public void dispose() {
        spaceShip.dispose();
        soki.forEach(Alien::dispose);
        fondo.getTexture().dispose();
    }

    public void caidaAliens(){
        soki.forEach(drop -> {
            drop.draw(main.batch);
            drop.posAlien.x += 150 * Gdx.graphics.getDeltaTime();
            if (drop.posAlien.y + 64 < 0) soki.removeValue(drop, true);
            colisionBulletAlien(drop, spaceShip);
            colisionBulletAlien(drop, spaceShip2);
        });
    }

    public void colisionBulletAlien(Alien drop, Spaceship spaceShip) {
        spaceShip.bullets.forEach(bullet -> {
            if (bullet.bulletSprite.getBoundingRectangle().overlaps(drop.alienSprite.getBoundingRectangle())) {
                soki.removeValue(drop, true);
                drop.dispose();
                spaceShip.bullets.removeValue(bullet, true);
                spaceShip.score++;
            }
        });
    }

    public void colisionBullet(){
        spaceShip.bullets.forEach(bullet -> {
                if (bullet.bulletSprite.getBoundingRectangle().overlaps(spaceShip2.shipSprite.getBoundingRectangle())) {
                    spaceShip.bullets.removeValue(bullet, true);
                    spaceShip2.score--;
                }
        });
        spaceShip2.bullets.forEach(bullet -> {
                if (bullet.bulletSprite.getBoundingRectangle().overlaps(spaceShip.shipSprite.getBoundingRectangle())) {
                    spaceShip2.bullets.removeValue(bullet, true);
                    spaceShip.score--;
                }
        });
    }
    private void drawOnscreenText() {
        main.font.draw(main.batch, "Score: " + spaceShip.score, 15, 20);
        main.font.draw(main.batch, "Score: " + spaceShip2.score, 720, 460);
    }
    private void spawnSoki() {
        Alien enemy = new Alien(new Vector2(0, MathUtils.random(72, 370)));
        soki.add(enemy);
        lastDropTime = TimeUtils.nanoTime();
    }
    private void updateSpaceship(PlayerInput player, Spaceship spaceShip){
        player.update();
        spaceShip.LEFT = player.LEFT==1;
        spaceShip.RIGHT = player.RIGHT == 1;
        spaceShip.SHOOT = player.A;
    }
}
