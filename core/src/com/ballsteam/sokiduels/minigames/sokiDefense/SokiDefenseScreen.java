package com.ballsteam.sokiduels.minigames.sokiDefense;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.ballsteam.sokiduels.Screens.AbstractScreen;
import com.ballsteam.sokiduels.SokiDuels;
import com.ballsteam.sokiduels.player.Player;
import java.util.HashMap;

public class SokiDefenseScreen extends AbstractScreen {
    Escudo escudo1;
    Escudo escudo2;
    Array<Bala> balas;
    long lastBulletTime;
    Array<Flag> flagsBlue;
    Array<Flag> flagsRed;
    HashMap<Player, Escudo> players = new HashMap<>();


    public SokiDefenseScreen(SokiDuels main, Player J1, Player J2) {
        super(main);
        escudo1 = new Escudo(new Texture("shield1.png"),new Texture("shield2.png"),new Texture("shield3.png"));
        escudo2 = new Escudo(new Texture("shield4.png"),new Texture("shield5.png"),new Texture("shield6.png"));
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
        if (flagsBlue.size < 1) spawnFlagBlue();
        if (flagsRed.size < 1) spawnFlagRed();
        if(TimeUtils.nanoTime() - lastBulletTime > 500000000) spawnBullet();
        flagsRed.forEach(flag -> flag.draw(main.batch));
        flagsBlue.forEach(flag -> flag.draw(main.batch));
        players.forEach(this::updateSpaceship);
        escudo1.draw(main.batch);
        escudo2.draw(main.batch);
        drawBullets();
        colisionBullet();
        colisionFlag(flagsBlue, escudo1, escudo2);
        colisionFlag(flagsRed, escudo2, escudo1);
        drawOnscreenText();
        main.batch.end();
    }
    public void colisionBullet(){
        balas.forEach(bala -> {
            if (bala.bulletSprite.getBoundingRectangle().overlaps(escudo1.escudoSprite.getBoundingRectangle())){
                balas.removeValue(bala,true);
                escudo1.addDamage();
            }
            if (bala.bulletSprite.getBoundingRectangle().overlaps(escudo2.escudoSprite.getBoundingRectangle())){
                balas.removeValue(bala,true);
                escudo2.addDamage();
            }
        });
    }
    public void drawBullets(){
        balas.forEach(bala -> {
            bala.draw(main.batch);
            if (bala.posBullet.y > 480 || bala.posBullet.y < 0 || bala.posBullet.x > 800 || bala.posBullet.x < 0) balas.removeValue(bala,true);
        });
    }
    public void spawnFlagBlue() {
        Flag flagBlue = new Flag(new Vector2(MathUtils.random(32, 768),MathUtils.random(32, 448)),"flagBlue.png");
        flagsBlue.add(flagBlue);
    }
    public  void  spawnFlagRed() {
        Flag flagRed = new Flag(new Vector2(MathUtils.random(32, 768),MathUtils.random(32, 448)),"flagRed.png");
        flagsRed.add(flagRed);
    }
    private void spawnBullet() {
        Bala balaDown = new Bala(new Vector2(MathUtils.random(32, 768),0 ),0);
        Bala balaLeft = new Bala(new Vector2(800,MathUtils.random(32, 448)),1);
        Bala balaUp = new Bala(new Vector2(MathUtils.random(32, 768), 480),2);
        Bala balaRight = new Bala(new Vector2(0,MathUtils.random(32, 448)),3);
        balas.add(balaDown);
        balas.add(balaUp);
        balas.add(balaRight);
        balas.add(balaLeft);
        lastBulletTime = TimeUtils.nanoTime();
    }
    private void drawOnscreenText() {
        main.font.draw(main.batch, "Score: " + escudo1.score, 15, 20);
        main.font.draw(main.batch, "Score: " + escudo2.score, 700, 20);
    }
    public void colisionFlag(Array<Flag> flagsBlue, Escudo escudo1, Escudo escudo2) {
        flagsBlue.forEach(flag -> {
            if (flag.flagSprite.getBoundingRectangle().overlaps(escudo1.escudoSprite.getBoundingRectangle())) {
                flagsBlue.removeValue(flag, true);
                escudo1.score += 10;
                escudo1.damage = 1;
            }
            if (flag.flagSprite.getBoundingRectangle().overlaps(escudo2.escudoSprite.getBoundingRectangle())) {
                flagsBlue.removeValue(flag, true);
            }
        });
    }

    private void updateSpaceship(Player player, Escudo escudo){
        player.Input.update();
        escudo.UP = player.Input.UP==1;
        escudo.DOWN = player.Input.DOWN==1;
        escudo.LEFT = player.Input.LEFT==1;
        escudo.RIGHT = player.Input.RIGHT==1;
    }
}
