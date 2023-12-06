package com.ballsteam.sokiduels.minigames.sokiDefense;

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
import com.ballsteam.sokiduels.interfaces.GameState;
import com.ballsteam.sokiduels.player.Player;

import java.util.HashMap;

public class SokiDefenseScreen extends AbstractScreen implements GameState {
    final Shield shield1;
    final Shield shield2;
    final Array<Arrow> bullets;
    long lastBulletTime;
    final Array<Flag> flagsBlue;
    final Array<Flag> flagsRed;
    final HashMap<Player, Shield> players = new HashMap<>();
    final Duelist duelist1;
    final Duelist duelist2;
    long timeGame;
    final Music music_background;
    final Sprite background;
    public SokiDefenseScreen(SokiDuels main, Player J1, Player J2, Duelist duelist1, Duelist duelist2) {
        super(main);
        this.duelist1 = duelist1;
        this.duelist2 = duelist2;
        shield1 = new Shield(new Texture("sokidefense/shield1.png"),new Texture("sokidefense/shield2.png"),new Texture("sokidefense/shield3.png"),duelist1);
        shield2 = new Shield(new Texture("sokidefense/shield4.png"),new Texture("sokidefense/shield5.png"),new Texture("sokidefense/shield6.png"),duelist2);
        background = new Sprite(new Texture("sokidefense/fondo.jpg"));
        players.put(J1, shield1);
        players.put(J2, shield2);
        bullets = new Array<>();
        flagsBlue = new Array<>();
        flagsRed = new Array<>();
        music_background = Gdx.audio.newMusic(Gdx.files.internal("sokidefense/defense.mp3"));
        music_background.setVolume(0.05f);
    }

    @Override
    public void buildStage() {
        spawnBullet();
        spawnFlagBlue();
        spawnFlagRed();
        background.setSize(getWidth(),getHeight());
        music_background.play();
        timeGame = System.currentTimeMillis();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        main.batch.begin();
        background.draw(main.batch);
        action(30000);
        result(30000, 35000L);
        closure(35000L);
        main.batch.end();
    }
    public void colisionBullet(){
        bullets.forEach(arrow -> {
            if (arrow.bulletSprite.getBoundingRectangle().overlaps(shield1.shieldSprite.getBoundingRectangle())){
                bullets.removeValue(arrow,true);
                shield1.addDamage();
            }
            if (arrow.bulletSprite.getBoundingRectangle().overlaps(shield2.shieldSprite.getBoundingRectangle())){
                bullets.removeValue(arrow,true);
                shield2.addDamage();
            }
        });
    }
    public void drawBullets(){
        bullets.forEach(arrow -> {
            arrow.draw(main.batch);
            if (arrow.posBullet.y > getHeight() || arrow.posBullet.y < 0 || arrow.posBullet.x > getWidth() || arrow.posBullet.x < 0) bullets.removeValue(arrow,true);
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
        Arrow arrowFromDown = new Arrow(new Vector2(MathUtils.random(32, getWidth()-32),0 ),0);
        Arrow arrowFromRight = new Arrow(new Vector2(getWidth(),MathUtils.random(32, getHeight()-32)),1);
        Arrow arrowFromUp = new Arrow(new Vector2(MathUtils.random(32, getWidth()-32), getHeight()),2);
        Arrow arrowFromLeft = new Arrow(new Vector2(0,MathUtils.random(32, getHeight()-32)),3);
        bullets.add(arrowFromDown);
        bullets.add(arrowFromUp);
        bullets.add(arrowFromLeft);
        bullets.add(arrowFromRight);
        lastBulletTime = TimeUtils.nanoTime();
    }
    private void finalText() {
        main.font.getData().setScale(2f);
        main.font.draw(main.batch, "Score: " + duelist1.getScore(), (getWidth()/3)-100, getHeight()/2);
        main.font.draw(main.batch, "Score: " + duelist2.getScore(), (getWidth()/3)*2-100, getHeight()/2);
    }
    private void drawOnscreenText() {
        main.font.draw(main.batch, "Score: " + duelist1.getScore(), 15, 20);
        main.font.draw(main.batch, "Score: " + duelist2.getScore(), getWidth()-100, 20);
    }
    public void colisionFlag(Array<Flag> flagsBlue, Shield shield1, Shield shield2, Duelist duelist) {
        flagsBlue.forEach(flag -> {
            if (flag.flagSprite.getBoundingRectangle().overlaps(shield1.shieldSprite.getBoundingRectangle())) {
                flagsBlue.removeValue(flag, true);
                duelist.addScore();
                shield1.damage = 1;
            }
            if (flag.flagSprite.getBoundingRectangle().overlaps(shield2.shieldSprite.getBoundingRectangle())) {
                flagsBlue.removeValue(flag, true);
            }
        });
    }

    private void updateShield(Player player, Shield shield){
        player.Input.update();
        shield.UP_DOWN = Integer.compare((int) player.Input.UP, (int) player.Input.DOWN);
        shield.LEFT_RIGHT = Integer.compare((int) player.Input.RIGHT, (int) player.Input.LEFT);
    }

    @Override
    public void dispose() {
        super.dispose();
        shield1.dispose();
        shield2.dispose();
        flagsBlue.forEach(Flag::dispose);
        flagsRed.forEach(Flag::dispose);
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
            shield1.draw(main.batch);
            shield2.draw(main.batch);
            drawBullets();
            colisionBullet();
            colisionFlag(flagsBlue, shield1, shield2, duelist1);
            colisionFlag(flagsRed, shield2, shield1, duelist2);
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
            music_background.dispose();
            main.setScreen(Screens.cachipunScreen);
        }
    }
}
