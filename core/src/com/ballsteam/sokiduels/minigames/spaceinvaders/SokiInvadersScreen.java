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

public class SokiInvadersScreen extends AbstractScreen implements GameState {
    Spaceship spaceShip;
    Spaceship spaceShip2;
    Sprite background;
    Array<Alien> aliensLeft;
    Array<Alien> aliensRight;
    int direction;
    long lastDropTime;
    Music music_background;
    long timeGame;
    Duelist duelist1;
    Duelist duelist2;
    HashMap<Player, Spaceship> players = new HashMap<>();
public SokiInvadersScreen(SokiDuels main, Player P1, Player P2, Duelist duelist1, Duelist duelist2) {
    super(main);
    timeGame = System.currentTimeMillis();
    this.duelist1 = duelist1;
    this.duelist2 = duelist2;
    music_background = Gdx.audio.newMusic(Gdx.files.internal("sokiInvaders/InvadersMusic.mp3"));
    spaceShip = new Spaceship(P1.isPlayerOne());
    spaceShip2 = new Spaceship(P2.isPlayerOne());
    players.put(P1,spaceShip);
    players.put(P2, spaceShip2);
    direction = 1;
    background = new Sprite(new Texture("sokiInvaders/fondo.png"));
    aliensLeft = new Array<>();
    aliensRight = new Array<>();
    }
    @Override
    public void buildStage() {
    background.setSize(getWidth(),getHeight());
    music_background.setVolume(0.05f);
    music_background.setLooping(true);
    music_background.play();
    spawnAlien();
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

    public void aliensFall(){
        aliensLeft.forEach(drop -> {
            drop.draw(main.batch);
            drop.posAlien.x += 150 * Gdx.graphics.getDeltaTime();
            if (drop.posAlien.x  > getWidth()) aliensLeft.removeValue(drop, true);
            colisionBulletAlien(drop, spaceShip,duelist1,aliensLeft);
            colisionBulletAlien(drop, spaceShip2,duelist2,aliensLeft);
        });
        aliensRight.forEach(drop -> {
            drop.draw(main.batch);
            drop.posAlien.x -= 150 * Gdx.graphics.getDeltaTime();
            if (drop.posAlien.x + 64 < 0) aliensRight.removeValue(drop, true);
            colisionBulletAlien(drop, spaceShip,duelist1,aliensRight);
            colisionBulletAlien(drop, spaceShip2,duelist2,aliensRight);
        });
    }

    public void colisionBulletAlien(Alien drop, Spaceship spaceShip,Duelist duelist,Array <Alien> aliens){
        spaceShip.bullets.forEach(bullet -> {
            if (bullet.bulletSprite.getBoundingRectangle().overlaps(drop.alienSprite.getBoundingRectangle())) {
                aliens.removeValue(drop, true);
                drop.dispose();
                spaceShip.bullets.removeValue(bullet, true);
                duelist.addScore();
            }
        });
    }

    public void colisionBullet(){
        spaceShip.bullets.forEach(bullet -> {
                if (bullet.bulletSprite.getBoundingRectangle().overlaps(spaceShip2.shipSprite.getBoundingRectangle())) {
                    spaceShip.bullets.removeValue(bullet, true);
                    duelist2.subtractScore();
                    duelist1.addScore();
                }
        });
        spaceShip2.bullets.forEach(bullet -> {
                if (bullet.bulletSprite.getBoundingRectangle().overlaps(spaceShip.shipSprite.getBoundingRectangle())) {
                    spaceShip2.bullets.removeValue(bullet, true);
                    duelist1.subtractScore();
                    duelist2.addScore();
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
    private void spawnAlien() {
        Alien enemyLeft = new Alien(new Vector2(0, MathUtils.random(getHeight()/5, (4*getHeight()/5))));
        aliensLeft.add(enemyLeft);
        Alien enemyRigth = new Alien(new Vector2(getWidth(), MathUtils.random(getHeight()/5, (4*getHeight()/5))));
        aliensRight.add(enemyRigth);
        lastDropTime = TimeUtils.nanoTime();
    }
    private void updateSpaceship(PlayerInput player, Spaceship spaceShip){
        player.update();
        spaceShip.LEFT = player.LEFT==1;
        spaceShip.RIGHT = player.RIGHT == 1;
        spaceShip.SHOOT = player.UP== 1;
    }
    @Override
    public void dispose() {
        spaceShip.dispose();
        spaceShip2.dispose();
        aliensLeft.forEach(Alien::dispose);
        aliensRight.forEach(Alien::dispose);
        background.getTexture().dispose();
        music_background.dispose();
    }

    @Override
    public void action(long timeEnd) {
        if (timeGame + timeEnd > System.currentTimeMillis()) {
            for (Player player : players.keySet()) {
                updateSpaceship(player.Input, players.get(player));
            }
            aliensFall();
            colisionBullet();
            if (TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnAlien();
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
