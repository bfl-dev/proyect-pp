package com.ballsteam.sokiduels.minigames.spaceinvaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.ballsteam.sokiduels.Screens.AbstractScreen;
import com.ballsteam.sokiduels.Screens.Screens;
import com.ballsteam.sokiduels.SokiDuels;
import com.ballsteam.sokiduels.minigames.Cachipun.CachipunScreen;
import com.ballsteam.sokiduels.player.Player;
import com.ballsteam.sokiduels.player.PlayerInput;

import java.util.HashMap;

public class SokiInvadersScreen extends AbstractScreen {
    Spaceship spaceShip;
    Spaceship spaceShip2;
    Sprite fondo;
    Array<Alien> soki;
    int direccion;
    long lastDropTime;
    Player J1;
    Player J2;
    Music music_background;
    long timeGame;
    HashMap<Player, Spaceship> players = new HashMap<>();
public SokiInvadersScreen(SokiDuels main, Player J1, Player J2) {
    super(main);
    this.J1 = J1;
    this.J2 = J2;
    timeGame = System.currentTimeMillis();
    music_background = Gdx.audio.newMusic(Gdx.files.internal("sokiInvaders/InvadersMusic.mp3"));
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
    music_background.setVolume(0.05f);
    music_background.setLooping(true);
    music_background.play();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        main.batch.begin();
        fondo.draw(main.batch);
        if (timeGame + 5000 > System.currentTimeMillis()) {
            for (Player player : players.keySet()) {
                updateSpaceship(player.Input, players.get(player));
            }
            caidaAliens();
            colisionBullet();
            if (TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnSoki();
            drawOnscreenText();
            spaceShip.draw(main.batch);
            spaceShip2.draw(main.batch);
        }else if (timeGame + 6000 > System.currentTimeMillis()) {
            drawOnScreenFinalText();
        }else {
            main.setScreen(Screens.cachipunScreen);
        }
        main.batch.end();
    }

    @Override
    public void dispose() {
        spaceShip.dispose();
        spaceShip2.dispose();
        soki.forEach(Alien::dispose);
        fondo.getTexture().dispose();
        music_background.dispose();
    }

    public void caidaAliens(){
        soki.forEach(drop -> {
            drop.draw(main.batch);
            drop.posAlien.x += 150 * Gdx.graphics.getDeltaTime();
            if (drop.posAlien.y + 64 < 0) soki.removeValue(drop, true);
            colisionBulletAlien(drop, spaceShip,J1);
            colisionBulletAlien(drop, spaceShip2,J2);
        });
    }

    public void colisionBulletAlien(Alien drop, Spaceship spaceShip,Player player) {
        spaceShip.bullets.forEach(bullet -> {
            if (bullet.bulletSprite.getBoundingRectangle().overlaps(drop.alienSprite.getBoundingRectangle())) {
                soki.removeValue(drop, true);
                drop.dispose();
                spaceShip.bullets.removeValue(bullet, true);
                player.score++;
            }
        });
    }

    public void colisionBullet(){
        spaceShip.bullets.forEach(bullet -> {
                if (bullet.bulletSprite.getBoundingRectangle().overlaps(spaceShip2.shipSprite.getBoundingRectangle())) {
                    spaceShip.bullets.removeValue(bullet, true);
                    J2.setScore(J2.score-1);
                }
        });
        spaceShip2.bullets.forEach(bullet -> {
                if (bullet.bulletSprite.getBoundingRectangle().overlaps(spaceShip.shipSprite.getBoundingRectangle())) {
                    spaceShip2.bullets.removeValue(bullet, true);
                    J1.setScore(J1.score-1);
                }
        });
    }
    private void drawOnScreenFinalText() {
        main.font.draw(main.batch, "Score: " + J1.score, 20, 20);
        main.font.draw(main.batch, "Score: " + J2.score, getWidth()-69, getHeight()-10);
    }
    private void drawOnscreenText() {
        main.font.draw(main.batch, "Score: " + J1.score, 15, 20);
        main.font.draw(main.batch, "Score: " + J2.score, getWidth()-69, getHeight()-10);
    }
    private void spawnSoki() {
        Alien enemy = new Alien(new Vector2(0, MathUtils.random(getHeight()/5, (4*getHeight()/5))));
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
