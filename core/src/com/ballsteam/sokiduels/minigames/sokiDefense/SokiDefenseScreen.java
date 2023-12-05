package com.ballsteam.sokiduels.minigames.sokiDefense;

import com.badlogic.gdx.graphics.Texture;
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

import java.util.HashMap;

public class SokiDefenseScreen extends AbstractScreen implements GameState { //TODO: RENAME ALL THE VARIABLES AND METHODS TO ENGLISH
    Escudo escudo1;
    Escudo escudo2;
    Array<Bullet> balas;
    long lastBulletTime;
    Array<Flag> flagsBlue;
    Array<Flag> flagsRed;
    HashMap<Player, Escudo> players = new HashMap<>();
    Duelist duelist1;
    Duelist duelist2;
    long timeGame;
    public SokiDefenseScreen(SokiDuels main, Player J1, Player J2, Duelist duelist1, Duelist duelist2) {
        super(main);
        timeGame = System.currentTimeMillis();
        this.duelist1 = duelist1;
        this.duelist2 = duelist2;
        escudo1 = new Escudo(new Texture("sokidefense/shield1.png"),new Texture("sokidefense/shield2.png"),new Texture("sokidefense/shield3.png"));
        escudo2 = new Escudo(new Texture("sokidefense/shield4.png"),new Texture("sokidefense/shield5.png"),new Texture("sokidefense/shield6.png"));
        players.put(J1,escudo1);
        players.put(J2,escudo2);
        balas = new Array<>();
        flagsBlue = new Array<>();
        flagsRed = new Array<>();
        spawnBullet();
        spawnFlagBlue();
        spawnFlagRed();
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
        closure(35000L);
        main.batch.end();
    }
    public void colisionBullet(){
        balas.forEach(bullet -> {
            if (bullet.bulletSprite.getBoundingRectangle().overlaps(escudo1.escudoSprite.getBoundingRectangle())){
                balas.removeValue(bullet,true);
                escudo1.addDamage();
            }
            if (bullet.bulletSprite.getBoundingRectangle().overlaps(escudo2.escudoSprite.getBoundingRectangle())){
                balas.removeValue(bullet,true);
                escudo2.addDamage();
            }
        });
    }
    public void drawBullets(){
        balas.forEach(bullet -> {
            bullet.draw(main.batch);
            if (bullet.posBullet.y > getHeight() || bullet.posBullet.y < 0 || bullet.posBullet.x > getWidth() || bullet.posBullet.x < 0) balas.removeValue(bullet,true);
        });
    }
    public void spawnFlagBlue() {
        Flag flagBlue = new Flag(new Vector2(MathUtils.random(32, getWidth()-32),MathUtils.random(32, getHeight()-32)),"sokidefense/flagBlue.png");
        flagsBlue.add(flagBlue);
    }
    public  void  spawnFlagRed() {
        Flag flagRed = new Flag(new Vector2(MathUtils.random(32, getWidth()-32),MathUtils.random(32, getHeight()-32)),"sokidefense/flagRed.png");
        flagsRed.add(flagRed);
    }
    private void spawnBullet() {
        Bullet bulletFromDown = new Bullet(new Vector2(MathUtils.random(32, getWidth()-32),0 ),0);
        Bullet bulletFromRight = new Bullet(new Vector2(getWidth(),MathUtils.random(32, getHeight()-32)),1);
        Bullet bulletFromUp = new Bullet(new Vector2(MathUtils.random(32, getWidth()-32), getHeight()),2);
        Bullet bulletFromLeft = new Bullet(new Vector2(0,MathUtils.random(32, getHeight()-32)),3);
        balas.add(bulletFromDown);
        balas.add(bulletFromUp);
        balas.add(bulletFromLeft);
        balas.add(bulletFromRight);
        lastBulletTime = TimeUtils.nanoTime();
    }
    private void finalText() {
        main.font.getData().setScale(2f);
        main.font.draw(main.batch, "Score: " + duelist1.score, (getWidth()/3)-100, getHeight()/2);
        main.font.draw(main.batch, "Score: " + duelist2.score, (getWidth()/3)*2-100, getHeight()/2);
    }
    private void drawOnscreenText() {
        main.font.draw(main.batch, "Score: " + duelist1.score, 15, 20);
        main.font.draw(main.batch, "Score: " + duelist2.score, getWidth()-100, 20);
    }
    public void colisionFlag(Array<Flag> flagsBlue, Escudo escudo1, Escudo escudo2,Duelist duelist) {
        flagsBlue.forEach(flag -> {
            if (flag.flagSprite.getBoundingRectangle().overlaps(escudo1.escudoSprite.getBoundingRectangle())) {
                flagsBlue.removeValue(flag, true);
                duelist.score += 10;
                escudo1.damage = 1;
            }
            if (flag.flagSprite.getBoundingRectangle().overlaps(escudo2.escudoSprite.getBoundingRectangle())) {
                flagsBlue.removeValue(flag, true);
            }
        });
    }

    private void updateShield(Player player, Escudo escudo){
        player.Input.update();
        escudo.UP_DOWN = Integer.compare((int) player.Input.UP, (int) player.Input.DOWN);
        escudo.LEFT_RIGHT = Integer.compare((int) player.Input.RIGHT, (int) player.Input.LEFT);
    }

    @Override
    public void action(long timeEnd) {
        if(timeGame + timeEnd > System.currentTimeMillis()) {
            if (flagsBlue.size < 1) spawnFlagBlue();
            if (flagsRed.size < 1) spawnFlagRed();
            if (TimeUtils.nanoTime() - lastBulletTime > 500000000) spawnBullet();
            flagsRed.forEach(flag -> flag.draw(main.batch));
            flagsBlue.forEach(flag -> flag.draw(main.batch));
            players.forEach(this::updateShield);
            escudo1.draw(main.batch);
            escudo2.draw(main.batch);
            drawBullets();
            colisionBullet();
            colisionFlag(flagsBlue, escudo1, escudo2, duelist1);
            colisionFlag(flagsRed, escudo2, escudo1, duelist2);
            drawOnscreenText();
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
        }
    }
}
