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
import com.ballsteam.sokiduels.minigames.Cachipun.Duelist;
import com.ballsteam.sokiduels.minigames.GameState;
import com.ballsteam.sokiduels.player.Player;
import com.ballsteam.sokiduels.player.PlayerInput;

import java.util.HashMap;

public class SokiInvadersScreen extends AbstractScreen implements GameState { //TODO: RENAME ALL THE VARIABLES AND METHODS TO ENGLISH
    Spaceship spaceShip;
    Spaceship spaceShip2;
    Sprite fondo;
    Array<Alien> soki;
    int direccion;
    long lastDropTime;
    Music music_background;
    long timeGame;
    Duelist duelist1;
    Duelist duelist2;
    HashMap<Player, Spaceship> players = new HashMap<>();
public SokiInvadersScreen(SokiDuels main, Player J1, Player J2, Duelist duelist1, Duelist duelist2) {
    super(main);
    timeGame = System.currentTimeMillis();
    this.duelist1 = duelist1;
    this.duelist2 = duelist2;
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
        action(30000);
        result(30000, 35000L);
        closure(35000);
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
            colisionBulletAlien(drop, spaceShip,duelist1);
            colisionBulletAlien(drop, spaceShip2,duelist2);
        });
    }

    public void colisionBulletAlien(Alien drop, Spaceship spaceShip,Duelist duelist){
        spaceShip.bullets.forEach(bullet -> {
            if (bullet.bulletSprite.getBoundingRectangle().overlaps(drop.alienSprite.getBoundingRectangle())) {
                soki.removeValue(drop, true);
                drop.dispose();
                spaceShip.bullets.removeValue(bullet, true);
                duelist.score++;
            }
        });
    }

    public void colisionBullet(){
        spaceShip.bullets.forEach(bullet -> {
                if (bullet.bulletSprite.getBoundingRectangle().overlaps(spaceShip2.shipSprite.getBoundingRectangle())) {
                    spaceShip.bullets.removeValue(bullet, true);
                }
        });
        spaceShip2.bullets.forEach(bullet -> {
                if (bullet.bulletSprite.getBoundingRectangle().overlaps(spaceShip.shipSprite.getBoundingRectangle())) {
                    spaceShip2.bullets.removeValue(bullet, true);
                }
        });
    }
    private void finalText() {
        main.font.getData().setScale(2f);
        main.font.draw(main.batch, "Score: " + duelist1.score, (getWidth()/3)-100, getHeight()/2);
        main.font.draw(main.batch, "Score: " + duelist2.score, (getWidth()/3)*2-100, getHeight()/2);
    }
    private void drawOnscreenText() {
        main.font.draw(main.batch, "Score: " + duelist1.score, 15, 20);
        main.font.draw(main.batch, "Score: " + duelist2.score, getWidth()-100, getHeight()-10);
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
        spaceShip.SHOOT = player.UP== 1;
    }
    @Override
    public void action(long timeEnd) {
        if (timeGame + timeEnd > System.currentTimeMillis()) {
            for (Player player : players.keySet()) {
                updateSpaceship(player.Input, players.get(player));
            }
            caidaAliens();
            colisionBullet();
            if (TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnSoki();
            drawOnscreenText();
            spaceShip.draw(main.batch);
            spaceShip2.draw(main.batch);
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
            main.setScreen(Screens.cachipunScreen);
            music_background.dispose();
        }
    }
}
