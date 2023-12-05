package com.ballsteam.sokiduels.minigames.sokiDefense;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.ballsteam.sokiduels.minigames.Cachipun.Duelist;

public class Shield {
    Sprite shieldSprite;
    Vector2 posEscudo;
    Texture texture;
    Texture texture2;
    Texture texture3;
    protected int UP_DOWN;
    protected int LEFT_RIGHT;
    private final Duelist duelist;
    int damage;
    private long timeOfDeath;
    Sound impact;
    private boolean alive = true;
    public Shield(Texture Texture, Texture Texture2, Texture Texture3, Duelist duelist){
        this.texture = Texture;
        this.texture2 = Texture2;
        this.texture3 = Texture3;
        this.duelist = duelist;
        impact = Gdx.audio.newSound(Gdx.files.internal("sokidefense/impact.ogg"));
        shieldSprite = new Sprite(texture);
        shieldSprite.setSize(45,45);
        posEscudo = new Vector2(0,0);
        damage = 1;
    }
    public void update(){
        if (alive){
            motion();
        } else if (timeOfDeath+750<TimeUtils.millis()) {
            resurrect();
        }

    }
    private void motion(){
        posEscudo.add(new Vector2(LEFT_RIGHT*4,UP_DOWN*4).clamp(0,4));
        if (posEscudo.x > 1366 - shieldSprite.getWidth()) posEscudo.x = 1366 - shieldSprite.getWidth();
        if (posEscudo.x < 0) posEscudo.x = 0;
        if (posEscudo.y > 768 - shieldSprite.getWidth()) posEscudo.y = 768 - shieldSprite.getWidth();
        if (posEscudo.y < 0) posEscudo.y = 0;
    }
    private void resurrect(){
        alive = true;
        shieldSprite.setAlpha(1f);
    }
    private void die(){
        damage = 1;
        timeOfDeath = TimeUtils.millis();
        alive = false;
        posEscudo.x = 683;
        posEscudo.y = 384;
        shieldSprite.setAlpha(0.5f);
        duelist.subtractScore();
    }
    public void draw (SpriteBatch batch){
        update();
        status();
        shieldSprite.setPosition(posEscudo.x,posEscudo.y);
        shieldSprite.draw(batch);
    }
    public void status(){
        switch (damage) {
            case 1 -> shieldSprite.setTexture(texture);
            case 2 -> shieldSprite.setTexture(texture2);
            case 3 -> shieldSprite.setTexture(texture3);
        }
    }
    public void addDamage(){
        if (alive) {
            damage++;
            impact();
            if (damage > 3) {
                die();
            }
        }
    }
    private void impact(){
        if (duelist.isPlayerOne()){
            impact.play(0.05f);
        } else {
            impact.play(0.05f,2f,1f);
        }
    }
    public void dispose(){
        shieldSprite.getTexture().dispose();
    }
}
